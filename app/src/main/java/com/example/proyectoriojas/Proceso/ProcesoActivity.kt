package com.example.proyectoriojas

import Venta
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ProcesoActivity : ComponentActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private val events = mutableMapOf<CalendarDay, MutableList<Venta>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.procesocalendario)

        calendarView = findViewById(R.id.calendarView)
        eventRecyclerView = findViewById(R.id.eventRecyclerView)

        eventAdapter = EventAdapter()
        eventRecyclerView.layoutManager = LinearLayoutManager(this)
        eventRecyclerView.adapter = eventAdapter

        // Llamar a la función para obtener las fechas de ventas desde la API
        obtenerFechasVentasDesdeAPI()

        // Configurar el listener para seleccionar fechas
        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val selectedDate = eventDay.calendar
                val selectedEvents = events[CalendarDay(selectedDate)] ?: emptyList()
                eventAdapter.setEvents(selectedEvents)
            }
        })
    }

    private fun obtenerFechasVentasDesdeAPI() {
        // Realizar la llamada para obtener las fechas de ventas
        RetrofitClient.apiService.obtenerVentas().enqueue(object : Callback<List<Venta>> {
            override fun onResponse(call: Call<List<Venta>>, response: Response<List<Venta>>) {
                if (response.isSuccessful) {
                    val ventas = response.body()
                    ventas?.forEach { venta ->
                        // Convertir la fecha al formato adecuado
                        val fechaFormatted = convertirFecha(venta.fecha)
                        // Crear un objeto CalendarDay con la fecha convertida
                        val calendarDay = CalendarDay(fechaFormatted)
                        // Añadir la venta a la lista de eventos de la fecha correspondiente
                        if (events[calendarDay] == null) {
                            events[calendarDay] = mutableListOf()
                        }
                        events[calendarDay]?.add(venta)
                        // Crear un EventDay con el círculo rojo
                        val eventDay = EventDay(fechaFormatted, R.drawable.circle_red)
                        // Añadir el evento al CalendarView
                        calendarView.setDate(fechaFormatted.time)
                        calendarView.setEvents(listOf(eventDay))
                    }
                }
            }

            override fun onFailure(call: Call<List<Venta>>, t: Throwable) {
                // Manejar el error en caso de fallo al obtener datos de la API
                t.printStackTrace()
            }
        })
    }

    private fun convertirFecha(fechaString: String): Calendar {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fecha = formato.parse(fechaString)
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        return calendar
    }
}

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val events = mutableListOf<Venta>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    fun setEvents(events: List<Venta>) {
        this.events.clear()
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(android.R.id.text1)
        private val subtitle = itemView.findViewById<TextView>(android.R.id.text2)

        fun bind(event: Venta) {
            title.text = "Nombre: ${event.nombre}"
            subtitle.text = "Cantidad Vendida: ${event.cantidadVendida}"
        }
    }
}

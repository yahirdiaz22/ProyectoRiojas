package com.example.proyectoriojas
import Venta
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        obtenerFechasVentasDesdeAPI()

        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val selectedDate = eventDay.calendar
                val selectedEvents = events[CalendarDay(selectedDate)] ?: emptyList()
                eventAdapter.setEvents(selectedEvents)
            }
        })

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun obtenerFechasVentasDesdeAPI() {
        RetrofitClient.apiService.obtenerVentas().enqueue(object : Callback<List<Venta>> {
            override fun onResponse(call: Call<List<Venta>>, response: Response<List<Venta>>) {
                if (response.isSuccessful) {
                    val ventas = response.body()
                    ventas?.forEach { venta ->
                        val fechaFormatted = convertirFecha(venta.fecha)
                        val calendarDay = CalendarDay(fechaFormatted)
                        if (events[calendarDay] == null) {
                            events[calendarDay] = mutableListOf()
                        }
                        events[calendarDay]?.add(venta)
                        val eventDay = EventDay(fechaFormatted, R.drawable.circle_red)
                        calendarView.setDate(fechaFormatted.time)
                        calendarView.setEvents(listOf(eventDay))
                    }
                }
            }

            override fun onFailure(call: Call<List<Venta>>, t: Throwable) {
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

    inner class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

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

        inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val title = itemView.findViewById<TextView>(android.R.id.text1)
            private val subtitle = itemView.findViewById<TextView>(android.R.id.text2)

            fun bind(event: Venta) {
                title.text = "Nombre: ${event.nombre}"
                subtitle.text = "Total: ${event.total}"
            }
        }
    }
}

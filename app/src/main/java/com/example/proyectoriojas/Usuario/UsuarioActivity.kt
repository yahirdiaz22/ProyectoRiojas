    package com.example.proyectoriojas.Usuario

    import Usuario
    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.ListView
    import android.widget.Toast
    import androidx.appcompat.app.AlertDialog
    import androidx.appcompat.app.AppCompatActivity
    import com.example.proyectoriojas.R
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class UsuarioActivity : AppCompatActivity() {

        private lateinit var listViewUsuarios: ListView
        private lateinit var buttonInsertar: Button
        private lateinit var buttonRegresar: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.mostrarusuario)

            listViewUsuarios = findViewById(R.id.listViewUsuarios)
            buttonInsertar = findViewById(R.id.button)
            buttonRegresar = findViewById(R.id.btnRegresar)

            buttonInsertar.setOnClickListener {
                // Abrir la actividad para insertar un nuevo usuario
                val intent = Intent(this, InsertarUsuario::class.java)
                startActivity(intent)
            }

            buttonRegresar.setOnClickListener {
                // Finalizar la actividad actual y regresar a la anterior
                finish()
            }

            // Inicializar la carga de usuarios
            obtenerUsuariosActivosDesdeAPI()
        }

        override fun onResume() {
            super.onResume()
            // Volver a cargar los usuarios cuando la actividad vuelva a estar en primer plano
            obtenerUsuariosActivosDesdeAPI()
        }

        private fun obtenerUsuariosActivosDesdeAPI() {
            val apiService = RetrofitClient.apiService
            apiService.obtenerUsuario().enqueue(object : Callback<List<Usuario>> {
                override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                    if (response.isSuccessful) {
                        val usuarios = response.body() ?: emptyList()
                        val usuariosActivos = usuarios.filter { it.status == 1 } // Filtrar usuarios activos

                        val adapter = UsuarioAdapter(this@UsuarioActivity, usuariosActivos.toMutableList()) { usuario ->
                            confirmarEliminarUsuario(usuario)
                        }

                        listViewUsuarios.adapter = adapter
                    } else {
                        Toast.makeText(this@UsuarioActivity, "Error al obtener usuarios: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                    Toast.makeText(this@UsuarioActivity, "Error al conectar con el servidor: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        private fun confirmarEliminarUsuario(usuario: Usuario) {
            AlertDialog.Builder(this).apply {
                setTitle("Confirmar eliminación")
                setMessage("¿Está seguro de que desea eliminar a ${usuario.nombre}?")
                setPositiveButton("Sí") { _, _ ->
                    eliminarUsuarioEnAPI(usuario)
                }
                setNegativeButton("No", null)
            }.show()
        }

        private fun eliminarUsuarioEnAPI(usuario: Usuario) {
            val apiService = RetrofitClient.apiService
            apiService.eliminarUsuario(usuario.idUsuario).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@UsuarioActivity, "Usuario eliminado: ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                        obtenerUsuariosActivosDesdeAPI() // Volver a cargar los usuarios después de eliminar
                    } else {
                        Toast.makeText(this@UsuarioActivity, "Error al eliminar usuario: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@UsuarioActivity, "Error al conectar con el servidor: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

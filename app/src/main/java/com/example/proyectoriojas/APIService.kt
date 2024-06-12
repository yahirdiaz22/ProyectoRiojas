    package com.example.proyectoriojas.network

    import Cliente
    import Compra
    import Producto
    import Proveedor
    import Usuario
    import Venta
    import okhttp3.ResponseBody
    import retrofit2.Call
    import retrofit2.http.DELETE
    import retrofit2.http.GET
    import retrofit2.http.POST
    import retrofit2.http.PUT
    import retrofit2.http.Path
    import retrofit2.http.Query
    import retrofit2.http.Body

    interface APIService {

        @GET("api/Cliente")
        fun obtenerClientes(): Call<List<Cliente>>
        @GET("api/Usuario/VerificarCorreoExistente/VerificarCorreoExistente")
        fun verificarCorreoExistente(@Query("correoElectronico") correoElectronico: String): Call<Boolean>
        @GET("/api/Usuario/VerificarExistenciaUsuario/VerificarExistencia")
        fun verificarExistenciaUsuario(
            @Query("correoElectronico") correoElectronico: String,
            @Query("password") password: String
        ): Call<ResponseBody>

        @POST("/api/Cliente")
        fun agregarCliente(
            @Query("nombre") nombre: String,
            @Query("direccion") direccion: String,
            @Query("correoElectronico") correoElectronico: String,
            @Query("telefono") telefono: String,
            @Query("status") status: Boolean = true
        ): Call<Void>

        @DELETE("/api/Cliente/{id}")
        fun eliminarCliente(@Path("id") idCliente: Int): Call<Void>

        @PUT("/api/Cliente/{id}")
        fun actualizarCliente(
            @Path("id") idCliente: Int,
            @Query("nombre") nombre: String,
            @Query("direccion") direccion: String,
            @Query("correoElectronico") correo: String,
            @Query("telefono") telefono: String
        ): Call<Void>

        @GET("api/Compra")
        fun obtenerCompra(): Call<List<Compra>>

        @POST("/api/Compra")
        fun agregarCompra(
            @Query("cantidadComprada") cantidad: Int,
            @Query("fecha") fecha: String,
            @Query("precioUnitario") precio: Double,
            @Query("nombre") nombre: String,
            @Query("status") status: Boolean = true
        ): Call<Void>

        @DELETE("/api/Compra/{id}")
        fun eliminarCompra(@Path("id") idCompra: Int): Call<Void>

        @PUT("/api/Compra/{id}")
        fun actualizarCompra(
            @Path("id") idCompra: Int,
            @Query("cantidadComprada") cantidadComprada: Int,
            @Query("fecha") fecha: String,
            @Query("precioUnitario") precioUnitario: Int,
            @Query("nombre") nombre: String,

        ): Call<Void>

        @PUT("/api/Compra/{id}")
        fun editarCompra(@Path("id") idCompra: Int, @Body compra: Compra): Call<Void>

        @GET("api/Producto")
        fun obtenerProductos(): Call<List<Producto>>

        @POST("/api/Producto")
        fun agregarProducto(
            @Query("nombre") nombre: String,
            @Query("precio") precio: Double,
            @Query("cantidad") cantidad: Int,
            @Query("status") status: Boolean = true
        ): Call<Void>

        @DELETE("/api/Producto/{id}")
        fun eliminarProducto(@Path("id") idProducto: Int): Call<Void>

        @PUT("/api/Producto/{id}")
        fun editarProducto(
            @Path("id") idProducto: Int,
            @Query("nombre") nombre: String,
            @Query("precio") precio: Double,
            @Query("cantidad") cantidad: Int,
            @Query("status") status: Boolean = true

        ): Call<Void>
        // Obtener todos los proveedores
        @GET("api/Proveedor")
        fun obtenerProveedores(): Call<List<Proveedor>>

        // Agregar un nuevo proveedor
        @POST("/api/Proveedor")
        fun agregarProveedor(
            @Query("nombre") nombre: String,
            @Query("direccion") direccion: String,
            @Query("correoElectronico") correoElectronico: String,
            @Query("telefono") telefono: String,
            @Query("status") status: Boolean = true
        ): Call<Void>

        // Eliminar un proveedor existente
        @DELETE("/api/Proveedor/{id}")
        fun eliminarProveedor(@Path("id") idProveedor: Int): Call<Void>

        // Actualizar un proveedor existente
        @PUT("/api/Proveedor/{id}")
        fun actualizarProveedor(
            @Path("id") idProveedor: Int,
            @Query("nombre") nombre: String,
            @Query("direccion") direccion: String,
            @Query("correoElectronico") correoElectronico: String,
            @Query("telefono") telefono: String,
            @Query("status") status: Int = 1
        ): Call<Void>

        // Obtener todos los usuarios
        @GET("api/Usuario/GetTodasLosLogin")
        fun obtenerUsuario(): Call<List<Usuario>>

        // Agregar un nuevo usuario
        @POST("api/Usuario/CrearLogin")
        fun agregarUsuario(
            @Query("nombre") nombre: String,
            @Query("correoElectronico") correoElectronico: String,
            @Query("password") password: String,
            @Query("status") status: Boolean = true
        ): Call<Void>

        // Eliminar un usuario existente
        @DELETE("/api/Usuario/{id}")
        fun eliminarUsuario(@Path("id") idUsuario: Int): Call<Void>

        // Actualizar un usuario existente
        @PUT("/api/Usuario/ActualizarLogin/{id}")
        fun actualizarUsuario(
            @Path("id") idUsuario: Int,
            @Query("nombre") nombre: String,
            @Query("correoElectronico") correoElectronico: String
        ): Call<Void>

        // Obtener todas las ventas
            @GET("api/Venta")
            fun obtenerVentas(): Call<List<Venta>>

        // Agregar una nueva venta
        @POST("/api/Venta")
        fun agregarVenta(
            @Query("cantidadVendida") cantidadVendida: Int,
            @Query("fecha") fecha: String,
            @Query("nombre") nombre: String,
            @Query("precioUnitario") precioUnitario: Int,
            @Query("total") total: Double,
            @Query("status") status: Boolean = true,
            ): Call<Void>

        // Eliminar una venta existente
        @DELETE("/api/Venta/{id}")
        fun eliminarVenta(@Path("id") idVenta: Int): Call<Void>

        // Actualizar una venta existente
        @PUT("/api/Venta/{id}")
        fun actualizarVenta(
            @Path("id") idVenta: Int,
            @Query("cantidadVendida") cantidadVendida: Int,
            @Query("fecha") fecha: String,
            @Query("nombre") nombre: String,
            @Query("precioUnitario") precioUnitario: Int,
            @Query("total") total: Double,
            @Query("status") status: Boolean = true,
        ): Call<Void>

    }

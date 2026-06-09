/**
 * Ejercicio 5: It y Scope Functions (run, apply, also, let)
 *
 * Implementa los métodos de esta clase para que pasen todos los tests
 * del archivo Ejercicio5ItScopeFunctionsTest.kt
 *
 * IMPORTANTE: No modifiques la firma de los métodos, solo implementa su lógica.
 * IMPORTANTE: Debes usar las scope functions indicadas en cada sección.
 */

data class Usuario(
    var id: Int = 0,
    var nombre: String = "",
    var email: String = "",
    var activo: Boolean = false,
    var roles: MutableList<String> = mutableListOf(),
    var configuracion: ConfiguracionUsuario = ConfiguracionUsuario(),
)

data class ConfiguracionUsuario(
    var tema: String = "claro",
    var idioma: String = "es",
    var notificaciones: Boolean = true,
    var nivelPrivacidad: Int = 1,
)

data class Validacion(
    val campo: String,
    val valido: Boolean,
    val mensaje: String,
)

class UsuarioBuilder {
    // Parte A: Uso del parámetro implícito 'it'

    fun procesarNumeros(numeros: List<Int>): List<Int> {
      //("Implementar: Filtrar números pares y multiplicarlos por 10, usando 'it'")
        val espar= numeros.filter { it % 2 == 0 }
        val pordiez = espar.map {it*10}
        return pordiez
    }

    fun validarUsuarios(usuarios: List<Usuario>): List<List<Validacion>> {
        // Implementar validación de usuarios usando 'it':
        //- Validar que el nombre no esté vacío
        //- Validar que el email contenga '@'
        //- Validar que tenga al menos un rol
        //Retornar lista de validaciones por cada usuario

        return usuarios.map {
            listOf(
                Validacion("nombre", it.nombre != " ", "tiene nombre"),
                Validacion("email", it.email.contains("@"), "tiene arroba"),
                Validacion("roles", it.roles.isNotEmpty(), "tiene rol")
            )
        }
    }

    fun procesarTextos(textos: List<String>): List<String> {
        //("Implementar: Limpiar espacios, convertir a minúsculas y filtrar vacíos usando 'it'")
        return textos
            .map { it.replace(" ", "") }
            .map { it.lowercase() }
            .filter { it.isNotEmpty() }
    }

    // Parte B: Función run

    fun calcularNivelAcceso(usuario: Usuario): Int {
        //(
            """
            Implementar usando 'run':
            - Si activo: +10 puntos
            - Por cada rol: +5 puntos
            - Si email contiene '@empresa.com': +5 puntos
        """
        var suma = 0
        return usuario.run {
            if (activo == true){
                suma += 10
            }
            for (rol in roles){
                suma += 5
            }
            if(email.contains("@empresa.com")){
                suma += 5
            }
            suma
        }
    }

    fun crearUsuarioConTipo(tipo: String): Usuario {
      //(
            """
            Implementar usando 'run' para decidir configuración:
            - Si tipo es "ADMIN": roles=[ADMIN], nivelPrivacidad=3, notificaciones=true
            - Si tipo es "USER": roles=[USER], nivelPrivacidad=1, notificaciones=false
            - Otros casos: configuración por defecto
        """
        return tipo.run {
            if (this == "ADMIN"){
                Usuario(
                roles= mutableListOf("ADMIN")).apply {
                    configuracion.nivelPrivacidad = 3
                    configuracion.notificaciones = true
                }
            }
            else if (this=="USER"){
                Usuario(roles = mutableListOf("USER")).apply {
                    configuracion.nivelPrivacidad = 1
                    configuracion.notificaciones = false
                    }
            }
            else{
                Usuario(roles = mutableListOf()).apply {
                    configuracion.nivelPrivacidad = 1
                    configuracion.notificaciones = false
                }
            }
        }
    }

    // Parte C: Función apply

    fun crearUsuarioCompleto(
        nombre: String,
        email: String,
        roles: List<String>,
    ): Usuario {
       //(
            """
            Implementar usando 'apply':
            - Crear usuario y configurar todas sus propiedades
            - Establecer activo = true
            - Asignar roles
            - Crear configuración por defecto
        """
        return Usuario().apply{
            this.nombre = nombre
            this.activo = true
            this.email= email
            this.roles= roles.toMutableList()
        }
    }

    fun actualizarUsuario(
        usuario: Usuario,
        actualizacion: Usuario.() -> Unit,
    ): Usuario {
        //("Implementar: Usar 'apply' para aplicar la función de actualización al usuario")
        return usuario.apply {
            actualizacion()
        }
    }

    // Parte D: Función also

    fun crearUsuarioConLog(
        nombre: String,
        email: String,
        onLog: (String) -> Unit,
    ): Usuario {
       //(
            """
            Implementar usando 'also' para logging:
            - Crear usuario
            - Loggear "Usuario creado: [nombre]"
            - Asignar email y loggear "Email asignado: [email]"
            - Activar usuario y loggear "Usuario activado"
        """
        return Usuario(nombre= nombre, email =  email,).also {
            onLog("Usuario creado: ${nombre}")
            onLog("Email asignado: ${email}")
            it.activo= true
            onLog("Usuario activado")

        }
    }

    fun crearYValidar(
        nombre: String,
        email: String,
    ): Pair<Usuario, Boolean> {
      //(
            """
            Implementar usando 'also' para validación:
            - Crear usuario
            - Validar que nombre no esté vacío y email contenga '@'
            - Retornar par (usuario, esValido)
        """
        var esValido = false
        val nuevoUsuario = Usuario(nombre=nombre, email= email).also {
            esValido = it.nombre.isNotEmpty() && it.email.contains("@")
        }
        return nuevoUsuario to esValido
    }

    // Parte E: Función let

    fun procesarEmailOpcional(email: String?): String {
       //(
            """
            Implementar usando 'let':
            - Si email no es null: "Usuario con email: [email]"
            - Si email es null: "Usuario sin email"
        """
        return email?.let { "Usuario con email: $it" }
            ?: "Usuario sin email"

    }

    fun generarMensajesBienvenida(usuarios: List<Usuario>): List<String> {
       //(
            """
            Implementar usando 'let':
            - Solo procesar usuarios activos con email no vacío
            - Generar mensaje "Bienvenido/a [nombre] ([email])"
        """
        return usuarios
            .filter { it.email.isNotBlank() && it.activo == true }
            .map { usuario -> usuario.let { "Bienvenido/a ${it.nombre} (${it.email})" } }
    }

    // Parte F: Combinación de Scope Functions

    fun procesarUsuarioComplejo(datosBase: Map<String, String>): Usuario? {
     //(
            """
            Implementar combinando scope functions:
            1. Verificar que existan 'nombre' y 'email' (si no, retornar null)
            2. Crear usuario con 'run'
            3. Configurar propiedades con 'apply'
            4. Si departamento es "IT", usar 'also' para configuración especial (tema oscuro, rol IT_USER)
            5. Retornar usuario configurado
        """

        val nombre = datosBase["nombre"]
        val email = datosBase["email"]
        if (nombre.isNullOrBlank() || email.isNullOrBlank()) {
            return null
        }

        return Usuario().run {
            Usuario().apply {
                this.nombre = nombre
                this.email = email
            }.also {
                if (datosBase["departamento"] == "IT") {
                    it.configuracion.tema = "oscuro"
                    it.roles.add("IT_USER")
                }
            }
        }
    }

    fun procesarLoteUsuarios(usuarios: List<Usuario>): List<Usuario> {
        //(
            """
            Implementar pipeline con scope functions:
            1. Activar todos los usuarios (apply)
            2. Asignar rol USER si no tienen roles (also)
            3. Configurar notificaciones = true (apply)
            4. Si nombre es "Admin", agregar rol ADMIN y nivelPrivacidad = 3 (run)
        """
        return usuarios.map { usuario ->
            usuario
                .apply {
                    this.activo = true
                }
                .also {
                    if (it.roles.isEmpty()) {
                        it.roles.add("USER")
                    }
                }
                .apply {
                    this.configuracion.notificaciones = true
                }
                .run {
                    if (this.nombre == "Admin") {
                        this.roles.add("ADMIN")
                        this.configuracion.nivelPrivacidad = 3
                    }
                    this
                }
        }
    }

    fun parsearYCrearUsuario(datosRaw: String): Usuario? {
     //(
            """
            Implementar parsing completo:
            1. Parsear formato "clave:valor|clave:valor|..."
            2. Crear usuario con los datos parseados
            3. Usar scope functions apropiadas para cada transformación
            4. Retornar null si el formato es inválido
        """
        if (datosRaw.isBlank() || !datosRaw.contains(":")) {
            return null
        }

        val mapaDatos = datosRaw.let { texto ->
            try {
                texto.split("|").associate {
                    val partes = it.split(":")
                    partes[0].trim() to partes[1].trim()
                }
            } catch (e: Exception) {
                return null
            }
        }

        return run {
            Usuario().apply {
                this.nombre = mapaDatos["nombre"] ?: "Sin Nombre"
                this.email = mapaDatos["email"] ?: "Sin Email"
            }
        }
    }
}


/**
 * Ejercicio 3: SortBy y Lambdas
 * 
 * Implementa los métodos de esta clase para que pasen todos los tests
 * del archivo Ejercicio3SortByLambdaTest.kt
 * 
 * IMPORTANTE: No modifiques la firma de los métodos, solo implementa su lógica.
 */

data class Empleado(
    val id: Int,
    val nombre: String,
    val departamento: String,
    val salario: Double,
    val anosExperiencia: Int,
    val evaluacionDesempeno: Double, // 0.0 a 5.0
    val proyectosCompletados: Int
)
class SistemaRanking {

    // Parte A: Ordenamiento Simple con sortBy

    fun ordenarPorSalario(empleados: List<Empleado>): List<Empleado> {
        val ordenados = empleados.sortedBy { it.salario }
        return ordenados
    }

    fun ordenarPorExperienciaDesc(empleados: List<Empleado>): List<Empleado> {
        val orden = empleados.sortedByDescending { it.anosExperiencia }
        return orden
    }

    fun ordenarPorNombre(empleados: List<Empleado>): List<Empleado> {
        val orden = empleados.sortedBy { it.nombre }
        return orden
    }

    // Parte B: Lambdas Complejas

    fun ordenarPorEficiencia(empleados: List<Empleado>): List<Empleado> {
        val orden = empleados.sortedByDescending { it.proyectosCompletados / it.anosExperiencia }
        return orden
    }

    fun ordenarPorPuntuacionCompuesta(empleados: List<Empleado>): List<Empleado> {
        val orden = empleados.sortedByDescending {
            (it.evaluacionDesempeno * 2) + (it.proyectosCompletados * 0.1)
        }
        return orden
    }

    fun ordenarITPrimero(empleados: List<Empleado>): List<Empleado> {
        val orden = empleados.sortedBy {
            (it.salario)
        }.sortedByDescending {
            (it.departamento == "IT")
        }
        return orden
    }

    // Parte C: Ordenamiento Múltiple

    fun ordenarPorDepartamentoYSalario(empleados: List<Empleado>): List<Empleado> {
        val orden = empleados.sortedBy {
            (it.anosExperiencia)
        }.sortedBy {
            (it.salario)
        }.sortedBy {
            (it.departamento)
        }
        return orden
    }

    fun ordenarSegunSeniority(empleados: List<Empleado>): List<Empleado> {
        val juniors = empleados.filter { it.anosExperiencia < 5 }.sortedByDescending {
            (it.evaluacionDesempeno)
        }
        val seniors = empleados.filter { it.anosExperiencia >= 5 }.sortedByDescending {
            (it.proyectosCompletados)
        }
        val orden = juniors + seniors
        return orden
    }

    // Parte D: Lambdas como Parámetros de Configuración

    fun <T : Comparable<T>> ordenarConEstrategia(
        empleados: List<Empleado>,
        estrategia: (Empleado) -> T
    ): List<Empleado> {
        return empleados.sortedByDescending { empleado -> estrategia(empleado) }
    }

    fun obtenerTopEmpleados(
        empleados: List<Empleado>,
        filtro: (Empleado) -> Boolean,
        ordenamiento: (Empleado) -> Double,
        limite: Int
    ): List<Empleado> {
        val filtrados = empleados.filter(filtro)
        val ordenados = filtrados.sortedByDescending { empleado -> ordenamiento(empleado) }
        return ordenados.take(limite)
    }
}
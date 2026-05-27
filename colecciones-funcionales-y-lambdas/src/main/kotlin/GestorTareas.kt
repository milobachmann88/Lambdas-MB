/**
 * Ejercicio 2: Find, Any y All
 *
 * Implementa los métodos de esta clase para que pasen todos los tests
 * del archivo Ejercicio2FindAnyAllTest.kt
 *
 * IMPORTANTE: No modifiques la firma de los métodos, solo implementa su lógica.
 */

data class Tarea(
    val id: Int,
    val titulo: String,
    val prioridad: Int, // 1 = baja, 2 = media, 3 = alta
    val completada: Boolean,
    val etiquetas: List<String>,
    val tiempoEstimadoHoras: Int,
)

data class EstadoProyecto(
    val hayTareasCriticasPendientes: Boolean,
    val totalHorasPendientes: Int,
    val todosLosBugsResueltos: Boolean
)

class GestorTareas {
    // Parte A: Operaciones con Find

    fun encontrarPrimeraTareaUrgente(tareas: List<Tarea>): Tarea? {
        return tareas.find {it.prioridad == 3}
    }

    fun buscarPorId(
        tareas: List<Tarea>,
        id: Int,
    ): Tarea? {
        return tareas.find { it.id == id }
    }

    fun encontrarTareaPendienteConEtiqueta(
        tareas: List<Tarea>,
        etiqueta: String,
    ): Tarea? {
        return tareas.find { tarea ->
            !tarea.completada && tarea.etiquetas.contains(etiqueta)
        }
    }

    // Parte B: Operaciones con Any

    fun hayTareasUrgentesPendientes(tareas: List<Tarea>): Boolean {
        return tareas.any {it.prioridad == 3 && !it.completada}
    }

    fun hayTareasQueSuperanHoras(
        tareas: List<Tarea>,
        horasLimite: Int,
    ): Boolean {
        return tareas.any{it.tiempoEstimadoHoras > horasLimite}
    }

    fun existeTareaConEtiqueta(
        tareas: List<Tarea>,
        etiqueta: String,
    ): Boolean {
        return tareas.any { it.etiquetas.contains(etiqueta) }
    }

    // Parte C: Operaciones con All

    fun todasCompletadas(tareas: List<Tarea>): Boolean {
        return tareas.all { it.completada }
    }

    fun todasTienenEtiquetas(tareas: List<Tarea>): Boolean {
        return tareas.all { it.etiquetas.size >= 1 }
    }

    fun todasDentroDeHoras(
        tareas: List<Tarea>,
        horasMaximo: Int,
    ): Boolean {
        return tareas.all { it.tiempoEstimadoHoras < horasMaximo }
    }

    // Parte D: Combinación de Find, Any y All

    fun proyectoListoParaEntrega(tareas: List<Tarea>): Boolean {
        val prioridadAlta = tareas.all { if (it.prioridad == 3) it.completada else true}
            val noBlockers = !tareas.any { !it.completada && it.etiquetas.contains("blocker")}
                val documentacion = tareas.find { it.completada && it.etiquetas.contains("docs")}!= null
        return prioridadAlta && noBlockers && documentacion
    }

    fun generarResumenEstado(tareas: List<Tarea>): EstadoProyecto {
        val hayTareasCriticasPendientes= tareas.any{ it.prioridad == 3 && !it.completada}
        val totalHorasPendientes= tareas
            .filter { !it.completada }
            .sumOf { it.tiempoEstimadoHoras }
        val todosLosBugsResueltos= tareas.all { if (it.etiquetas.contains("bug")) it.completada else true }

        return EstadoProyecto(
            hayTareasCriticasPendientes= hayTareasCriticasPendientes,
            totalHorasPendientes= totalHorasPendientes,
            todosLosBugsResueltos= todosLosBugsResueltos
        )
    }
}


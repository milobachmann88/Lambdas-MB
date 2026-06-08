/**
 * Ejercicio 4: Funciones como Argumentos
 *
 * Implementa los métodos de esta clase para que pasen todos los tests
 * del archivo Ejercicio4FuncionesComoArgumentosTest.kt
 *
 * IMPORTANTE: No modifiques la firma de los métodos, solo implementa su lógica.
 */

enum class TipoTransaccion { INGRESO, EGRESO }

enum class EstadoTransaccion { PENDIENTE, PROCESADA, RECHAZADA }

data class Transaccion(
    val id: String,
    val monto: Double,
    val tipo: TipoTransaccion,
    val categoria: String,
    val fecha: String, // Formato: "YYYY-MM-DD"
    val estado: EstadoTransaccion,
)

data class ConfiguracionProcesamiento(
    val filtro: (Transaccion) -> Boolean,
    val transformacion: (Transaccion) -> Double,
    val formateo: (Double) -> String,
)

class ProcesadorTransacciones {
    // Parte A: Funciones de Transformación como Parámetros

    fun transformarMontos(
        transacciones: List<Transaccion>,
        transformacion: (Double) -> Double,
    ): List<Double> {
        return transacciones.map { t ->
            transformacion(t.monto)
        }
    }

    fun <T> procesarCon(
        transacciones: List<Transaccion>,
        procesador: (Transaccion) -> T,
    ): List<T> {
        // ("Implementar: Debe procesar cada transacción con la función dada")
        return transacciones.map { t ->
            procesador(t)
        }
    }

    // Parte B: Funciones de Filtrado como Parámetros

    fun filtrarTransacciones(
        transacciones: List<Transaccion>,
        predicado: (Transaccion) -> Boolean,
    ): List<Transaccion> {
        //("Implementar: Debe filtrar transacciones usando el predicado")
        return transacciones.filter { t ->
            predicado(t)
        }
    }

    fun filtrarConMultiplesCriterios(
        transacciones: List<Transaccion>,
        criterios: List<(Transaccion) -> Boolean>,
    ): List<Transaccion> {
        // ("Implementar: Debe filtrar transacciones que cumplan TODOS los criterios")
        return transacciones.filter { t ->
            criterios.all { criterio -> criterio(t) }
        }
    }

    // Parte C: Funciones de Agregación como Parámetros

    fun <T> agregar(
        transacciones: List<Transaccion>,
        valorInicial: T,
        agregador: (T, Transaccion) -> T,
    ): T {
        //("Implementar: Debe agregar valores usando la función agregadora")
        var acumulador: T = valorInicial
        for (t in transacciones) {
            acumulador = agregador(acumulador, t)
        }
        return acumulador
    }

    // Parte D: Composición de Funciones

    fun ejecutarPipeline(
        transacciones: List<Transaccion>,
        filtro1: (Transaccion) -> Boolean,
        filtro2: (Transaccion) -> Boolean,
        transformacion: (Transaccion) -> Double,
        agregacion: (Double, Double) -> Double,
    ): Double {
        //(
        """
            Implementar pipeline:
            1) Aplicar filtro1
            2) Aplicar filtro2
            3) Transformar cada transacción a Double
            4) Agregar todos los valores con la función de agregación (inicial: 0.0)
        """

        val montos = transacciones
            .filter { t -> filtro1(t) }
            .filter { t -> filtro2(t) }
            .map { t -> transformacion(t) }

        var acumulador = 0.0
        for (monto in montos) {
            acumulador = agregacion(acumulador, monto)
        }

        return acumulador
    }

    fun procesarConConfiguracion(
        transacciones: List<Transaccion>,
        config: ConfiguracionProcesamiento,
    ): List<String> {
        //  (
        """
            Implementar:
            1) Filtrar usando config.filtro
            2) Transformar usando config.transformacion
            3) Formatear usando config.formateo
        """
        // )
        val filtradas = transacciones.filter { t -> config.filtro(t) }
        val transformadas = filtradas.map { t -> config.transformacion(t) }
        val formateadas = transformadas.map { t -> config.formateo(t) }

        return formateadas

    }

    fun procesarConEventos(
        transacciones: List<Transaccion>,
        onTransaccionProcesada: (Transaccion) -> Unit,
        onTransaccionRechazada: (Transaccion) -> Unit,
    ) {
        for (t in transacciones) {
            if (t.estado == EstadoTransaccion.PROCESADA) {
                onTransaccionProcesada(t)
            } else if (t.estado == EstadoTransaccion.RECHAZADA) {
                onTransaccionRechazada(t)
            }
        }
    }
}


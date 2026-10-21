package com.atom.myapp
/* 3.- View Model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SensorUiState(
    val actual: Double = 0.0,
    val promedio: Double = 0.0,
    val lecturas: List<Double> = emptyList(),
    val monitoreando: Boolean = false
)

class SensorViewModel(
    private val repo: SensorRepository = SensorRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SensorUiState())
    val uiState: StateFlow<SensorUiState> = _uiState.asStateFlow()

    // Shared Flow para eventos de una sola vez (alertas)
    private val _alertas = MutableSharedFlow<String>()
    val alertas: SharedFlow<String> = _alertas.asSharedFlow()

    private val _contador = MutableStateFlow<Int?>(null)
    val contador: StateFlow<Int?> = _contador.asStateFlow()

    private var jobSensor: Job? = null
    private var jobContador: Job? = null

    fun iniciar() {
        if (jobSensor?.isActive == true) return
        _uiState.update { it.copy(monitoreando = true) }

        jobSensor = viewModelScope.launch {
            repo.temperaturaFlow()
                .map { "%.1f".format(it).toDouble() }
                .onEach { temp ->
                    if (temp > 35.0) _alertas.emit("Temperatura alta: $temp °C!")
                }
                .catch { e -> _alertas.emit("Error: ${e.message}") }
                .collect { temp ->
                    _uiState.update { estado ->
                        val nuevas = (estado.lecturas + temp).takeLast(10)
                        estado.copy(
                            actual = temp,
                            lecturas = nuevas,
                            promedio = nuevas.average()
                        )
                    }
                }
        }
    }

    fun detener() {
        jobSensor?.cancel()
        _uiState.update { it.copy(monitoreando = false) }
    }

    fun iniciarContador(segundos: Int = 10) {
        jobContador?.cancel()
        jobContador = viewModelScope.launch {
            repo.cuentaRegresiva(segundos)
                .onCompletion { _contador.value = null }
                .collect { _contador.value = it }
        }
    }
}
*/
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

data class SensorUiState(
    val actual: Double = 0.0,
    val promedio: Double = 0.0,
    val lecturas: List<Double> = emptyList(),
    val monitoreando: Boolean = false
)

class SensorViewModel(
    private val repo: SensorRepository = SensorRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SensorUiState())
    val uiState: StateFlow<SensorUiState> = _uiState.asStateFlow()

    // Shared Flow para eventos de una sola vez (alertas)
    private val _alertas = MutableSharedFlow<String>()
    val alertas: SharedFlow<String> = _alertas.asSharedFlow()

    private val _contador = MutableStateFlow<Int?>(null)
    val contador: StateFlow<Int?> = _contador.asStateFlow()

    private var jobSensor: Job? = null
    private var jobContador: Job? = null

    // Apartado 3: Convertir temperaturaFlow en un StateFlow caliente usando stateIn
    val temperaturaStateFlow: StateFlow<Double> = repo.temperaturaFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun iniciar() {
        if (jobSensor?.isActive == true) return
        _uiState.update { it.copy(monitoreando = true) }

        jobSensor = viewModelScope.launch {
            repo.temperaturaFlow()
                .combine(_umbralAlerta) { temp, umbral ->
                    Pair(temp, umbral) // Unimos ambos valores
                }
                .onEach { (temp, umbral) ->
                    // La alerta se dispara si la temperatura supera el umbral dinámico del Slider
                    if (temp > umbral) {
                        _alertas.emit("¡Alerta! Temperatura de $temp °C supera tu límite de $umbral °C")
                    }
                }
                .collect { (temp, _) ->
                    // Actualizas tu UI con la temperatura actual...
                }
        }
    }

    // 1. Estado para guardar el valor del Slider (ej. empieza en 35.0)
    private val _umbralAlerta = MutableStateFlow(35.0)
    val umbralAlerta: StateFlow<Double> = _umbralAlerta.asStateFlow()

    fun actualizarUmbral(nuevoUmbral: Double) {
        _umbralAlerta.value = nuevoUmbral
    }

    fun detener() {
        jobSensor?.cancel() // Apartado 1: Cancelación de Job
        _uiState.update { it.copy(monitoreando = false) }
    }

    fun iniciarContador(segundos: Int = 10) {
        jobContador?.cancel()
        jobContador = viewModelScope.launch {
            repo.cuentaRegresiva(segundos)
                .onCompletion { _contador.value = null }
                .collect { _contador.value = it }
        }
    }
}
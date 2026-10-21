package com.atom.myapp
/* 2.- Fuente de datos
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class SensorRepository {
    // Flow frío: se ejecuta solo cuando alguien lo colecta
    fun temperaturaFlow(): Flow<Double> = flow {
        while (true) {
            val lectura = 20.0 + Random.nextDouble() * 20.0 // 20-40 °C
            emit(lectura)
            delay(1000)
        }
    }

    // Cuenta regresiva
    fun cuentaRegresiva(desde: Int): Flow<Int> = flow {
        for (i in desde downTo 0) {
            emit(i)
            delay(1000)
        }
    }
}
*/


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

class SensorRepository {
    // Apartado 5: flowOn(Dispatchers.Default) para procesamiento en segundo plano
    fun temperaturaFlow(): Flow<Double> = flow {
        while (true) {
            val lectura = 20.0 + Random.nextDouble() * 20.0 // 20-40 °C
            emit(lectura)
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)

    // Cuenta regresiva
    fun cuentaRegresiva(desde: Int): Flow<Int> = flow {
        for (i in desde downTo 0) {
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
}
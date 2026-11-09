package com.atom.myapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.atom.myapp.ui.theme.MyAppTheme
import com.atom.myapp.views.HomeScreen
import com.atom.myapp.views.MainOnBoarding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "user_prefs")
val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()

                    var isCompleted by remember { mutableStateOf<Boolean?>(null) }

                    LaunchedEffect(Unit) {
                        val completed = context.dataStore.data.map { prefs ->
                            prefs[ONBOARDING_COMPLETED_KEY] ?: false
                        }.first()
                        isCompleted = completed
                    }

                    when (isCompleted) {
                        null -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        true -> {
                            HomeScreen(
                                onResetOnboarding = {
                                    coroutineScope.launch {
                                        context.dataStore.edit { prefs ->
                                            prefs[ONBOARDING_COMPLETED_KEY] = false
                                        }
                                        isCompleted = false
                                    }
                                }
                            )
                        }
                        false -> {
                            MainOnBoarding(
                                onFinish = {
                                    coroutineScope.launch {
                                        context.dataStore.edit { prefs ->
                                            prefs[ONBOARDING_COMPLETED_KEY] = true
                                        }
                                        isCompleted = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
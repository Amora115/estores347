package com.example.projetodemoveis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val locationApi = RetrofitInstance.getLocationApi()
    private val weatherApi = RetrofitInstance.getWeatherApi()

    private val _weatherData = MutableLiveData<String>()
    val weatherData: LiveData<String> get() = _weatherData

    fun fetchWeatherData() {
        // Usando viewModelScope para garantir que o código esteja dentro de uma coroutine
        viewModelScope.launch {
            try {
                // Chama a API de Localização
                val location = locationApi.getLocation() // Função suspend, chamada dentro da coroutine
                // Usa a cidade retornada para chamar a API de Clima
                val weather = weatherApi.getWeather(location.city) // Função suspend, chamada dentro da coroutine
                // Atualiza os dados para exibição
                _weatherData.value = "Clima em ${location.city}: ${weather.description}, ${weather.temperature}°C"
            } catch (e: Exception) {
                _weatherData.value = "Erro ao obter os dados: ${e.message}"
            }
        }
    }
}

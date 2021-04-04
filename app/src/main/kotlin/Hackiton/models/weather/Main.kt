package Hackiton.models.weather

data class Main(
    val feels_like: String,
    val humidity: Int,
    val pressure: Int,
    val temp: String,
    val temp_max: String,
    val temp_min: String
)
// set date and time
const now = new Date()
//shows user date
now ;

//have user set city
GET api.openweathermap.org/data/2.5/weather?id=2158177&units=metric&appid={2d6333bd76970c4b1d16306b517f334c}

//importing openweathermap api
// reference https://stackoverflow.com/questions/36174177/show-local-weather-using-openweathermap-api

var getWeather = function(data) {
    $.getJSON('http://api.openweathermap.org/data/2.5/weather', {
        lat: data.lat,
        lon: data.lon,
        units: "metric",
        appid: "2d6333bd76970c4b1d16306b517f334c"
    }, showWeather, 'jsonp');
};

var showWeather = function(data) {
    $("#test").text("this shows local weather")
    $("#temp").text(data.main.temp)
    $("#description").text(data.weather[0].description)
    $("#place").text(data.name)
};

$(document).ready(function() {
    $("#btn").click(function() {
			console.log("button pressed")
        $.getJSON('http://ip-api.com/json', getWeather)
    })
})

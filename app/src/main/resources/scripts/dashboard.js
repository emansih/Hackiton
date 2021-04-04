function updateClocks () {
	localClock(); setInterval('localClock()', 1000 );
	worldclock(); setInterval('worldclock()', 1000 );
}

function localClock () {

	// console.log("Clock Fired");

  var currentTime = new Date ( );

  var currentHours = currentTime.getHours ( );
  var currentMinutes = currentTime.getMinutes ( );
  var currentSeconds = currentTime.getSeconds ( );

  // Pad the minutes and seconds with leading zeros, if required
  currentMinutes = ( currentMinutes < 10 ? "0" : "" ) + currentMinutes;
  currentSeconds = ( currentSeconds < 10 ? "0" : "" ) + currentSeconds;

  // Choose either "AM" or "PM" as appropriate
  var timeOfDay = ( currentHours < 12 ) ? "AM" : "PM";

  // Convert the hours component to 12-hour format if needed
  currentHours = ( currentHours > 12 ) ? currentHours - 12 : currentHours;

  // Convert an hours component of "0" to "12"
  currentHours = ( currentHours == 0 ) ? 12 : currentHours;

  // Compose the string for display
	var timeOutput = currentHours + ":" + currentMinutes;
	// var timeOutput = currentHours + ":" + currentMinutes + ":" + currentSeconds;
	var ampmOutput =  ":" + currentSeconds + " " + timeOfDay;

  // Update the time display
    document.getElementById("mainClock").firstChild.nodeValue = timeOutput;
	document.getElementById("AMPM").firstChild.nodeValue = ampmOutput;
}

function worldclock () {
	var currentTime = new Date ( );
	var cityNameLabel = ""  //EDIT THIS TO SET CITY MESSAGE
	
	var offsetHours = -3; //EDIT THIS TO SET TIME OFFSET
	var offsetMins = 0;	//EDIT THIS TO SET TIME OFFSET

	var currentHours = currentTime.getHours ( );
	var currentMinutes = currentTime.getMinutes ( );
	var currentSeconds = currentTime.getSeconds ( );
	currentHours = (currentHours + offsetHours);

  // Pad the minutes and seconds with leading zeros, if required
	currentMinutes = ( currentMinutes < 10 ? "0" : "" ) + currentMinutes;
  currentSeconds = ( currentSeconds < 10 ? "0" : "" ) + currentSeconds;

  // Choose either "AM" or "PM" as appropriate
  var timeOfDay = ( currentHours < 12 ) ? "AM" : "PM";

  // Convert the hours component to 12-hour format if needed
  currentHours = ( currentHours > 12 ) ? currentHours - 12 : currentHours;

  // Convert an hours component of "0" to "12"
  currentHours = ( currentHours == 0 ) ? 12 : currentHours;

  // Compose the string for display
	var timeOutput = currentHours + ":" + currentMinutes;
	var ampmOutput = ":" + currentSeconds + " " + timeOfDay;
	var dateOutput = "Date Not yet Coded!";

	var offsetOutput = offsetHours;
	var word = "before"

	if (offsetHours >= 0) {
		word = "after";
	}
	if (offsetHours <= 0) {
		offsetOutput = offsetOutput * -1;
	}

	// var cityNameOutput =  cityNameLabel + " (" + offsetOutput + " hrs)"

	var cityNameOutput =  offsetOutput + " hrs " + word + " now."


  // Update the time display
  document.getElementById("worldclockwidget").firstChild.nodeValue = timeOutput;
	document.getElementById("worldclockAMPM").firstChild.nodeValue = ampmOutput;
	document.getElementById("clockwidgetCityName").innerHTML = cityNameOutput;
}


function checkTodo(){
	var array = []
	var checkboxes = document.querySelectorAll('input[type=checkbox]:checked')

	for (var i = 0; i < checkboxes.length; i++) {
		array.push(checkboxes[i].value)
	}
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open("GET", 'https://hackiethon.hisname.xyz/marktodo/' + array[0], false );
	xmlHttp.send(null);
	window.location.reload(false);
}

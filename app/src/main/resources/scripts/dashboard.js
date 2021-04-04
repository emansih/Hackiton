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
	var timeZone = getCookie("timeZone")
	var currentTime = new Date().toLocaleString("en-US",
		{timeZone: timeZone, hour: '2-digit', minute:'2-digit', second: '2-digit'});
	// Update the time display
    document.getElementById("worldclockwidget").firstChild.nodeValue = currentTime;
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

function changeTimeZone(){
	var timeZone = document.getElementById("timezone").value;
	document.getElementById("clockwidgetCityName").innerHTML = timeZone
	setCookie("timeZone", timeZone)
}

function cookieTimeZone(){
	if(getCookie("timeZone") != null){
		document.getElementById("clockwidgetCityName").innerHTML = getCookie("timeZone")
		document.getElementById("timezone").value = getCookie("timeZone")
	} else {
		setCookie("timeZone", "Asia/Taipei")
	}
}
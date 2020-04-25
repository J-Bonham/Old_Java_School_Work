//AVF 1401
//Jeremiah Bonham
//Main JS File

//test alert
var testAlert = function () {
    alert("Working!")
}

//Instagram Fetch
//////////////////////////////
var instaFetch = function(){
    
    var tag = $("#enteredTag").val();
    var url = "https://api.instagram.com/v1/tags/" + tag +  "/media/recent?callback=?&amp;client_id=2e8139ebd80443ad92b4b74334c0d085;min_id=10";
    $.getJSON(url, instaOut);
};

var instaOut = function(info) {
    console.log(info);
    $("#data-msg").html("<h2> </h2>");
    $.each(info.data, function(index, photo) {
        var image = "<li class='instaList'><img class='instaIMG' src='" + photo.images.standard_resolution.url + "' alt='" + photo.user.min_id + "' /><h4>" + photo.user.full_name + "</h4></li>";
        $("#data-output").append(image);
    });

};


//Check Network before pulling picture from Instagram MashUp

var instaCheck = function () {
    var connectionType = navigator.connection.type;

    var states = {};
    states[Connection.WIFI]     = "WiFi";
    states[Connection.CELL_3G]  = "3G";
    states[Connection.CELL_4G]  = "4G";
    states[Connection.CELL]     = "Cell";
    states[Connection.NONE]     = "None";
    
    if (states[connectionType] == ("WiFi")) {
        instaFetch()
    }
    else if (states[connectionType] == ("None")) {
        alert("There is no connection to a network.  Please try again later.")
    }
    else {
       var dataUseage = confirm("Be aware that this will count against montly limits if not on WiFi. Continue?")
        
       if (dataUseage == true) {
        instaFetch()
        }
    }
    
}





$("#goInsta").click(instaCheck);

//Weather Fetch
////////////////////////////////
var getWeather = function(){

    //var enteredCity = prompt("Please enter your City", "Chicago");
    //var enteredST = prompt("Please enter your State abbreviation", "IL");
    
    var enteredCity = $("#enteredCity").val();
    var enteredST = $("#enteredST").val();
    
    var enteredLocation = (enteredCity + "," + enteredST);
    var url2 = "http://api.openweathermap.org/data/2.5/find?q=" + enteredLocation + "&units=imperial";
    $.getJSON(url2, weatherOutput);
};

var weatherOutput = function(info) {

    //alert ("pulling data");
    console.log(info);

    $("#weather-msg").html("<h3>  </h3>");
    
    $.each(info.list, function(index, weatherData) {
        
        $("#location").append(" " + weatherData.name);

        $("#temper").append("  " + weatherData.main.temp + " F ");

        $("#lowHigh").append("Low for today: " + weatherData.main.temp_min + " High for today: " + weatherData.main.temp_max);

        $("#humidity").append(" " + weatherData.main.humidity + "%");

        $("#clouds").append(" " + weatherData.clouds.all + "%");

    });

    //window.reload();

};

$("#go").click(getWeather);



//GeoLocation Function

var getGeo = function() {
var geoSuccess = function(position) {
    alert('Latitude: '+ position.coords.latitude + '\n' +
          'Longitude: '+ position.coords.longitude + '\n');
};

// onError Callback receives a PositionError object
//
function geoError(error) {
    alert('code: '    + error.code    + '\n' +
          'message: ' + error.message + '\n');
}

navigator.geolocation.getCurrentPosition(geoSuccess, geoError);
}

$("#geo").click(getGeo);




//Native Notification

var getNotifications = function(){
    var callBack = function() {
    }
    
    navigator.notification.alert("This is just a test!", callBack, "Test Notification", "Finished");

}
$("#goNotification").click(getNotifications);




//Compass Heading

var getCompass = function(){

var onSuccess = function(heading) {
    alert('Heading: ' + heading.magneticHeading);
};

var onError = function(error) {
    alert('CompassError: ' + error.code);
};

navigator.compass.getCurrentHeading(onSuccess, onError);
}
$("#goCompass").click(getCompass);




//Check network connection

var checkConnection = function () {
    var connectionType = navigator.connection.type;

    var states = {};
    states[Connection.WIFI]     = "You are connected to WiFi";
    states[Connection.CELL_3G]  = "You are connected to 3G";
    states[Connection.CELL_4G]  = "You are connected to 4G";
    states[Connection.CELL]     = "You are connected to Cell";
    states[Connection.NONE]     = "You are not connected to any network";

    alert(states[connectionType]);
}

$("#goNetwork").click(checkConnection);




//Weather and Geolocation MashUp

var getWeatherGeo = function() {
    
    navigator.geolocation.getCurrentPosition(getGeoWeather, getGeoWeatherFail);
};

//If location not found
var getGeoWeatherFail = function() {
    
    alert("Unable to determine location. Please try again later.")
}

var getGeoWeather = function(position) {
    var lat = position.coords.latitude;
    var lon = position.coords.longitude;
        
    var url3 = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=imperial";
    $.getJSON(url3, addWeather)
          
} 
   
var addWeather = function(info) { 

    $("#location").append(" " + info.name);

    $("#temper").append("  " + info.main.temp + " F ");

    $("#lowHigh").append("Low for today: " + info.main.temp_min + " High for today: " + info.main.temp_max);

    $("#humidity").append(" " + info.main.humidity + "%");

    $("#clouds").append(" " + info.clouds.all + "%");
}

$("#getLoc").click(getWeatherGeo); 

var ws;
var host = window.location.host;


init = function () {
    ws = new WebSocket("ws://"+host+"/getuser");
    ws.onopen = function (event) {
    }
    ws.onmessage = function (event) {
        var user =  event.data;
        //user = JSON.parse(user);
        output.innerHTML = user
        //user.id + " " + user.name
        //    + " " + user.phones + " " + user.address;
    }
    ws.onclose = function (event) {
    }
}

function fieldsToJson(){
        var obj = {};
        var form = document.getElementById( "getuserform" );
		var elements = form.querySelectorAll( "input" );
		for( var i = 0; i < elements.length; ++i ) {
			var element = elements[i];
			var name = element.name;
			var value = element.value;

			if( name ) {
				obj[ name ] = value;
			}
		}
//		return JSON.stringify( value );
		return value;
}

function sendMessage() {
    var json = fieldsToJson();
    ws.send(json);


}
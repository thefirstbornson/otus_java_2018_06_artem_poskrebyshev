var ws;
var host = window.location.host;


init = function () {
    ws = new WebSocket("ws://"+host+"/adduser");
    ws.onopen = function (event) {
    }
    ws.onmessage = function (event) {
        var user =  event.data;
        user = JSON.parse(user);
        output.innerHTML = user.id + " " + user.name
            + " " + user.phones + " " + user.address;
    }
    ws.onclose = function (event) {
    }
}

function fieldsToJson(){
        var obj = {};
        var form = document.getElementById( "adduserform" );
		var elements = form.querySelectorAll( "input" );
		for( var i = 0; i < elements.length; ++i ) {
			var element = elements[i];
			var name = element.name;
			var value = element.value;

			if( name ) {
				obj[ name ] = value;
			}
		}
		return JSON.stringify( obj );
}

function sendMessage() {
    if(ws.readyState === ws.CLOSED){
        ws = new WebSocket("ws://"+host+"/adduser");
    }
    var json = fieldsToJson();
    ws.send(json);
}
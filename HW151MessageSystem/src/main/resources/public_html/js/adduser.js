var ws;

init = function () {
    var host = window.location.host;
    output.innerHTML = host;
    ws = new WebSocket("ws://"+host+"/adduser");


    ws.onopen = function (event) {
        //sendMessage();
        //ws.send("0");
    }
    ws.onmessage = function (event) {
		output.innerHTML = event.data;
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
    var json = fieldsToJson();
    ws.send(json);
    output.innerHTML = json;

}
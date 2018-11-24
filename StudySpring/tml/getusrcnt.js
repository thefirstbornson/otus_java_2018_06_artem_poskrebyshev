var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8090/numusers");
//    ws.onopen = function (event) {
    ws.onopen = function () {
        document.write("WebSocket opened <br>");
    }
    ws.onmessage = function (event) {
//        var textarea = document.getElementById("messages");
//        textarea.value = textarea.value + event.data + "\n";
    }
    ws.onclose = function (event) {
    }
    ws.onerror = function(err) {
         document.write("Error: " + err);
     }
}



function sendMessage() {
//    var messageField = document.getElementById("message");
//    var userNameField = document.getElementById("username");
//    var message = userNameField.value + ":" + messageField.value;
//    ws.send(message);
//    messageField.value = '';
}
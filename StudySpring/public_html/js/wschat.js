var ws;

init = function () {
    ws = new WebSocket("ws://192.168.195.249:8090/chat");
    ws.onopen = function (event) {
    }
    ws.onmessage = function (event) {
        var textarea = document.getElementById("messages");
        textarea.value = textarea.value + event.data + "\n";
    }
    ws.onclose = function (event) {

    }
};

function sendMessage() {
    var messageField = document.getElementById("message");
    var userNameField = document.getElementById("username");
    var message = userNameField.value + ":" + messageField.value;
    ws.send(message);
    messageField.value = '';
}
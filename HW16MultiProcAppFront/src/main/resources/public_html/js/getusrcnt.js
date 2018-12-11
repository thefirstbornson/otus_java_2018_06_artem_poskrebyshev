var ws;

init = function () {
     ws = new WebSocket("ws://localhost:8090/cntusrs");


    ws.onopen = function (event) {
        ws.send("0");
    }
    ws.onmessage = function (event) {
        var textarea = document.getElementById("cntusrs");
        textarea.value = textarea.value + event.data;
    }
    ws.onclose = function (event) {

    }
}

function sendMessage() {

}
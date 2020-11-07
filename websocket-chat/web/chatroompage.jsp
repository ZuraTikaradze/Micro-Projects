<%--
  Created by IntelliJ IDEA.
  User: Zura
  Date: 9/21/2018
  Time: 9:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Zuras chat </title>
</head>
</head>
<body>
<script>
    var websocket=new WebSocket ( "ws://"+document.location.host+document.location.pathname+"chatroomServerEndpoint/" + "${roomname}")
    websocket.onmessage = function processMessage(message) {
        var jsonData = JSON.parse(message.data);
        if (jsonData.message != null) messageTextArea.value += jsonData.message + "\n";
    }

    function sendMessage() {
        websocket.send(document.getElementById("messageText").value);
        document.getElementById("messageText").value = "";
    }
</script>

<marker> User:  ${username}  | Room : " ${roomname}"</marker>
<br>
<textarea id="messageTextArea" readonly="readonly" rows="10" cols="45"> </textarea> <br/>
<input type="text" id="messageText" size="50"/>
<input type="button" value="Send" onclick="sendMessage();"/>

</body>
</html>

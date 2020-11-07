<%--
  Created by IntelliJ IDEA.
  User: Zura
  Date: 9/21/2018
  Time: 9:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Zuras chat </title>
</head>
<body>
<form name="sumbitForm">
    <mark> pleas select a room :</mark>
    <br>
    <select id="roomSelect" name="roomSelect" onchange="handleNewRoom()">
        <option value="room1"> room 1</option>
        <option value="room2"> room 2</option>
        <option value="room3"> room 3</option>
        <option value="newRoomOption"> newRoom</option>
    </select>
    <br>
    <div id=\"newRoomDivId\" style=" display:none">
    <mark>Please enter room Name</mark>
    <br>
    <input type="text" name="newRoomName" size="20 "/>
    </div>
    <mark>Please suply user Name</mark>
    <br>
    <input type="text" name="username" size="20 "/>
    <input type="submit" value="Enter"/>
</form>

<script>
    function handleNewRoom() {
        var roomSelect = document.getElementById("roomSelect");
        var roomSelectOption = roomSelect.options[roomSelect.selectedIndex].value;
        if (roomSelectOption == 'newRoomOption')
        document.getElementById("newRoomDivId").style.display='block';
    else
        document.getElementById("newRoomDivId").style.display='none';
    }
    window.onload = function () {
        document.submitForm.action = submitAction();
    }
    function submitAction() {
        return document.location.pathname;
    }
</script>

</body>
</html>

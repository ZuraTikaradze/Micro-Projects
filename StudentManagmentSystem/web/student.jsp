<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <title>Add New Student</title>
</head>
<body>
<form action="StudentController" method="post">
    <fieldset>
        <div>
            <input type="text" name="Id" value="<c:out value="${student.id}" />" readonly="readonly"
                   placeholder="Student ID"/>
        </div>
        <div>
            <input type="text" name="Name" value="<c:out value="${student.name}" />" placeholder="Name"/>
        </div>
        <div>
            <input type="text" name="Surname" value="<c:out value="${student.surname}" />" placeholder="Surname"/>
        </div>
        <div>
            <input type="text" name="Age" value="<c:out value="${student.age}" />" placeholder="Age"/>
        </div>
        <div>
            <input type="submit" value="Submit"/>
        </div>
    </fieldset>
</form>
</body>
</html>
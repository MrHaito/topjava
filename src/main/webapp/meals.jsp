<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h3><a href="meals?action=add">Add Meal</a></h3>
<table>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: ${meal.excess ? 'red' : 'green'}">
            <td>${meal.date} ${meal.time}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?id=${meal.id}&action=edit">Edit</a></td>
            <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

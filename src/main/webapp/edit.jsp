<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${meal.description}</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${meal.description}</h2>
<h3><a href="meals">Meals</a></h3>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${meal.uuid}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" name="datetime"
                       value="${meal.date}T${meal.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"></dd>
        </dl>
        <dl>
            <dt>Description: </dt>
            <dd><input type="text" name="description" value="${meal.description}" required></dd>
        </dl>
        <dl>
            <dt>Calories: </dt>
            <dd><input type="number" name="calories" value="${meal.calories}"></dd>
        </dl>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>

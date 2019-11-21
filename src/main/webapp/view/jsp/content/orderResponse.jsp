<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="main">
    <p>За вами приедет:</p>
    <p>${sessionScope.car.color} ${sessionScope.car.model} ${sessionScope.car.number}</p>
    <p>через 5 минут</p>
</div>
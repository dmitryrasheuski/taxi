<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="main">
    <p>Вход в личный кабинет:</p>
    <form class="loginForm" method="get">

        <div>
            <label for="phone">Phone</label>
            <input required id="phone" type="text" name="phone" pattern="\+375-(29|33|44)-\d{3}-\d{2}-\d{2}">
        </div>
        <div>
            <label for="password">Password</label>
            <input required id="password" type="text" name="password" >
        </div>

        <button type="submit" name="command" value="login">Submit</button>

        <a class="goToRegister" href="/taxi/frontController/registry.jsp">Go to Registry</a>

    </form>
</div>

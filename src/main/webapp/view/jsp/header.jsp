<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form class="login">
    <c:choose>

        <c:when test="${sessionScope.user != null}">
            <span class="hello">${sessionScope.user.name}</span>
            <button name="command" value="logout">Logout</button>
        </c:when>
        <c:otherwise>
            <a href="/taxi/frontController/login.jsp">Login</a>
        </c:otherwise>

    </c:choose>
</form>
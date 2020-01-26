<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p class="nameApp">Baше<br />ТAXI</p>
<nav class="menu">

    <a href="order.jsp">Order taxi</a>
    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <a href="?command=getTripList">Trip List</a>
        </c:when>
        <c:otherwise>
            <a href="/taxi/frontController/login.jsp">Personal account</a>
        </c:otherwise>
    </c:choose>
    <a href="info.jsp">about Us</a>

</nav>
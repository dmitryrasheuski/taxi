<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    int i = 0;
%>

<div class="main">
    <table>
        <caption>Your Trips</caption>
        <thead>
            <tr> <th>№</th><th>From</th> <th>Where</th> </tr>
        </thead>
        <tbody>

            <c:forEach var="order" items="${requestScope.orderListForPassenger}">
                <tr> <td><%= ++i %></td> <td>${order.from}</td> <td>${order.where}</td></tr>
            </c:forEach>

        </tbody>
    </table>
</div>




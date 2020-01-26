<%@ page import="web.JspPages" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.exception != null}" >
    <p class="exceptionMessage">
        <%=
        ((Exception)request.getAttribute("exception")).getMessage()
        %>
    </p>
</c:if>

<c:choose>
    <c:when test="${sessionScope.nextPage == JspPages.ORDER}" >
        <%@include file="content/order.jsp"%>
    </c:when>
    <c:when test="${sessionScope.nextPage == JspPages.ORDER_RESPONSE}" >
        <%@include file="content/orderResponse.jsp"%>
    </c:when>
    <c:when test="${sessionScope.nextPage == JspPages.LOGIN}" >
        <%@include file="content/login.jsp"%>
    </c:when>
    <c:when test="${sessionScope.nextPage == JspPages.INFO}" >
        <%@include file="content/info.jsp"%>
    </c:when>
    <c:when test="${sessionScope.nextPage == JspPages.REGISTRY}" >
        <%@include file="content/registry.jsp"%>
    </c:when>
    <c:when test="${sessionScope.nextPage == JspPages.ORDER_RESPONSE}" >
        <%@include file="content/registryResponse.jsp"%>
    </c:when>
    <c:when test="${sessionScope.nextPage == JspPages.TRIP_LIST}" >
        <%@ include file="content/tripList.jsp"%>
    </c:when>

    <c:otherwise>
        <%@include file="content/order.jsp"%>
    </c:otherwise>
</c:choose>

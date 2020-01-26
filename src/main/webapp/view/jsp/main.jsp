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

<c:forEach var="page" items="${JspPages.values}" >
    <c:if test="${sessionScope.nextPage == page}" >
        <jsp:include page="${page.getPath}" />
    </c:if>
</c:forEach>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="../style/style.css" />
    <title>Title</title>
</head>
<body>

    <aside class="menu">
        <%@include file="aside.jsp"%>
    </aside>

    <div class="base">

        <header>
            <%@include file="header.jsp"%>
        </header>
        <main>
            <%@include file="main.jsp"%>
        </main>
        <footer>
            <%@include file="footer.jsp"%>
        </footer>

    </div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="main">
    <form class="registryForm">

        <div>
            <label for="name">Name</label>
            <input required id="name" type="text" name="name" min="2" max="20" pattern="[a-zA-Zа-яА-Я]+" />
        </div>

        <div>
            <label for="surname">Surname</label>
            <input required id="surname" type="text" name="surname" min="2" max="20" pattern="[a-zA-Zа-яА-Я]+" />
        </div>

        <div>
            <label for="phone">Phone</label>
            <input required id="phone" type="text" name="phone" pattern="\+375-(44|33|29)-\d{3}-\d{2}-\d{2}" />
        </div>

        <div>
            <label for="password">Password</label>
            <input required id="password" name="password" max="20">
        </div>

        <div>
            <label for="passwordRepeat">PasswordRepeat</label>
            <input required id="passwordRepeat" name="passwordRepeat" max="20">
        </div>

        <button type="submit" name="command" value="registry">Submit</button>

    </form>
</div>
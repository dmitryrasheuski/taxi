<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="main">
    <form class="orderForm">

        <div>
            <label for="phone">Phone</label>
            <input required id="phone" type="text" name="phone" pattern="\+375-(29|33|44)-\d{3}-\d{2}-\d{2}" value="${sessionScope.user.phone}">
        </div>
        <div>
            <label for="where">Where</label>
            <input required id="where" type="text" name="where" >
        </div>
        <div>
            <label for="from">From</label>
            <input required id="from" type="text" name="from" >
        </div>
        <div>
            <label for="typeCar">Type car</label>
            <select id="typeCar" name="typeCar">
                <option value="sedan">sedan</option>
                <option value="wagon">wagon</option>
                <option value="jeep">jeep</option>
            </select>
        </div>
        <div>
            <label for="comment">Comment</label>
            <textarea id="comment" name="comment"></textarea>
        </div>

        <button type="submit" name="command" value="createOrder">Submit</button>

    </form>
</div>

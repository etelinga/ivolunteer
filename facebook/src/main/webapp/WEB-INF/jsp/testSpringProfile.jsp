<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>Test Volunteer Profile</title></head>
<body>
<form:form commandName="profile">
    <form:hidden path="keyField"/>
    <form:hidden path="fb_sig_user"/>
    <div>
        <form:label path="emailOne">Email One:</form:label>
        <form:input path="emailOne"/>
        <spring:hasBindErrors name="stuff">
            <spring:bind path="stuff.emailOne">
                <c:forEach items="${status.errorMessages}" var="error">
                    Error ${error}
                </c:forEach>
            </spring:bind>
        </spring:hasBindErrors>
    </div>
    <div>
        <form:label path="enableComments">Comments:</form:label>
        <form:checkbox path="enableComments"/>
    </div>
    <div>
        <form:label path="notifyFriends">Notify:</form:label>
        <form:checkbox path="notifyFriends"/>
    </div>

    <div>
        <input type="submit" value="Save Changes"/>
    </div>
</form:form>
</body>
</html>
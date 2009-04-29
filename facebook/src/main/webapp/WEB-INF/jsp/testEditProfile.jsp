<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>Member Profile</title></head>
<body>
<form:form commandName="profile">
    <form:hidden path="keyField"/>
    <form:hidden path="fb_sig_user"/>
    <form:hidden path="formId"/>
    <c:if test="${profile.add}">
        Add Mode
    </c:if>
    <input id="keyField" name="keyField" type="hidden" value="${profile.keyField}"/>
    <input id="fb_sig_user" name="fb_sig_user" type="hidden" value="${profile.fb_sig_user}"/>
    <div>
        Email Address: <input type="text" name="emailAddress" value="${profile.emailAddress}"/>
        Zip Code: <input type="text" name="postalCode" value="${profile.postalCode}"/>
        <input type="hidden" name="_enableComments" value="on"/>
        <input type="hidden" name="_notifyFriends" value="on"/>
        <input type="hidden" name="_historyVisible" value="on"/>
        Enable Comments:
        <c:choose>
            <c:when test="${profile.enableComments}">
                <input type="checkbox" name="enableComments" value="true" checked="checked"/>
            </c:when>
            <c:otherwise>
                <input type="checkbox" name="enableComments" value="true"/>
            </c:otherwise>
        </c:choose>
        Notify Friends:
        <c:choose>
            <c:when test="${profile.notifyFriends}">
                <input type="checkbox" name="notifyFriends" value="true" checked="checked"/>
            </c:when>
            <c:otherwise>
                <input type="checkbox" name="notifyFriends" value="true"/>
            </c:otherwise>
        </c:choose>
        History Visible:
        <c:choose>
            <c:when test="${profile.historyVisible}">
                <input type="checkbox" name="historyVisible" value="true" checked="checked"/>
            </c:when>
            <c:otherwise>
                <input type="checkbox" name="historyVisible" value="true"/>
            </c:otherwise>
        </c:choose>
    </div>
    <div>
        <input type="submit" value="Save Changes"/>
    </div>
</form:form></body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:choose>
    <c:when test="${profile.add}">
    <div>
        Welcome to iVolunteer. <br/>The iVolunteer application makes it easy for you and your friends to find
        ways to make a difference in your community.
    </div>
    </c:when>
    <c:otherwise>
<div>
    Use this form to enter your e-mail address if you'd like to link the Facebook application to your iPhone.<br/>
    No need for a password since we're not going to send you e-mails from this site.<br/> There are selections
    for the type of organization you're interested in volunteering for along with a duration selection. These
    preferences are used for displaying a list of opportunities available in your area.
</div>
        </c:otherwise>
    </c:choose>
<fb:editor action="profile.xhtml" labelwidth="100">
    <fb:editor-custom><input id="keyField" name="keyField" type="hidden" value="${profile.keyField}"/><input id="formId" name="formId" type="hidden" value="${comment.formId}"/>
        <input type="hidden" name="_enableComments" value="on"/><input type="hidden" name="_notifyFriends" value="on"/></fb:editor-custom>
    <fb:editor-text label="Email Address" name="emailOne" value="${profile.emailOne}"/>
    <fb:editor-custom label="Enable Comments" id="comments"><c:choose><c:when test="${profile.enableComments}"><input type="checkbox" name="enableComments" value="true" checked="checked"/>
    </c:when><c:otherwise><input type="checkbox" name="enableComments" value="true"/></c:otherwise></c:choose>
    </fb:editor-custom><fb:editor-custom label="Notify Friends" id="notify"><c:choose><c:when test="${profile.notifyFriends}"><input type="checkbox" name="notifyFriends" value="true" checked="checked"/>
    </c:when><c:otherwise><input type="checkbox" name="notifyFriends" value="true"/></c:otherwise></c:choose>
    </fb:editor-custom>
    <fb:editor-buttonset><fb:editor-button value="Save"/><fb:editor-cancel href="home.xhtml"/></fb:editor-buttonset>
</fb:editor>

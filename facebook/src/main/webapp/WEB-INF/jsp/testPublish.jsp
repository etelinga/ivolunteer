<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>Test Publish</title></head>
<body>
<form:form commandName="publish">
    <form:hidden path="keyField"/>
    <form:hidden path="fb_sig_user"/>
    <form:hidden path="formId"/>
    <div>
        <select id="ids" name="ids[]" multiple="true">
            <option value="1">One</option>
            <option value="2">Twp</option>
            <option value="3">Three</option>
        </select>
    </div>
    <div>
        <input type="submit" value="Save Changes"/>
        <a href="home.xhtml">Home</a>
    </div>
</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<fb:fbml version="1.1">
<fb:title>Puff My Stuff</fb:title>
<%@ include file="/WEB-INF/jspf/style.jspf" %>
<fb:editor action="publish.xhtml">
    <fb:editor-custom><input id="keyField" name="keyField" type="hidden" value="${publish.keyField}"/><input id="formId" name="formId" type="hidden" value="${publish.formId}"/></fb:editor-custom>
    <div>Pick the friends you'd like to tell about <strong>${publish.description}</strong></div><br/>
    <strong style="float: left; position: relative; top: 5px;">To:</strong><fb:multi-friend-input width="450px" border_color="#8496ba"/>
    <fb:editor-buttonset><fb:editor-button value="Puff It"/><fb:editor-cancel href="home.xhtml"/></fb:editor-buttonset>
</fb:editor>
</fb:fbml>

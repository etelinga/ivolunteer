<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<fb:fbml version="1.1">
<fb:title>My Stuff</fb:title>
<%@ include file="/WEB-INF/jspf/style.jspf" %>
<c:set var="menu"><s:message code="menu.past"/></c:set>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<div id="pager"><tag:pager linkCode="mystuff.pager.link" source="${stuffs}"/></div><br/><br/>
<table id="events" class="events" cellspacing="0" border="0"><tbody>
    <c:forEach items="${events.pageList}" var="stuff"><tr class="list"><td class="list_item"><a href="eventDetails.xhtml?keyField=${stuff.keyField}">${event.description}</a>
    <br/><a href="publish.xhtml?keyField=${event.keyField}">Tell my friends where I'm volunteering.</a></td></tr></c:forEach>
</tbody></table>
</fb:fbml>

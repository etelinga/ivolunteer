<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<fb:fbml version="1.1">
<style>
    .lists th {
        text-align: left;
        padding: 5px 10px;
        background: #6d84b4;
    }

    .lists .spacer {
        background: none;
        border: none;
        padding: 0px;
        margin: 0px;
        width: 10px;
    }

    .lists th h4 {
        float: left;
        color: white;
    }

    .lists th a {
        float: right;
        font-weight: normal;
        color: #d9dfea;
    }

    .lists th a:hover {
        color: white;
    }

    .lists td {
        margin: 0px 10px;
        padding: 0px;
        vertical-align: top;
        width: 306px;
    }

    .lists .list {
        background: white none repeat scroll 0;
        border-color: -moz-use-text-color #BBBBBB;
        border-style: none solid;
        border-width: medium 1px;
    }

    .lists .list .stuff .item {
        border-top: 1px solid #E5E5E5;
        padding: 10px;
    }

    .lists .list .comment .item {
        border-top: 3px solid #E5E5E5;
        padding: 10px;
    }

    .lists .list .list_item {
        border-top: 1px solid #E5E5E5;
        padding: 10px;
    }

    .lists .list .list_item.first {
        border-top: none;
    }

    .lists .see_all {
        background: white none repeat scroll 0;
        border-color: -moz-use-text-color #BBBBBB rgb( 187, 187, 187 );
        border-style: none solid solid;
        border-width: medium 1px 1px;
        text-align: left;
    }

    .lists .see_all div {
        border-top: 1px solid #E5E5E5;
        padding: 5px 10px;
    }

    .af_normal {
        color: rgb( 33, 33, 255 );
        font-size: 14pt;
    }

</style>
<fb:title>My Events</fb:title>
<c:set var="menu"><s:message code="menu.home"/></c:set>
<%@ include file="/WEB-INF/jspf/menu.jspf" %>
<c:if test="${not empty exception}"><fb:error message="Problems, so sorry.">${exception.message}</fb:error></c:if>
<table class="lists" cellspacing="0" border="0">
    <tr><th><h4><s:message code="your.events"/></h4><a href="listEvents.xhtml"><s:message code="see.all"/></a></th></tr>
    <tr><td class="list"><div class="stuff"><c:forEach items="${events}" var="event"><div class="item">${event.description}</div></c:forEach></div></td>
    </tr><tr><td class="see_all"><div><a href="listEvents.xhtml"><s:message code="see.all"/></a></div></td></tr>
</table>

</fb:fbml>

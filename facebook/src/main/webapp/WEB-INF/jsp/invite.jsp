<%@ page import="org.actionfeed.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<fb:fbml version="1.1">
<fb:title>iVolunteer Invite</fb:title>
<fb:request-form action="home.xhtml" method="POST" invite="true" type="Puff"
content="Join me, add iVolunteer! <fb:req-choice url='http://www.facebook.com/add.php?api_key=<%=Constants.API_KEY%>' label='Join me won't you.'/>">
<fb:multi-friend-selector showborder="false" actiontext="Invite your friends to iVolunteer."
exclude_ids="${invite.excludeIds}" max="20"/></fb:request-form>
</fb:fbml>
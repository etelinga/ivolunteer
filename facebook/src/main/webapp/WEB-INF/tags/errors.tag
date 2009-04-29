<%@ tag body-content="scriptless" %>
<%@ attribute name="name" required="true" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<spring:hasBindErrors name="${name}">
<ul>
<c:forEach items="${errors.allErrors}" var="error">
<li><span class="error"><spring:message message="${error}" /></span></li>
</c:forEach>
</ul>
</spring:hasBindErrors>
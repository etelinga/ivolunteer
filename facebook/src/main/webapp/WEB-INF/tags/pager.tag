<%@ tag body-content="scriptless" %>
<%@ attribute name="source" required="true" description="The PagedListHolder"
              type="org.actionfeed.web.spring.BasePagedListHolder" %>
<%@ attribute name="linkCode" required="true" description="The code to use from the message bundle for the link." %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<s:message code="${linkCode}" var="link"/>
<c:if test="${source.pageCount > 1}"><ul id="navigation" class="pagelinks">
    <c:if test="${not source.firstPage}"><li><a href="<c:url value="${link}"><c:param name="page" value="0"/></c:url>"><s:message code="pager.first"/></a></li></c:if>
    <c:forEach begin="${source.firstLinkedPage}" end="${source.lastLinkedPage}" var="crtpg"><c:choose>
    <c:when test="${crtpg == source.page}"><li class="current">${crtpg + 1}</li></c:when>
    <c:otherwise><li><a href="<c:url value="${link}"><c:param name="page" value="${crtpg}"/></c:url>">${crtpg+1}</a></li></c:otherwise></c:choose></c:forEach>
    <c:if test="${not source.lastPage}"><li><a href="<c:url value="${link}"><c:param name="page" value="${source.maxLinkedPages}"/></c:url>"><s:message code="pager.last"/></a></li></c:if>
</ul></c:if>
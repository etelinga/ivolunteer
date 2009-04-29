<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head><title>Bad News</title></head>
  <body>The server has shot the pooch. Be back as soon as someone figures out what got into him.
  <c:if test="${not empty exception}"><strong>Error: ${exception.message}</strong></c:if>  
  </body>
</html>
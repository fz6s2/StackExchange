<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/WEB-INF/tlds/tags" prefix="f" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stack Exchange search</title>
    </head>
    <body>    
        <form method="POST" action="${pageContext.request.contextPath}/search" name="SearchForm">
            <p>
            <label>Asc 'Stack Exchange' your question: </label><br/>
            <input type="text" name="title" size="100"/>            
            </p>
            <p>
            <font color="red" >${error}</font>
            </p>
            <button type="submit">Send</button>
            <hr/>
            <c:if test="${not empty questions}">
                <table border = "0" cellspacing="0" cellpadding="5">
                    <tr><td>ID</td>
                        <td>Title</td>
                        <td>Creation Date</td>
                        <td>Answered?</td>
                        <td>Owner</td>
                    </tr>                    
                    <c:forEach items="${questions}" var="question">
                    <c:choose>
                        <c:when test="${question.isAnswered}">
                            <tr bgcolor="#FFFFE0">
                        </c:when>
                        <c:otherwise> 
                            <tr>
                        </c:otherwise>
                    </c:choose>            
                        <td>${question.questionId}</td>
                        <td><a href="${question.link}">${question.title}</a></td>                         
                        <td>${f:formatLocalDateTime(question.creationDate, 'yyyy-MM-dd hh:mm:ss')}</td>
                        <td>${question.isAnswered}</td>
                        <td><a href="${question.owner.link}">${question.owner.displayName}</a></td>
                    </tr>    
                    </c:forEach>    
                </table>
            </c:if>    
        </form>
    </body>
</html>

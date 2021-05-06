<%-- 
    Document   : applications
    Created on : May 6, 2021, 1:01:58 PM
    Author     : Home
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Applications</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="css/site.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="navigation-container">
            <div class="navbar navbar-light col-md-5">
                <a href="<c:url value="/jobs" />">View Jobs</a>
                <a href="<c:url value="/applications" />">Applications</a>
                <a href="<c:url value="/login" />">Login</a>
            </div>
        </div>
            <div class="image-container">
                <img alt="Medirevv Logo" src="images/20201119152943_MediRevv-Horizontal-Logo-Color.png">
            </div>
            <div class="list row">
                <div class="col-sm-0 col-md-1">
                </div>
                <div class="listing-container col-md-8">
                    <c:forEach items="${applicationList}" var="application">
                        <h3><a  href="<c:url value="applications"><c:param name="action" value="view"/><c:param name="id" value="${application.id}" /></c:url>">
                                ${fn:escapeXml(application.firstName)}&nbsp;${fn:escapeXml(application.lastName)}
                            </a>
                            <p>${fn:escapeXml(application.email)}</p>
                        </h3>
                    </c:forEach>
                </div>
            </div>
    </body>
</html>

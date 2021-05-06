<%-- 
    Document   : application
    Created on : May 6, 2021, 1:42:00 PM
    Author     : Home
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <title>Application</title>
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
                    <p>${fn:escapeXml(application.firstName)}&nbsp;${fn:escapeXml(application.lastName)}</p>
                    <p>${fn:escapeXml(application.email)}</p>
                    <p>${fn:escapeXml(application.phoneNumber)}</p>
                    <c:if test="${application.attachment.name} != null}">
                        <a href="
                            <c:url value="/applications">
                                <c:param name="action" value="download" />
                                <c:param name="applicationId" value="${application.id}" />
                                <c:param name="attachment" value="${application.attachment.name}" />
                            </c:url>
                        ">Download</a>
                    </c:if>
                    
                    <c:if test="${application.salary != null && application.salary != 0}">
                        <p><fmt:formatNumber currencyCode="currency" value="${application.salary}" maxFractionDigits="2"></fmt:formatNumber>
                        </p> 
                    </c:if>
                    <c:if test="${!(application.salary != null && application.salary != 0)}">
                        <p>Not Specified
                        </p> 
                    </c:if>
                    <p>${application.startDate}</p> 
                        
                </div>
            </div>
    </body>
</html>

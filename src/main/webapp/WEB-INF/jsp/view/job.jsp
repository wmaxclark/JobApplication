<%-- 
    Document   : jobList
    Created on : May 4, 2021, 7:31:20 PM
    Author     : William Clark
--%>
<%@page import="java.util.Arrays"%>
<%@page import="java.sql.Array"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.SortedSet"%>
<%@page import="clark.jobapplication.Job"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Current Openings</title>
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
            <div class="list col-md-12 row">
                <div class="col-sm-0 col-md-1">
                    
                </div>
                <div class="listing-container col-sm-10 col-md-5">
                    <h2><c:out value="${job.title}"></c:out></h2>
                    <p>${job.city}, ${job.state}</p>
                    <c:if test="${job.fullTime}">
                        <p>Full-Time</p>
                    </c:if>
                        <c:if test="${!job.fullTime}">
                        <p>Part-Time</p>
                    </c:if>
                    <p>${job.department}</p>
                    <p>Experience Desired: ${job.experience}</p>
                    <c:if test="${job.fullTime}">
                        <p><fmt:formatNumber currencyCode="currency" value="${job.salary}" maxFractionDigits="2"></fmt:formatNumber>  Annually</p>
                    </c:if>
                        <c:if test="${!job.fullTime}">
                        <p><fmt:formatNumber currencyCode="currency" value="${job.salary / 2080}" maxFractionDigits="2"></fmt:formatNumber>  Hourly</p>
                    </c:if>
                    
                    <p>${job.description}</p>
                </div>
                <div class="col-sm-10 col-md-5">
                    <form id="applicationForm" action="<c:url value="/applications" />" method="POST" class="text-center" enctype="multipart/form-data" >
                        <c:if test="${success}">
                            <p>Application Submitted Successfully!</p>
                        </c:if>
                        <input type="hidden" name="Id" value="${fn:escapeXml(job.id)}" />
                        
                        <label for="firstName">First Name:</label>
                        <input type="text" name="firstName" value="${fn:escapeXml(application.firstName)}" />
                        <p class="text-danger">${fn:escapeXml(application.firstNameError)}</p>
                        
                        <label for="lastName">Last Name:</label>
                        <input type="text" name="lastName" value="${fn:escapeXml(application.lastName)}" />
                        <p class="text-danger">${fn:escapeXml(application.lastNameError)}</p>
                        
                        <label for="email">Email:</label>
                        <input type="email" name="email" value="${fn:escapeXml(application.email)}" />
                        <p class="text-danger">${fn:escapeXml(application.emailError)}</p>
                        
                        <label for="phone">Phone Number:</label>
                        <input type="tel" name="phone" value="${fn:escapeXml(application.phoneNumber)}" />
                        <p class="text-danger">${fn:escapeXml(application.phoneNumberError)}</p>
                        
                        <label for="attachment">Resume:</label>
                        <input type="file" name="attachment" value="${fn:escapeXml(application.attachment)}/>
                        <p class="text-danger">${fn:escapeXml(application.attachmentError)}</p>
                        
                        <label for="salary">Salary:</label>
                        <input type="number" name="salary" value="<fmt:formatNumber currencyCode="currency" value="${fn:escapeXml(application.salary)}" maxFractionDigits="2"></fmt:formatNumber>" />
                        <p class="text-danger">${fn:escapeXml(application.salaryError)}</p>
                        
                        <label for="startDate">Desired Start Date:</label>
                        <input type="date" name="startDate" value="${fn:escapeXml(application.startDate)}" />
                        <p class="text-danger">${fn:escapeXml(application.startDateError)}</p>
                        
                        <input class="btn btn-primary" type="submit" value="Submit">
                    </form>
                    
                </div>
                <div class="col-sm-0 col-md-1">
                    
                </div>
            </div>
        
        
        <footer>
            <div class="footer-container">
                
            </div>
        </footer>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.2/dist/jquery.validate.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/additional-methods.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
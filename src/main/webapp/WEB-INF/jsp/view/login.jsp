<%-- 
    Document   : login
    Created on : May 6, 2021, 12:48:17 PM
    Author     : William Clark
--%>

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
            <div class="list row">
                <div class="col-sm-0 col-md-1">
                </div>
                <div class="listing-container col-md-8">
                    <h2>Login</h2>
                        <c:if test="${loginFailed}">
                            <p style="font-weight: bold;">The username or password you entered are not correct. Please try again.</p>
                        </c:if>
                        <c:if test="${!job.fullTime}">
                            <p>You must log in to access the site.</p>
                        </c:if>
                        <form method="POST" action="<c:url value="/login" />">
                            <label for="username">Username</label>
                            <input type="text" name="username" id="username" /><br><br>
                            <label for="password">Password</label>
                            <input type="password" name="password" id="password" /><br><br>
                            <input type="submit" value="Log In" />
                        </form>
                </div>
            </div>
    </body>
</html>
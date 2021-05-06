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
            <div class="list">
                <div class="listing-container col-md-10">

                    <%
                        SortedSet<Job> jobs = (SortedSet)request.getAttribute("listings");
                        int begin = Integer.parseInt(request.getAttribute("begin").toString());
                        int end = Integer.parseInt(request.getAttribute("end").toString());
                        int totalPages = Integer.parseInt(request.getAttribute("totalPages").toString());
                        int currentPage = Integer.parseInt(request.getAttribute("currentPage").toString());
                    %>
                    <br>
                    <h2>Current Openings</h2>
                    <%
                            Job[] jobarray = new Job[jobs.size()];
                            jobarray = jobs.toArray(jobarray);
                            for(int i = begin; i <= end && i <= jobs.size() - 1; i++) {
                    %>
                                <c:set var ="title" value = "<%= jobarray[i].getTitle() %>" />
                                <h3><a href="<c:url value="jobs">
                                           <c:param name="id" value="<%= String.valueOf(jobarray[i].getId()) %>"/>
                                    </c:url>">${fn:escapeXml(title)}</a></h3>
                                
                                    <p><%= jobarray[i].getCity() %>, <%= jobarray[i].getState() %> -  <%= jobarray[i].getDepartment() %></p>
                                    <br>

                                
                    <%
                            }
                    %>
                    <div class="pagination-links">
                        <%
                                for(int i = 1; i <= totalPages; i++) {
                        %>
                                    <a href="<c:url value="/jobs">
                                               <c:param name="page" value="<%= String.valueOf(i) %>"/>
                                       </c:url>" class="btn btn-primary"><%= String.valueOf(i) %></a>
                        <%
                                }
                        %>
                    </div>
                    <br>
                    
                </div>
            </div>
        
        
        <footer>
            <div class="footer-container">
                
            </div>
        </footer>
    </body>
</html>
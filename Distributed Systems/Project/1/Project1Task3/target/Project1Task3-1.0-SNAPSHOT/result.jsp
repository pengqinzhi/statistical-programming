<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%= request.getAttribute("doctype") %>

<html>
<head>
    <title>Survey Result</title>
</head>
    <body>
        <h1>Distributed Systems Class Clicker</h1>
        <% if (request.getAttribute("countA") != null || (request.getAttribute("countB") != null) || (request.getAttribute("countC") != null) || (request.getAttribute("countD") != null)) { %>
            <%--Show the result if it exists--%>
            <p>The results from the survey are as follows:</p>
            <% if (request.getAttribute("countA") != null) { %>
                A : <%= request.getAttribute("countA")%><br/>
            <% } %>
            <% if (request.getAttribute("countB") != null) { %>
                B : <%= request.getAttribute("countB")%><br/>
            <% } %>
            <% if (request.getAttribute("countC") != null) { %>
                C : <%= request.getAttribute("countC")%><br/>
            <% } %>
            <% if (request.getAttribute("countD") != null) { %>
                D : <%= request.getAttribute("countD")%><br/>
            <% } %>
            <p>These results has now been cleared.</p>
        <%--If all count are zero then show no result --%>
        <% } else { %>
             <p>There are currently no results.</p>
        <% } %>
    </body>
</html>
<%-- @author Qinzhi Peng, qinzhip--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>JSP Page</title>
    </head>
    <body>
        <p>Give me a keyword, and I'll give you an interesting picture.</p>
        <form action="getAnInterestingPicture" method="GET">
            <label for="letter">Type the word.</label>
            <input type="text" name="searchWord" id="letter" value="" /><br>
            <input type="submit" value="Click Here" />
        </form>
    </body>
</html>


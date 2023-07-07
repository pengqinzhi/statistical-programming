<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ComputeHashes</title>
    </head>
    <body>
        <h1>Compute Hashes!</h1>
        <form action="computeHashes" method="GET">
            <label for="text">Type the text:</label>
            <input type="text" name="text" id="text" value=""/><br>
            Hash function:
            <input type="radio" name="hashChoice" id="MD5" value="MD5" checked="checked" /> <label for="MD5"> MD5 </label>
            <input type="radio" name="hashChoice" id="SHA-256" value="SHA-256" /> <label for="SHA-256"> SHA-256 </label><br><br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
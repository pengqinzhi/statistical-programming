<html>
    <head>
        <title>Compute Hashes</title>
    </head>
    <body>
        <p>Original Text: <%= request.getAttribute("oriText") %></p>
        <p>Hash Function: <%= request.getAttribute("hashChoice") %></p>
        <p>Hash Value(hexadecimal): <%= request.getAttribute("hash_hex") %></p>
        <p>Hash Value(base64): <%= request.getAttribute("hash_base") %></p>
    </body>
</html>

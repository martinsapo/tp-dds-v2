<html>
<head>
    <title>File Uploading Form</title>
    <meta charset="utf-8">
    <meta name="robots" content="noindex, nofollow">

    <title>Carga de archivo de dispositivos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />
</head>

<body>
<h1>File Upload:</h1>
<h2>Select a file to upload:</h2> <br />
<form action = "${pageContext.request.contextPath}/upload" method = "post" enctype = "multipart/form-data">
    <input  type = "file" name = "file" size = "50" />
    <br />
    <input class="btn-dark" type = "submit" value = "Upload File" />
</form>
</body>

</html>
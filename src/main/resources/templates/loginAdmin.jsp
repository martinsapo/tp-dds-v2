<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="robots" content="noindex, nofollow">

    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</head>
<body>

<div class="container">
    <h1 class="title" style="color: #ffffff; text-align: center;">Sistema de gestion energetica</h1>
    <h3 class="title" style="color: #ffffff; text-align: center;">Administrador</h3>
    <hr>
    <div class="card card-container">
        <form class="form-signin" method="post" action="${pageContext.request.contextPath}/submitLoginAdmin" modelAttribute="userFormData">
            <input type="text" id="inputUsuario" name="username" class="form-control" placeholder="Usuario" required autofocus>
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Contrasena" required>
            <c:choose>
                <c:when test="${empty msg}">

                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger" role="alert">
                            ${msg}
                    </div>
                </c:otherwise>
            </c:choose>


            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Ingresar</button>
        </form>
    </div>
    <form method="get" action="${pageContext.request.contextPath}/goToMapFromAdmin">
        <div class="map inner">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Ir a mapa</button>
        </div>
    </form>

    <form method="get" action="${pageContext.request.contextPath}/goToClientLogin">
        <div class="map inner">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Ir a login de cliente</button>
        </div>
    </form>
</div>
</body>
</html>

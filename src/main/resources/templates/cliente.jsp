<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: QAD
  Date: 28/10/2018
  Time: 6:20 p.m.
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="robots" content="noindex, nofollow">

    <title>Cliente</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <h4><span class="glyphicon glyphicon-user white"> ${userFormData.username} </span></h4>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown"
            aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbarNavDropdown" class="navbar-collapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link">Bienvenido!</a>
            </li>
        </ul>
        <ul class="navbar-nav">
            <li class="nav-item float-right">
                <h4><a class="nav-link" href="${pageContext.request.contextPath}/logout">Salir</a></h4>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="card card-container">
        <form class="form-signin" method="get" action="${pageContext.request.contextPath}/submithogar" modelAttribute="estadoHogar">
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Estado del Hogar</button>
        </form>

        <!-- Consumo de cliente luego de seleccionar un periodo -->
        <form class="form-signin" method="post" action="${pageContext.request.contextPath}/submitconsum" modelAttribute="consumo">
            <input type="date" name="fechaInicio" required>
            <input type="date" name="fechaFin" required>
            <button class="btn btn-lg btn-primary btn-block btn-signin " type="submit">Consumo Periodo</button>
        </form>
        <!-- Visualizar consumos -->
        <c:if test="${not empty consumoPorPeriodo}">
            <p>El consumo es de: ${consumoPorPeriodo}</p>
        </c:if>

        <form class="form-signin" method="post" action="${pageContext.request.contextPath}/submitcarga" modelAttribute="cargaArchivo">
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Cargar Archivo Dispositivos</button>
        </form>

        <form class="form-signin" method="get" action="${pageContext.request.contextPath}/submitsimplex" modelAttribute="userFormData" >
        <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Ejecutar Simplex</button>
        </form>

        <form class="form-signin" method="post" action="${pageContext.request.contextPath}/submitreglas" modelAttribute="reglasYDispositvos">
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">ABM Reglas</button>
        </form>

        <form class="form-signin" method="post" action="${pageContext.request.contextPath}/submitdispositivos" modelAttribute="reglasYDispositvos">
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">ABM Dispositivos</button>
        </form>
    </div>
</div>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: msaposnic
  Date: 10/12/2018
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ABM de reglas</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css">
    <script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="html" uri="http://www.springframework.org/tags/form" %>
</head>
<body>

<form class="form-signin" method="get" action="${pageContext.request.contextPath}/altaRegla" modelAttribute="cargaArchivo">
    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Dar de alta regla</button>
</form>

<h1>Reglas activas</h1>
<table style="width:100%">
    <tr>
        <th>Id de regla</th>
        <th>Id de dispositivo</th>
        <th>Sensor</th>
        <th>Accion</th>
        <th>Operador de la condicion</th>
        <th>Medicion esperada</th>
        <th>Eliminar</th>
    </tr>
    <c:forEach items="${reglas}" var="reglas">
        <tr>
            <td>${reglas.reglaId}</td>
            <td>${reglas.dispositivoId}</td>
            <td>${reglas.sensorId}</td>
            <td>${reglas.accionARealizar}</td>
            <td>${reglas.operador}</td>
            <td>${reglas.medicion}</td>
            <td>
                <form method="get" action="${pageContext.request.contextPath}/eliminarRegla">
                    <input type="hidden" name="id" value="${reglas.reglaId}"/>
                    <button class="btn" type="submit">Eliminar</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

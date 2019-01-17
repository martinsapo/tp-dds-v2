<!DOCTYPE html>
<html>
<head>
    <title>Info de hogar</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css">
    <script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="html" uri="http://www.springframework.org/tags/form" %>
</head>
<body style="padding-left:20px;padding-right: 20px; ">

<h1 class="title" style="color: #ffffff; text-align: center;">Info de hogar</h1>
<div id="estadoDeDispositivos">
    <h1>Estado de los dispositivos</h1>
    <table style="width:100%">
        <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Estado</th>
        </tr>
        <c:forEach items="${dispositivos}" var="dispositivos">
            <tr>
                <td>${dispositivos.id}</td>
                <td>${dispositivos.nombreDelDispositivo}</td>
                <td>${dispositivos.obtenerEstadoDelDispositivo()}</td>
            </tr>
        </c:forEach>
    </table>
    <h1>Reglas activas</h1>
    <table style="width:100%">
        <tr>
            <th>Id de regla</th>
            <th>Id de dispositivo</th>
            <th>Sensor</th>
            <th>Accion</th>
            <th>Operador de la condicion</th>
            <th>Medicion esperada</th>
        </tr>
        <c:forEach items="${reglas}" var="reglas">
            <tr>
                <td>${reglas.reglaId}</td>
                <td>${reglas.dispositivoId}</td>
                <td>${reglas.sensorId}</td>
                <td>${reglas.accionARealizar}</td>
                <td>${reglas.operador}</td>
                <td>${reglas.medicion}</td>
            </tr>
        </c:forEach>
    </table>
    <h1>Consumo en el ultimo periodo: ${consumo.toString()}</h1>

    <h1>Ultimas mediciones</h1>
    <table style="width:100%">
        <tr>
            <th>Id de la medicion</th>
            <th>Id del dispositivo</th>
            <th>Id del sensor</th>
            <th>Medicion</th>
        </tr>
        <c:forEach items="${mediciones}" var="medicion">
            <tr>
                <td>${medicion.idMedicion}</td>
                <td>${medicion.idDispositivo}</td>
                <td>${medicion.idSensor}</td>
                <td>${medicion.medicionValor}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
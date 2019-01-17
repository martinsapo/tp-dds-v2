<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

        <meta charset="utf-8">
        <meta name="robots" content="noindex, nofollow">

        <title>Reportes</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />

</head>
<body>

<form class="form-signin" method="post" action="${pageContext.request.contextPath}/consumoPorHogar" modelAttribute="consumo">
    <input type="date" name="fechaInicioHogar" required>
    <input type="date" name="fechaFinHogar" required>
    <button class="btn btn-lg btn-primary btn-block btn-signin " type="submit">Consumo por Hogar</button>
</form>
<form class="form-signin" method="post" action="${pageContext.request.contextPath}/consumoPromedioPorTipo" modelAttribute="consumo">
    <input type="date" name="fechaInicioPromedio" required>
    <input type="date" name="fechaFinPromedio" required>
    <button class="btn btn-lg btn-primary btn-block btn-signin " type="submit">Consumo promedio por Tipo de Dispositivo</button>
</form>
<form class="form-signin" method="post" action="${pageContext.request.contextPath}/consumoPorTransformador" modelAttribute="consumo">
    <input type="date" name="fechaInicioTransformador" required>
    <input type="date" name="fechaFinTransformador" required>
    <button class="btn btn-lg btn-primary btn-block btn-signin " type="submit">Consumo por Transformador</button>
</form>


<c:if test="${not empty hogares}">
    <table style="width:70%; margin-left:15%; margin-right:15%; text-align:center; background-color:snow; border: 1px solid black;">
        <tr>
            <th style="border: 1px solid black; text-align:center; ">Hogar</th>
            <th style="border: 1px solid black; text-align:center; ">Consumo</th>
        </tr>

        <c:forEach var="hogar" items="${hogares}" varStatus="loop">
            <tr>
                <td style="border: 1px solid black;">${hogar.getCliente().getNombre()}</td>
                <td style="border: 1px solid black;">${hogar.getCliente().calcularConsumoEnUnPeriodo(startDate, endDate)}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${(not empty consumoPromedioEstandar) || (not empty consumoPromedioInteligente)}">
    <h4>Consumo Promedio por Tipo de Dispositivo</h4>
    <table style="width:70%; margin-left:15%; margin-right:15%; text-align:center; background-color:snow; border: 1px solid black;">
        <tr>
            <th style="border: 1px solid black; text-align:center; ">Estandar</th>
            <th style="border: 1px solid black; text-align:center; ">Inteligente</th>
        </tr>
        <tr>
            <td style="border: 1px solid black;">${consumoPromedioEstandar}</td>
            <td style="border: 1px solid black;">${consumoPromedioInteligente}</td>
        </tr>
    </table>
</c:if>

<c:if test="${not empty transformadores}">
    <table style="width:70%; margin-left:15%; margin-right:15%; text-align:center; background-color:snow; border: 1px solid black;">
        <tr>
            <th style="border: 1px solid black; text-align:center; ">Transformador</th>
            <th style="border: 1px solid black; text-align:center; ">Consumo</th>
        </tr>

        <c:forEach var="transformador" items="${transformadores}" varStatus="loop">
            <tr>
                <td style="border: 1px solid black;">${transformador.getId()}</td>
                <td style="border: 1px solid black;">${transformador.calcularConsumoEnUnPeriodo(startTime, endTime)}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>

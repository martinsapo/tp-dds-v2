<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="robots" content="noindex, nofollow">

    <title>Hogares y Consumos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>


</head>
<body>

<%--
<h1>${transformadores}</h1>
<h1>${transformadores[0]}</h1>
<h1>${transformadores[0].getTransformadores()}</h1>
<h1>${transformadores[0].getTransformadores().get(0)}</h1>
<h1>${transformadores[0].getTransformadores().get(0).getListaDeHogares()}</h1>
<h1>${transformadores[0].getTransformadores().get(0).getListaDeHogares().get(0)}</h1>
<h1>${transformadores[0].getTransformadores().get(0).getListaDeHogares().get(0).getCliente()}</h1>
<h1>${transformadores[0].getTransformadores().get(0).getListaDeHogares().get(0).getCliente().getNombre()}</h1>
<h1>${transformadores[0].getTransformadores().get(0).getListaDeHogares().get(0).getCliente().getDispositivosInteligentes()}</h1>
--%>

<div class="container">
    <h1 class="title" style="color: #ffffff; text-align: center;">Consumo de Hogares</h1>
    <hr>
        <%--
        <table style="width:70%; margin-left:15%; margin-right:15%; text-align:center; background-color:snow;">
            <tr>
                <th style="text-align:center;">ID Hogar</th>
                <th style="text-align:center;">Consumo</th>
            </tr>
            <c:forEach var="transformador" items="${transformadores}" varStatus="loop">
                <tr>
                    <td>${transformador.getNombre()}</td>
                    <td>${transformador.getEstado()}</td>
                    <!-- <td>${transformador[loop.index].getNombre()}</td> Como hacer el for loop en JSTL-->
                </tr>
            </c:forEach>
        </table>
        --%>

    <table style="width:70%; margin-left:15%; margin-right:15%; text-align:center; background-color:snow; border: 1px solid black;">
        <c:forEach var="zonaGeografica" items="${zonasGeograficas}" varStatus="loop">
            <c:forEach var="transformador" items="${zonaGeografica.getTransformadores()}" varStatus="loop">
                <tr>
                    <th style="border: 1px solid black; text-align:center; ">Transformador</th>
                    <th style="border: 1px solid black; text-align:center; ">${transformador.getId()}</th>
                </tr>

                <c:forEach var="hogar" items="${transformador.getListaDeHogares()}" varStatus="loop">
                    <tr>
                        <td style="border: 1px solid black;">${hogar.getCliente().getNombre()}</td>
                        <td style="border: 1px solid black;">${hogar.getCliente().obtenerTodosLosConsumosDeTodosLosDispositivosEnElUltimoMes()}</td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </c:forEach>
    </table>

</div>
</body>
</html>
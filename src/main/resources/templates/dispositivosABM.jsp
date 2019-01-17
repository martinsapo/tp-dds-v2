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
    <script>
        function doChange()
        {
            var val = document.getElementById("dropdown").value;
            var e = document.getElementById("inputKw");
            e.value = val;
        }
    </script>
</head>
<body>

<div class="container">
    <div class="card card-container">
        <form class="form-signin" method="get" action="${pageContext.request.contextPath}/asociarDispositivo" modelAttribute="dispositivo">
            <div>
                <h3>Seleccione dispositivo</h3>
                <select id ="dropdown" title="catalogo" name="catalogo" path="catalogoElegido">
                    <c:forEach items="${dispositivosDelCatalogo}" var="catalogo">
                        <option value="${catalogo.id}">${catalogo.nombre}</option>
                    </c:forEach>
                </select>
            </div>
            <br>
            <c:choose>
                <c:when test="${empty msg}">

                </c:when>
                <c:otherwise>
                    <div class="alert alert-info" role="alert">
                            ${msg}
                    </div>
                </c:otherwise>
            </c:choose>

            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Asociar Dispositivo</button>


        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.min.js"></script>
</body>
</html>

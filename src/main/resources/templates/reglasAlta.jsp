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
</head><div>
        <h3>Acciones posibles</h3>
        <select title="acciones" name="accion" path="accionARealizar">
            <c:forEach items="${accionesPosibles}" var="accion">
                <option value="${accion.name()}">${accion.name()}</option>
            </c:forEach>
        </select>
    </div>
<body>
<form class="form-signin" method="get" action="${pageContext.request.contextPath}/reglasABM" modelAttribute="cargaArchivo">
    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Volver</button>
</form>

<form method="get" action="${pageContext.request.contextPath}/guardarRegla" modelAttribute="regla">
    <div>
        <h3>Acciones posibles</h3>
        <select title="acciones" name="accion" path="accionARealizar">
            <c:forEach items="${accionesPosibles}" var="accion">
                <option value="${accion.name()}">${accion.name()}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <h3>Dispositivo</h3>
        <select title="dispositivo" name="dispositivo" path="dispositivoId">
            <c:forEach items="${dispositivosInteligentes}" var="dispositivo">
                <option value="${dispositivo.id}">${dispositivo.nombreDelDispositivo}</option>
            </c:forEach>
        </select>
    </div>

    <div>
        <h3>Condicion</h3>
        <select title = "tipoDeCondicion" name="tipoCondicion" path="tipoDeCondicion">
            <option value="valor">Por valor</option>
            <option value="binaria">Binaria</option>
            <option value="consumo">Por consumo mensual</option>
        </select>
        <select name="operador" path="operador">
            <c:forEach items="${operadores}" var="operador">
                <option value="${operador.name()}">${operador.name()}</option>
            </c:forEach>
        </select>
        <label>Valor (si aplica)</label>
        <input title="valor" type="text" name="value" path="medicion"/>
    </div>

    <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Gurdar</button>
</form>
</body>
</html>


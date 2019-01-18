<%@ taglib prefix="c" uri=""%>
<!DOCTYPE html>
<html>
<head>
    <title>Mapa</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css">
    <script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js"></script>
</head>
<body style="padding-left:20px;padding-right: 20px; ">

<h1 class="title" style="color: #ffffff; text-align: center;">Mapa de transformadores</h1>
<hr>

<div id="mapa" style="height: 500px">

</div>
</body>
</html>
<script type="text/javascript">

    $( document ).ready(function() {
        mapa = L.map('mapa', {
            center: [-34.598313, -58.463745],
            zoom: 10,
            minZoom: 4,
            maxZoom:17,
            zoomControl:true
        });
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: ''}).addTo(mapa);

        <c:forEach items="${list}" var="entity">

            var marker = L.marker([${entity.latitud}, ${entity.longitud}]).addTo(mapa).bindPopup("<label class=\"title\" style=\"color: #7a7f87; text-align: center;\"> ${entity.consumo} </label>");

        </c:forEach>

    });
</script>
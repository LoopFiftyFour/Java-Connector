<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GetEntitiesByAttribute</title>
</head>
<body>
<h1>GetEntitiesByAttribute</h1>
<hr/>
<h2>Results</h2>
<div>${results.count} total</div>
<table>
    <tr>
        <th></th>
        <th>Name</th>
        <th>Price</th>
        <th>Category</th>
        <th>Manufacturer</th>
    </tr>
    <c:forEach items="${results.results}" var="i">
        <tr>
            <td><img src="${i.imageUrl}" height="100" width="100"/></td>
            <td>${i.name}</td>
            <td>${i.price}</td>
            <td>${i.category}</td>
            <td>${i.manufacturer}</td>
        </tr>
    </c:forEach>
</table>
<hr/>
<h2>Facets</h2>
<c:forEach items="${results.distinctFacets}" var="i">
    <div>${i.name}</div>
    <ul>
        <c:forEach items="${i.options}" var="p">
            <li>${p.name} (${p.count}) (selected=${p.selected})</li>
        </c:forEach>
    </ul>
</c:forEach>
<c:forEach items="${results.rangeFacets}" var="i">
    <div>${i.name}</div>
    <div>Min: ${i.min}</div>
    <div>Max: ${i.max}</div>
</c:forEach>
<hr/>
<a href="<c:url value="/index" />">Back to index</a>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Suggest</title>
</head>
<body>
<h1>Suggest</h1>
<hr/>
<div>Query: ${results.query}</div>
<div>Original query: ${results.originalQuery}</div>
<div>Query used for search: ${results.queryUsedForSearch}</div>
<div>Make sense: ${results.makesSense}</div>
<hr/>

<h2>Query suggestions (${results.queryCount})</h2>
<ul>
    <c:forEach items="${results.queries}" var="i">
        <li>${i}</li>
    </c:forEach>
</ul>

<h2>Scoped query (query: ${results.scopedQuery}, scopes based on ${results.scopeAttribute})</h2>
<ul>
    <c:forEach items="${results.scoped}" var="i">
        <li>${i}</li>
    </c:forEach>
</ul>
<hr/>

<h2>Results</h2>
<div>${results.productCount} total</div>
<table>
    <tr>
        <th></th>
        <th>Id</th>
        <th>Type</th>
        <th>Name</th>
        <th>Price</th>
        <th>Category</th>
        <th>Manufacturer</th>
    </tr>
    <c:forEach items="${results.results}" var="i">
        <tr>
            <td><img src="${i.imageUrl}" height="100" width="100"/></td>
            <td>${i.id}</td>
            <td>${i.type}</td>
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

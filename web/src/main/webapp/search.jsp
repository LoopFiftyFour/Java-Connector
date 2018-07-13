<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<body>
<h1>Search</h1>
<hr/>
<div>Query: ${results.query}</div>
<div>Make sense: ${results.makesSense}</div>
<hr/>
<h2>Related queries</h2>
<ul>
    <c:forEach items="${results.relatedQueries}" var="i">
        <li>${i}</li>
    </c:forEach>
</ul>
<hr/>
<h2>Spelling suggestions</h2>
<ul>
    <c:forEach items="${results.spellingSuggestions}" var="i">
        <li>${i}</li>
    </c:forEach>
</ul>
<h2>Results</h2>
<div>${results.count} total</div>
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
<h2>Related Results</h2>
<div>${results.relatedCount} total</div>
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
    <c:forEach items="${results.relatedResults}" var="i">
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

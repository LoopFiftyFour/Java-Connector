<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AutoComplete</title>
</head>
<body>
<h1>AutoComplete</h1>
<hr/>
<div>Query: ${results.query}</div>

<h2>Autocomplete queries (${results.count})</h2>
<ul>
    <c:forEach items="${results.results}" var="i">
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
<a href="<c:url value="/index" />">Back to index</a>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<h1>Index</h1>
<ul>
    <li><a href="<c:url value="/search"/>">Search</a></li>
    <li><a href="<c:url value="/search/selectFacet"/>">Search with selected facet</a></li>
    <li><a href="<c:url value="/getentities"/>">GetEntities</a></li>
    <li><a href="<c:url value="/getrelatedentities"/>">GetRelatedEntities</a></li>
    <li><a href="<c:url value="/getentitiesbyattribute"/>">GetEntitiesByAttribute</a></li>
    <li><a href="<c:url value="/autocomplete"/>">AutoComplete</a></li>
    <li><a href="<c:url value="/createevents"/>">CreateEvents</a></li>
</ul>
</body>
</html>

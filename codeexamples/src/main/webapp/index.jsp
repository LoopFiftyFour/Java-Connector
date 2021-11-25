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
    <li><a href="<c:url value="/faceting"/>">Faceting</a></li>
    <li><a href="<c:url value="/categorylisting"/>">CategoryListing</a></li>
    <li><a href="<c:url value="/autocomplete"/>">AutoComplete</a></li>    
    <li><a href="<c:url value="/eventtracking"/>">EventTracking</a></li>    
    <li><a href="<c:url value="/getbasketrecommendations"/>">GetBasketRecommendations</a></li>
    <li><a href="<c:url value="/sync"/>">Sync</a></li> 	
</ul>
</body>
</html>

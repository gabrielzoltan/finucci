<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setHeader("Expires","0");

%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>User login</title>
    </head>
    <body>
        <h2>Please login</h2>
        <span>Username:</span><input type="text" />
        <br/>
        <span>Password:</span><input type="password" />
        <br/>
        <input type="button" value="Login" onclick="location.href='${redirectUri}&authorization_code=authcode';" />
    </body>
</html>
<%-- 
    Document   : getdata
    Created on : 15-abr-2013, 16:14:06
    Author     : oswaldomaestra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach  var="productos" items="${productosCategoria}" >
    <c:if test="${fn:startsWith(fn:toLowerCase(productos.producto.nombreProducto), param.q)}">
        
        <c:out value="${productos.producto.nombreProducto}"></c:out>
        
    </c:if>
    
</c:forEach>

<%-- 
    Document   : getURL
    Created on : 19-abr-2013, 15:23:09
    Author     : oswaldomaestra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        
        <c:out value="<li><a href='<c:out value=${actualURL}></c:out>'><c:out value=${categoriaSeleccionada.nombreCategoria}></c:out></a>
                        </li>"></c:out>
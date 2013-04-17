<%-- 
    Document   : index
    Created on : 15-abr-2013, 15:58:39
    Author     : oswaldomaestra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var='view' value='/index' scope='session' />
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/JShoP/css/affablebean.css">

        <script src="/JShoP/js/jquery-1.4.2.js" type="text/javascript"></script>
        <script src="/JShoP/js/jquery-ui-1.8.4.custom.min.js" type="text/javascript"></script>
        <script src="/JShoP/js/jqautocomplete.js"></script>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    
        <div id="indexRightColumn">
        <c:forEach var="category" items="${categories}">
            
            <c:if test="${category.fkCategoria == null}">
                
                <div class="categoryBox">
                    <a href="<c:url value='categoria?${category.idCategoria}'/>">
                        <fmt:bundle basename="modelo.Categoria">
                        <span class="categoryLabel"></span>
                        <span class="categoryLabelText"><fmt:message key='${category.nombreCategoria}'/></span>

                        <img src="${initParam.categoryImagePath}${category.nombreCategoria}.png"
                         alt="<fmt:message key='${category.nombreCategoria}'/>" class="categoryImage">
                        </fmt:bundle>
                    </a>
                </div>
                    
            </c:if>
        </c:forEach>
        </div>
        
</body>
    </body>
</html>

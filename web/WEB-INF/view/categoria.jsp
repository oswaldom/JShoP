<%-- 
    Document   : categoria
    Created on : 15-abr-2013, 16:15:14
    Author     : oswaldomaestra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/SearchTest2/css/jqautocomplete.css">
        <link rel="stylesheet" type="text/css" href="/SearchTest2/css/affablebean.css">

        <script src="/SearchTest2/js/jquery-1.4.2.js" type="text/javascript"></script>
        <script src="/SearchTest2/js/jquery-ui-1.8.4.custom.min.js" type="text/javascript"></script>
        <script src="/SearchTest2/js/jqautocomplete.js"></script>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:set var="view" value="/categoria" scope="session" />


<%-- HTML markup starts below --%>


<div id="categoryLeftColumn">

    <c:forEach var="category" items="${subCategorias}">

        <c:choose>
            <c:when test="${category.nombreCategoria == selectedCategory.nombreCategoria}">
                
                <div class="categoryButton" id="selectedCategory">
                    <span class="categoryText">
                        <fmt:message key="${category.nombreCategoria}"/>
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='categoria?${category.idCategoria}'/>" class="categoryButton">
                    <span class="categoryText">
                        <fmt:message key="${category.nombreCategoria}"/>
                    </span>
                </a>
            </c:otherwise>
        </c:choose>

    </c:forEach>

</div>


<div id="categoryRightColumn">

    <p id="categoryTitle"><fmt:message key="${selectedCategory.nombreCategoria}" /></p>
    
    <c:if test="${selectedCategory.fkCategoria != null}">
        
        <div id='search-box'>
            <form action='' id='search-form' method='get' target='_top'>
                <input id='search-text' name='q' placeholder='Buscar producto...' type='text'/>
                <button id='search-button' type='submit'><span>Search</span></button>
            </form>
                
            <script>
                $("#search-text").autocomplete("getdata.jsp");
            </script>
      
        </div>

    <table id="productTable">

        <c:forEach var="product" items="${productosCategoria}" varStatus="iter">

            <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                <td>
                    <img src="${initParam.productImagePath}${product.producto.nombreProducto}.jpg"
                         alt="<fmt:message key='${product.producto.nombreProducto}'/>">
                </td>

                <td>
                    <fmt:message key="${product.producto.nombreProducto}"/>
                    <br>
                    <span class="smallText"><fmt:message key='${product.producto.nombreProducto}Description'/></span>
                </td>

                <td><fmt:formatNumber type="currency" currencySymbol="&euro; " value="${product.producto.precioProducto}"/></td>

                <td>
                    <form action="<c:url value='addToCart'/>" method="post">
                        <input type="hidden"
                               name="productId"
                               value="${product.producto.idProducto}">
                        <input type="submit"
                               name="submit"
                               value="<fmt:message key='addToCart'/>">
                    </form>
                </td>
            </tr>

        </c:forEach>

    </table>
        </c:if>
</div> 
        
        
    </body>
</html>

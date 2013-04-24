<%-- 
    Document   : buscar
    Created on : 21-abr-2013, 18:54:36
    Author     : oswaldomaestra
--%>

<div id="categoryRightColumn">

    <p id="categoryTitle">${mensaje}</p>
    
    <c:if test="${categoriaSearch.categoriaList.isEmpty()}">
        
        <c:forEach var="productos" items="${categoriaSearch.productoCategoriaList}" varStatus="iter">
            
            <c:set var="busqueda" value="${busqueda}"></c:set>
            <c:if test="${fn:startsWith(fn:toLowerCase(productos.producto.nombreProducto), (busqueda) )}">
            
                <div align="center" class="productDiv">

                <td class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                
                <img src="${initParam.productImagePath}${productos.producto.idProducto}.jpg"
                         alt="${productos.producto.nombreProducto}">
                    
                    <div align="center" class="productDescription">
                        <a href="<c:url value='producto?${productos.producto.idProducto}'/>">
                
                            <span class="productLabel"></span>
                            <span class="productDescription">${productos.producto.nombreProducto}</span>

                        </a>
                    

                    <br>
                    <span class="smallText">${productos.producto.descripcionProducto}</span>
                    &euro; ${productos.producto.precioProducto}
                    <%-- <form id="slick-login" action="<c:url value='anadirAlCarrito'/>" method="post">
                        <input type="hidden"
                               name="productId"
                               value="${producto.idProducto}">
                        <input type="submit"
                               name="submit"
                               value="add to cart">
                    </form>--%>
                    </div>
            
                </div>
            </c:if>
        </c:forEach>

        
        
        </c:if>
</div>
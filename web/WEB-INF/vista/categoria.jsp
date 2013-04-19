<%--
    Document   : category
    Created on : May 21, 2010, 12:20:23 AM
    Author     : tgiunipero
--%>


<div id="categoryLeftColumn">

    <c:forEach var="subCategoria" items="${categoriaSeleccionada.categoriaList}">
        <div class="categoryBox">
        <c:choose>
            <c:when test="${subCategoria.nombreCategoria == categoriaSeleccionada.nombreCategoria}">
                
                <div class="categoryButton" id="selectedCategory">${subCategoria.nombreCategoria}</div>
            
            </c:when>
                
            <c:otherwise>
                
                <a href="<c:url value='categoria?${subCategoria.idCategoria}'/>">
                    <div class="categoryButton">${subCategoria.nombreCategoria}</div>
                </a>
                         
            </c:otherwise>
        </c:choose>
</div>
    </c:forEach>

</div>

<div id="categoryRightColumn">

    <p id="categoryTitle">${categoriaSeleccionada.nombreCategoria}</p>
    
    <c:if test="${categoriaSeleccionada.categoriaList.isEmpty()}">
        
        <c:forEach var="productos" items="${categoriaSeleccionada.productoCategoriaList}" varStatus="iter">
            
            <div align="center" class="productDiv">

            <td class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                
                <img src="${initParam.productImagePath}${productos.producto.nombreProducto}.jpg"
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
        </c:forEach>

        
        
        </c:if>
</div>
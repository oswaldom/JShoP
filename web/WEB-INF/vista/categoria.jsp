<%--
    Document   : category
    Created on : May 21, 2010, 12:20:23 AM
    Author     : tgiunipero
--%>


<div id="categoryLeftColumn">

    <c:forEach var="categoria" items="${categorias}">

        <c:choose>
            <c:when test="${categoria.nombreCategoria == categoriaSeleccionada.nombreCategoria}">
                <div class="categoryButton" id="selectedCategory">
                    <span class="categoryText">
                        ${categoria.nombreCategoria}
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='categoria?${categoria.idCategoria}'/>" class="categoryButton">
                    <span class="categoryText">
                        ${categoria.nombreCategoria}
                    </span>
                </a>
            </c:otherwise>
        </c:choose>

    </c:forEach>

</div>

<div id="categoryRightColumn">

    <p id="categoryTitle">${categoriaSeleccionada.nombreCategoria}</p>

    <table id="productTable">

        <c:forEach var="producto" items="${productosCategoria}" varStatus="iter">

            <td class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                <td>
                    <%--<img src="${initParam.productImagePath}${product.name}.png"
                         alt="${product.name}">--%>
                    <a href="<c:url value='producto?${producto.producto.idProducto}'/>">
                <span class="productLabel"></span>
                <span class="productLabelText">${producto.producto.nombreProducto}</span>

            </a>

                    <br>
                    <span class="smallText">${producto.producto.descripcionProducto}</span>
                    &euro; ${producto.producto.precioProducto}
                    <%-- <form id="slick-login" action="<c:url value='anadirAlCarrito'/>" method="post">
                        <input type="hidden"
                               name="productId"
                               value="${producto.idProducto}">
                        <input type="submit"
                               name="submit"
                               value="add to cart">
                    </form>--%>
                </td>
            </td>
            <tr></tr>
        </c:forEach>

    </table>
</div>
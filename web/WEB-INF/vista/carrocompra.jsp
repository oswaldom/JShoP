<%-- 
    Document   : carrocompra
    Created on : 22-abr-2013, 21:56:12
    Author     : oswaldomaestra
--%>

<c:set var="view" value="/cart" scope="session"/>


<%-- HTML markup starts below --%>

<div id="singleColumn">

    <c:choose>
        <c:when test="${carrito.numeroDeItems > 1}">
            <p><c:out value="yourCartContains"/> ${carrito.numeroDeItems} <c:out value="items"/>.</p>
        </c:when>
        <c:when test="${carrito.numeroDeItems == 1}">
            <p><c:out value="yourCartContains"/> ${carrito.numeroDeItems} <c:out value="item"/>.</p>
        </c:when>
        <c:otherwise>
            <p><c:out value="yourCartEmpty"/></p>
        </c:otherwise>
    </c:choose>

    <div id="actionBar">
        <%-- clear cart widget --%>
        <c:if test="${!empty carrito && carrito.numeroDeItems != 0}">

            <c:url var="url" value="verCarrito">
                <c:param name="clear" value="true"/>
            </c:url>

            <a href="${url}" class="bubble hMargin"><c:out value="clearCart"/></a>
        </c:if>

        <%-- continue shopping widget --%>
        <c:set var="value">
            <c:choose>
                <%-- if 'selectedCategory' session object exists, send user to previously viewed category --%>
                <c:when test="${!empty categoriaSeleccionada}">
                    categoria
                </c:when>
                <%-- otherwise send user to welcome page --%>
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>

        <c:url var="url" value="${value}"/>
        <a href="${url}" class="bubble hMargin"><c:out value="continueShopping"/></a>

        <%-- checkout widget --%>
        <c:if test="${!empty carrito && carrito.numeroDeItems != 0}">
            <a href="<c:url value='checkout'/>" class="bubble hMargin"><c:out value="proceedCheckout"/></a>
        </c:if>
    </div>

    <c:if test="${!empty carrito && carrito.numeroDeItems != 0}">

      <h4 id="subtotal"><c:out value="subtotal"/>:
          <fmt:formatNumber type="currency" currencySymbol="&euro; " value="${carrito.subtotal}"/>
      </h4>

      <table id="cartTable">

        <tr class="header">
            <th><c:out value="producto"/></th>
            <th><c:out value="nombre"/></th>
            <th><c:out value="precio"/></th>
            <th><c:out value="cantidad"/></th>
        </tr>

        <c:forEach var="carritoItem" items="${carrito.items}" varStatus="iter">

          <c:set var="producto" value="${carritoItem.producto}"/>

          <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
            <td>
                <img src="${initParam.productImagePath}${producto.idProducto}.jpg"
                             alt="${producto.idProducto}"
                             width="25">
            </td>

            <td><c:out value="${producto.nombreProducto}"/></td>

            <td>
                <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${carritoItem.total}"/>
                <br>
                <span class="smallText">(
                    <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${producto.precioProducto}"/>
                    / <c:out value="unidad"/> )</span>
            </td>

            <td>
                <form action="<c:url value='updateCart'/>" method="post">
                    <input type="hidden"
                           name="productId"
                           value="${producto.idProducto}">
                    <input type="text"
                           maxlength="2"
                           size="2"
                           value="${carritoItem.cantidad}"
                           name="quantity"
                           style="margin:5px">
                    <input type="submit"
                           name="submit"
                           value="<c:out value='update'/>">
                </form>
            </td>
          </tr>

        </c:forEach>

      </table>

    </c:if>
</div>

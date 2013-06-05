<%-- 
    Document   : carrocompra
    Created on : 22-abr-2013, 21:56:12
    Author     : oswaldomaestra
--%>

<c:set var="view" value="/cart" scope="session"/>


<%-- HTML markup starts below --%>

<div id="columnaDerechaPerfil">

    <c:choose>
        <c:when test="${carrito.numeroDeItems >= 1}">
            <p style="text-align: center; font-size: 14px;"><c:out value="Su carrito contiene: "/> ${carrito.numeroDeItems} <c:out value="items"/>.</p>
        </c:when>
        <c:otherwise>
            <p style="text-align: center; font-size: 14px;"><c:out value="Su carrito esta vacio."/></p>
        </c:otherwise>
    </c:choose>

    <div id="actionBar">
        <%-- clear cart widget --%>
        <c:if test="${!empty carrito && carrito.numeroDeItems != 0}">

            <c:url var="url" value="verCarrito">
                <c:param name="clear" value="true"/>
            </c:url>

            <a href="${url}" class="btn btn-info"><c:out value="limpiarCarrito"/></a>
        </c:if>

        <c:set var="value">
            <c:choose>
                <c:when test="${!empty categoriaSeleccionada}">
                    categoria
                </c:when>
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>

        <c:url var="url" value="${value}"/>
        <a href="${url}" class="btn btn-info"><c:out value="continuarComprando"/></a>

        <c:if test="${!empty carrito && carrito.numeroDeItems != 0}">
            <a href="<c:url value='checkout'/>" class="btn btn-info"><c:out value="procederPago"/></a>
        </c:if>
    </div>

    <c:if test="${!empty carrito && carrito.numeroDeItems != 0}">

      <h4 id="subtotal"><c:out value="subtotal"/>:
          <fmt:formatNumber type="currency" value="${carrito.subtotal}"/>
      </h4>

 <table cellpadding="0" cellspacing="0" border="0" id="table" class="sortable">
        <thead>
        <th class="nosort"><h3>Imagen</h3></th>
        <th><h3>Nombre</h3></th>
        <th><h3>Precio</h3></th>
        <th><h3>Cantidad</h3></th>
        </thead>
        <tbody>
                                <c:forEach var="carritoItem" items="${carrito.items}" varStatus="iter">

          <c:set var="producto" value="${carritoItem.producto}"/>
          <tr><td><a href="<c:url value='producto?${producto.idProducto}'/>">
                                            <img class ="img50" src="imagenProducto?id=${producto.idProducto}"
                                                 alt="${producto.nombreProducto}">
                                        </a>
                                    </td>
                                    <td><a href="<c:url value='producto?${producto.idProducto}'/>">
                                            ${producto.nombreProducto}
                                        </a>
                                    </td>
                                    <td>
                <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${carritoItem.total}"/>
                <br>
                <span class="smallText">(
                    <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${producto.precioProducto}"/>
                    / <c:out value="unidad"/> )</span>
            </td>
                                    <td>
                <form action="<c:url value='actualizarCarrito'/>" method="post">
                    <input type="hidden"
                           id="idProducto"
                           name="idProducto"
                           value="${producto.idProducto}">
                    <input type="text"
                           maxlength="2"
                           size="2"
                           value="${carritoItem.cantidad}"
                           id="cantidad"
                           name="cantidad"
                           style="margin:5px">
                    <input type="submit"
                           name="submit"
                           class="btn btn-info"
                           value="<c:out value='Act.'/>">
                </form>
            </td>
                                </tr>

        </c:forEach>
                
        </tbody>
    </table>

    <script type="text/javascript">
        var sorter = new TINY.table.sorter("sorter");
        sorter.head = "head";
        sorter.asc = "asc";
        sorter.desc = "desc";
        sorter.even = "evenrow";
        sorter.odd = "oddrow";
        sorter.evensel = "evenselected";
        sorter.oddsel = "oddselected";
        sorter.paginate = true;
        sorter.currentid = "currentpage";
        sorter.limitid = "pagelimit";
        sorter.init("table", 1);
    </script>

    <div id="controls">
        <div id="perpage">
             
            <div class="styled-select" >
                <span>Productos por página:</span><br>
                <select style="width: 50px" onchange="sorter.size(this.value)">
                    <option value="5" selected="selected">5</option>
                    <option value="10" >10</option>
                    <option value="20">20</option>
                </select> </div>

           
        </div>
        <div id="navigation">
            <img src="css/table-img/first.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1, true);" />
            <img src="css/table-img/previous.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1);" />
            <img src="css/table-img/next.gif" width="16" height="16" alt="First Page" onclick="sorter.move(1);" />
            <img src="css/table-img/last.gif" width="16" height="16" alt="Last Page" onclick="sorter.move(1, true);" />
        </div>
        <div id="text">Mostrando página <span id="currentpage"></span> de <span id="pagelimit"></span></div>
    </div>
    <script>
   sorter.size(5);
    </script>
    </c:if>
</div>

<%-- 
    Document   : index
    Created on : 15-abr-2013, 4:02:11
    Author     : oswaldomaestra
--%>


<div id="categoriaDetalle">
    <p id="categoriaTitulo">${categoriaSeleccionada.nombreCategoria}</p>
    <table cellpadding="0" cellspacing="0" border="0" id="table" class="tablaProducto">

        <tbody>
            <c:if test="${categoriaSeleccionada.categoriaList.isEmpty()}">
                <c:forEach var="productos" items="${categoriaSeleccionada.productoCategoriaList}" varStatus="iter">
                    <c:if test="${(iter.index % 3) == 0}">
                        </tr>
                        <tr>
                        </c:if>
                        <c:if test="${(iter.index%2) == 0}">
                            <td></c:if> 
                            <c:if test="${(iter.index%2) != 0}">
                            <td style='background: #F2F2F2'></c:if> 
                            <h2><strong>${productos.producto.nombreProducto}</strong></h2><br><a href="<c:url value='producto?${productos.producto.idProducto}'/>">
                                <img class ="img100" src="${initParam.productoImagenPath}${productos.producto.idProducto}.jpg"
                                     alt="${productos.producto.nombreProducto}">
                            </a><br>
                            
                                <br>
                                <div class="ribbon-wrapper" style="margin-top: -52px; margin-left: -6px;">
                                     <div class="ribbon-front">
                    <div id="precioProducto"><br><strong>Bs.F. ${productos.producto.precioProducto} </strong></div>
		</div>
		<div class="ribbon-edge-topleft"></div>
		<div class="ribbon-edge-topright"></div>
		<div class="ribbon-edge-bottomleft"></div>
		<div class="ribbon-edge-bottomright"></div>
                                </div>
                                <form action="<c:url value='agregarAlCarrito'/>" method="post">
                                <input type="hidden"
                                       name="idProducto"
                                       value="${productos.producto.idProducto}">
                                <button style="margin:-40px 0 0 30px; position: absolute;" class="btn btn-success" type="submit" >+</button>
                            </form>
                        </td>
                    </c:forEach>
                </c:if>
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
                    <option value="2" selected="selected">6</option>
                    <option value="4" >12</option>
                    <option value="6">24</option>
                </select> </div>


        </div>
        <div id="navigation">
            <img src="css/table-img/first.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1, true);" />
            <img src="css/table-img/previous.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1);" />
            <img src="css/table-img/next.gif" width="16" height="16" alt="First Page" onclick="sorter.move(1);" />
            <img src="css/table-img/last.gif" width="16" height="16" alt="Last Page" onclick="sorter.move(1, true);" />
        </div>
        <div id="text" style='text-align: right;'>Mostrando página <span id="currentpage"></span> de <span id="pagelimit"></span></div>
    </div>
    <script>
        sorter.size(2);
    </script>
</div>

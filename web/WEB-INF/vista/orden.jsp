<div id='columnaDerechaPerfil'>
    <br>
    <br>
    
    <div class="detallesOrden">
    <table cellpadding="0" cellspacing="0" border="0"  id="vertical-2" class="tablaDetalleOrden">
        <tbody>
            <tr>
                <th> <h3>Número de orden:</h3></th></tr>
    <tr>
                <td>${ordenSeleccionada.idOrden}</td>
        </tr>
        <tr>
            <th><h3>Fecha de pedido:</h3></th></tr>
    <tr>
            <td style="background:#ecf2f6">
                <fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss" value="${ordenSeleccionada.fechaCreacionOrden}"/></td>
        </tr>
        
           <c:if test="${ordenSeleccionada.statusOrden eq 'Entregada'}">
            <tr><th><h3>Fecha de entrega:</h3></th></tr>
    <tr>
               <td><fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss" value="${ordenSeleccionada.fechaEntregada}"/></td>
        </tr>
        <tr><th><h3>Entregado en:</h3></th></tr></c:if>
    <c:if test="${ordenSeleccionada.statusOrden eq 'En proceso'}">
    <tr><th><h3>Estado de la orden:</h3></th></tr>
    <tr><td>${ordenSeleccionada.statusOrden}</td></tr>
    <tr><th><h3>Para entregar en:</h3></th></tr><tr></tr>
    </c:if>
    <tr>
    <td>${ordenSeleccionada.direccionEntrega}<br>
    ${direccionEntrega}</td>
        </tr>
        <tr>
            <th><h3>Código QR:</h3></th></tr>
    <tr><td><img src="${linkOrden}"></td>
        </tr>
        </tbody>
    </table>
        </div>
        <div class="productosOrden">
    <table cellpadding="0" cellspacing="0" border="0" id="table" class="sortable">
        <thead>
            <tr>
                <th><h3>Nombre</h3></th>
        <th><h3>Precio (por Unidad)</h3></th>
        <th><h3>Cantidad</h3></th>
        <th><h3>Precio total</h3></th>
        </tr>
        </thead>
        <tbody>

            <c:forEach var="productoOrden" items="${listaProductoOrden}" varStatus="iter">
                <tr>
                    <%--<img src="${initParam.productoImagenPath}${product.name}.png"
                         alt="${product.name}">--%>
                    <td><a href="<c:url value='producto?${productoOrden.producto.idProducto}'/>">
                            <span>${productoOrden.producto.nombreProducto}</span>

                        </a></td>
                    <td style="text-align: right">${productoOrden.precio}</td>
                    <td>${productoOrden.cantidad}</td>
                    <td style="text-align: right" class ="precio" onChange ="">${productoOrden.precio*productoOrden.cantidad}</td>
                </tr>
            </c:forEach>
        </tbody>
        <tfooter>
        </tfooter>
    </table>
    <div align="right"><td align="right">Monto total :</td>
        <td style="text-align:right">${ordenSeleccionada.montoTotal}</td></div>
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
    <a href="/JShoP/factura?${ordenSeleccionada.idOrden}" target="_blank">
    <button class="btn btn-inverse">Imprimir factura</button></a>
</div>
    </div>
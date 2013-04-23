<div id='menuPerfil'>
    <div class='opcionPerfil'>
        <a href="<c:url value='verPerfil?pedidos'/>">
            <div class="perfilBoton">
                <span>
                    Pedidos
                </span>
                <h2>Vea sus pedidos recientes, acceda a sus facturas.</h2>
            </div></a>
        <a href="<c:url value='verPerfil?metodosDePago'/>">
            <div class="perfilBoton">
                <span>
                    Métodos de pago
                </span>
                <h2>Gestione sus métodos de pago, agregue, elimine, o modifique datos.</h2>
            </div></a>
        <a href="<c:url value='verPerfil?miCuenta'/>" class="perfilBoton">
            <div class="perfilBoton">
                <span>
                    Mi cuenta
                </span>
                <h2>Configure su perfil, modifique su correo electronico, dirección, entre otros</h2>
            </div></a>
    </div>

</div>
<div id='columnaDerechaPerfil'>
    <br>
    <br>

    <p id="tituloOpcion">Orden # ${numeroDeOrden} </p>
    <br>
    <style type="text/css">
        #vertical-2 thead,#vertical-2 tbody{
            display:inline-block;
        }

    </style>
    <table cellpadding="0" cellspacing="0" border="0"  id="vertical-2" class="sortable-v">
        <thead>
            <tr>
                <th> <h3>Orden #</h3></th>
        </tr>
        <tr>
            <th><h3>Fecha de pedido:</h3></th>
        </tr>
        <tr>
            <th><h3>Fecha de entrega:</h3></th>
        </tr>
        <tr>
            <th><h3>Entregado en:</h3></th>
        </tr>
        <tr>
            <th style="height:150px;"><h3 style="height:150px;">Código QR:</h3></th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${numeroDeOrden}</td>

            </tr>
            <tr><td style="background:#ecf2f6">${fechaDePedido}</td>
            </tr>
            <tr><td>${fechaDePedido}</td>
            </tr>
            <tr><td>${direccionEntrega}</td>
            </tr>
            <tr><td><img src="${linkOrden}"></td>
            </tr>
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
    <table cellpadding="0" cellspacing="0" border="0" id="table" class="sortable">
        <thead>
            <tr>
                <th><h3>Nombre</h3></th>
        <th><h3>Descripcion</h3></th>

        <th><h3>Precio (por Unidad)</h3></th>
        <th><h3>Cantidad</h3></th>
        <th><h3>Precio total</h3></th>
        </tr>
        </thead>
        <tbody>

            <c:forEach var="productoOrden" items="${listaProductoOrden}" varStatus="iter">
                <tr>
                    <%--<img src="${initParam.productImagePath}${product.name}.png"
                         alt="${product.name}">--%>
                    <td><a href="<c:url value='producto?${productoOrden.producto.idProducto}'/>">
                            <span>${productoOrden.producto.nombreProducto}</span>

                        </a></td>
                    <td>
                        <span class="smallText">${productoOrden.producto.descripcionProducto}</span></td>
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
        <td style="text-align:right">${totalOrden}</td></div>
    <%--<div id="controls">
        <div id="perpage">
            <select onchange="sorter.size(this.value)">
                <option value="5">5</option>
                <option value="10" selected="selected">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>
            <span>Entries Per Page</span>
        </div>
        <div id="navigation">
            <img src="css/table-img/first.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1, true);" />
            <img src="css/table-img/previous.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1);" />
            <img src="css/table-img/next.gif" width="16" height="16" alt="First Page" onclick="sorter.move(1);" />
            <img src="css/table-img/last.gif" width="16" height="16" alt="Last Page" onclick="sorter.move(1, true);" />
        </div>
        <div id="text">Displaying Page <span id="currentpage"></span> of <span id="pagelimit"></span></div>
    </div>--%>
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
</div>
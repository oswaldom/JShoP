<%-- 
    Document   : index
    Created on : 15-abr-2013, 11:14:06
    Author     : oswaldomaestra
--%>


<div id="adminMenu" class="alignLeft">
    <p><a href="<c:url value='verClientes'/>">Ver todos los clientes</a></p>

    <p><a href="<c:url value='verOrdenes'/>">Ver todas las ordenes</a></p>
    
    <p><a href="<c:url value='verProductos'/>">Ver todos los productos</a></p>

    <p><a href="<c:url value='logout'/>">Cerrar sesion</a></p>
</div>

<%-- customerList is requested --%>
<c:if test="${!empty clienteList}">

    <table id="adminTable" class="detailsTable">

        <tr class="header">
            <th colspan="4">Clientes</th>
        </tr>

        <tr class="tableHeading">
            <td>id Cliente</td>
            <td>Nombre</td>
            <td>Correo electronico</td>
            <td>Fecha registro</td>
        </tr>

        <c:forEach var="cliente" items="${clienteList}" varStatus="iter">

            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                onclick="document.location.href='customerRecord?${cliente.idCliente}'">

              <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="customerRecord?${cliente.idCliente}" class="noDecoration">${cliente.idCliente}</a></td>
                <td><a href="customerRecord?${cliente.idCliente}" class="noDecoration">${cliente.nombreCliente}</a></td>
                <td><a href="customerRecord?${cliente.idCliente}" class="noDecoration">${cliente.correoElectronico}</a></td>
                <td><a href="customerRecord?${cliente.idCliente}" class="noDecoration">${cliente.fechaRegistro}</a></td>
            </tr>

        </c:forEach>

    </table>

</c:if>

<%-- orderList is requested --%>
<c:if test="${!empty ordenList}">

    <table id="adminTable" class="detailsTable">

        <tr class="header">
            <th colspan="4">Ordenes</th>
        </tr>

        <tr class="tableHeading">
            <td>id Orden</td>
            <td>Nro. confirmacion</td>
            <td>Monto total</td>
            <td>Fecha creada</td>
        </tr>

        <c:forEach var="orden" items="${ordenList}" varStatus="iter">

            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                onclick="document.location.href='orderRecord?${orden.idOrden}';">

              <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="orderRecord?${orden.idOrden}" class="noDecoration">${orden.idOrden}</a></td>
                <td><a href="orderRecord?${orden.idOrden}" class="noDecoration">${orden.nroConfirmacion}</a></td>
                <td><a href="orderRecord?${orden.idOrden}" class="noDecoration">
                        <fmt:formatNumber type="currency"
                                          currencySymbol="&euro; "
                                          value="${orden.montoTotal}"/></a></td>

                <td><a href="orderRecord?${orden.idOrden}" class="noDecoration">
                        <fmt:formatDate value="${orden.fechaCreacionOrden}"
                                        type="both"
                                        dateStyle="short"
                                        timeStyle="short"/></a></td>
            
            </tr>

        </c:forEach>

    </table>

</c:if>

<%-- customerRecord is requested --%>
<c:if test="${!empty productoList}">
    
 
                <div id="container">
                    
                    <form id="product_form" action="<c:url value='agregarProducto'/>" method="post">
                        <a class="close_btn" href="" onclick='closeLogin();'>cerrar</a>
                        
                        <fieldset>
                            <h3>ID proximo producto:
                                <label for="id" style="color:cornflowerblue"><c:out value="${proximoProductoId}"></c:out></label>
                            </h3>
                            <h3>
                                <label for="nombre">Nombre del producto:</label>
                            </h3>
                            <input type="text" class="insert_form_text" name="nombreProducto" required>
                            <h3>
                                <label for="precio">Precio del producto(Bs.F):</label>
                            </h3>
                            <input type="text" class="insert_form_text" name="precioProducto" required>
                            <h3>
                                <label for="descripcion">Descripcion del producto:</label>
                            </h3>
                            <textarea class="insert_form_text" id="textArea" name="descripcionProducto" rows="100" cols="200" required></textarea>
                           
                        </fieldset>
                        
                        <button id='input_btn' type='submit'><span>Crear</span></button>
                        
                    </form>
                </div>
 
    
    <table id="adminTable" class="detailsTable">

        <tr class="header">
            <th colspan="4">Productos
            
        <a href="#" class="create_btn" onclick="loginOverlay();">
                        <span>Crear+</span>
                    </a>
            </th>
        
        </tr>

        <tr class="tableHeading">
            <td>id Producto</td>
            <td>Nombre</td>
            <td>Precio</td>
            <td>Descripcion</td>
        </tr>

        <c:forEach var="producto" items="${productoList}" varStatus="iter">

            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                onclick="document.location.href='productoRecord?${producto.idProducto}';">

              <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="productoRecord?${producto.idProducto}" class="noDecoration">${producto.idProducto}</a></td>
                <td><a href="productoRecord?${producto.idProducto}" class="noDecoration">${producto.nombreProducto}</a></td>
                <td><a href="productoRecord?${producto.idProducto}" class="noDecoration">
                        <fmt:formatNumber type="currency"
                                          currencySymbol="Bs.F"
                                          value="${producto.precioProducto}"/>
                    </a>
                </td>
                <td><a href="productoRecord?${producto.idProducto}" class="noDecoration">${producto.descripcionProducto}</a></td>
            
            </tr>

        </c:forEach>

    </table>
    
</c:if>
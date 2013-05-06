<div id='columnaDerechaPerfil'>
    <p><a href="<c:url value='logout'/>">Cerrar sesion</a></p>
    <br>
    <br>
    
    <div class="detallesOrden">
        <c:if test="${ordenSeleccionada.statusOrden eq 'Entregada'}">
            <p>La orden ha sido entregada</p>
        </c:if>
        <FORM action="ServletXML" method="get">
    <table cellpadding="0" cellspacing="0" border="0"  id="vertical-2" class="tablaDetalleOrden">
        <tbody>
            <tr>
                <th> <h3>Número de orden:</h3></th></tr>
    <tr>
        <td id="numeroOrden" name="numeroOrden" value='${ordenSeleccionada.idOrden}'>${ordenSeleccionada.idOrden}</td>
        <input type='hidden' id="numeroOrden" name="numeroOrden" value='${ordenSeleccionada.idOrden}'/>
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
    <tr><td>
        <div class="styled-select">
                <select id="statusSelect" name="statusSelect">

                        <option value="${ordenSeleccionada.statusOrden}">
                            ${ordenSeleccionada.statusOrden}</option>
                        <option value="Entregada">
                            Entregada</option>
                </select></div></td></tr>
    <tr><th><h3>Para entregar en:</h3></th></tr><tr></tr>
    </c:if>
    <tr>
    <td>${ordenSeleccionada.direccionEntrega}<br>
    ${direccionEntrega}</td>
        </tr>
        <tr>
            <th><h3>Código QR:</h3></th></tr>
    <tr><td><img src="${linkOrden}"></td>
        </tr><tr>
    <p> 
        <c:if test="${ordenSeleccionada.statusOrden eq 'En proceso'}">
        <input  name="button" type="submit" class="btn btn-success" value="Actualizar Orden" />
                </p></c:if>
</tr>
        </tbody>
    </table></FORM>
        </div>
        

    

        </div>
<%--
    Document   : categoria
    Created on : Feb 10, 2013, 12:20:23 AM
    Author     : oswaldomaestra
--%>



<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Controller so that
     users view the same page when switching between English and Czech. --%>
<c:set var="view" value="/checkout" scope="session"/>


<script src="js/jquery.validate.js" type="text/javascript"></script>

<script type="text/javascript">

    $(document).ready(function(){
        $("#checkoutForm").validate({
            rules: {
                name: "required",
                email: {
                    required: true,
                    email: true
                },
                phone: {
                    required: true,
                    number: true,
                    minlength: 9
                },
                address: {
                    required: true
                },
                creditcard: {
                    required: true,
                    creditcard: true
                }
            }
        });
    });
</script>


<%-- HTML markup starts below --%>

<div id="singleColumn">

    <h2 ><c:out value="Modulo de pago"/></h2>

    <p><c:out value="Caja:"/></p>

    <c:if test="${!empty orderFailureFlag}">
        <p class="error"><c:out value="orderFailureError"/></p>
    </c:if>
        
      

    <form id="login_form" action="<c:url value='realizarCompra'/>" method="post">
        <div id="datosClienteCheckout" style="margin-left: 50px;">

            <div id="datosDireccion">
            <td>
            <tr><h2><label for="direccion">Dirección de envío:</label>
            <input type="text" maxlength="45" placeholder="Calle/Av. Edif. Piso. Apto. " id="direccion"
                   name="direccion" value="${param.direccion}" required/>
            <td>
            <tr><h2><label for="pais">País:</label>
                <label for="estado" style="margin-left:170px;">Estado/Región:</label>
            </h2></tr>
            </td>
            <div class="styled-select">
                <select id="paisSelect" name="paisSelect" placeholder="Seleccione su país">
                    <c:forEach var="pais" items="${listaPais}">
                        <option value="${pais.idLugar}">
                            ${pais.nombreLugar}</option>
                        </c:forEach>
                </select>
                <select id="estadoSelect" name="estadoSelect" placeholder="Seleccione un estado">

                    <c:forEach var="estado" items="${listaEstado}">
                        <option value="${estado.idLugar}" class="${estado.fkLugar.idLugar}">
                            ${estado.nombreLugar}</option>
                        </c:forEach>
                </select>
            </div>
            <tr><h2>
                <label for="estado" >Ciudad:</label>
            </h2></tr>
            <div class="styled-select">
                <select id="ciudadSelect" name="ciudadSelect" onchange="lugarSeleccionado()"placeholder="Seleccione la ciudad">

                    <c:forEach var="ciudad" items="${listaCiudad}">
                        <option value="${ciudad.idLugar}" class="${ciudad.fkLugar.idLugar}">
                            ${ciudad.nombreLugar}</option>
                        </c:forEach>
                </select>
                <script type="text/javascript">$("#estadoSelect").chained("#paisSelect");
                       $("#ciudadSelect").chained("#estadoSelect");
function lugarSeleccionado(){
var metodoDePago = document.getElementById("ciudadSelect");
var lugarSeleccionado = tripType.options[metodoDePago.selectedIndex].value;

}
</script></script>

            </div>
            <br>
            <br>
            <div class="styled-select">
            <select id="metodoSeleccionado" name="metodoSeleccionado" onchange="metodoDePago()"style="width:250px; text-align: left;">
                        <c:forEach var="metPago" items="${metPagos}">
                        <option value="${metPago.idTipodepago}" class="${metPago.numeroTdc}">
                            ${metPago.marcacomercial}: ${metPago.numeroTdc}</option>
                        </c:forEach>
                    </select>
            </div>
            <br>
            <br>
             <table id="priceBox">
            <tr>
                <td><c:out value="subtotal"/>:</td>
                <td class="checkoutPriceColumn">
                    <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${carrito.subtotal}"/></td>
            </tr>
            <tr>
                <td><c:out value="recargo"/>:</td>
                <td class="checkoutPriceColumn">
                    <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${initParam.recargoPorEntrega}"/></td>
            </tr>
            <tr>
                <td class="total"><c:out value="total"/>:</td>
                <td class="total checkoutPriceColumn">
                    <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${carrito.total}"/></td>
            </tr>
            
        </table>
            <br>
                <button class="btn btn-success" type="submit" style="height: 35px; width: 150px; float: left; margin-left: 140px;" value="Comprar">
                Comprar
                </button>
        </div>

    </form>


       
</div>
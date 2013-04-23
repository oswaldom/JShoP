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

    <c:if test="${opcionPerfil eq 'pedidos'}">
        <p id="tituloOpcion">Viendo pedidos</p>
        <br>
        <c:if test="${!empty ordenesCliente}" >
        <p id="subtituloOpcion">Pedidos pendientes:</p>
        <table cellpadding="0" cellspacing="0" border="0" id="table" class="sortable">
            <thead>
                <tr>
                    <th class="nosort">
            <h3>Orden #</h3></th>
            <th><h3>Fecha</h3></th>
            <th><h3>Monto total</h3></th>
            <th class="nosort"><h3>Detalle</h3></th>
            </tr>
            </thead>
            <tbody>
                
                <c:forEach var="ordenCliente" items="${ordenesCliente}" varStatus="iter">
                    <c:if test="${ordenCliente.statusOrden eq 'En proceso'}">
                        <tr>
                            <%--<img src="${initParam.productImagePath}${product.name}.png"
                                 alt="${product.name}">--%>
                            <td><a href="<c:url value='verOrden?${ordenCliente.idOrden}'/>">
                                    <span>${ordenCliente.idOrden}</span>

                                </a></td>
                                <td><fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss" value="${ordenCliente.fechaCreacionOrden}"/></td>
                            <td style="text-align: right">${ordenCliente.montoTotal}</td>
                            <td><a href="<c:url value='verOrden?${ordenCliente.idOrden}'/>">
                                    <span>Ver detalle</span>

                                </a></td>
                        </tr></c:if>
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
        </c:if>
        <c:if test="${empty ordenesCliente}" >
            <p id="subtituloOpcion">Usted no tiene pedidos pendientes.</p>
        </c:if>
        
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
    </c:if>
        <c:if test="${opcionPerfil eq 'metodosDePago'}">
            <c:if test="${!empty metodosDePago}" >
            <p id="subtituloOpcion">Métodos de pago actuales:</p>
        <table cellpadding="0" cellspacing="0" border="0" id="metodosDePago" class="sortable">
            <thead>
                <tr>
                    <th class="nosort">
            <h3># de Tarjeta</h3></th>
            <th><h3>Fecha de Vencimiento</h3></th>
            <th><h3>Marca comercial</h3></th>
            <th class="nosort"><h3>Detalle</h3></th>
            </tr>
            </thead>
            <tbody>

                <c:forEach var="metodoDePago" items="${metodosDePago}" varStatus="iter">
                    
                        <tr>
                            <td>${metodoDePago.numeroTdc}</td>
                            <td><fmt:formatDate pattern="yyyy/MM" value="${metodoDePago.fechaVencimiento}"/></td>
                                            
                            <td>${metodoDePago.marcacomercial}</td>
                            
                            <td><a href="<c:url value='metodoDePago?${metodoDePago.idTipodepago}'/>">
                                    <span>Modificar</span>

                                </a></td>
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
            sorter.init("metodosDePago",1);
        </script>
            <a href="<c:url value='verPerfil?anadirTarjeta'/>">
                                        <button class="btn btn-success" type="button" >Añadir tarjeta</button></a>
            </c:if>
            
        <c:if test="${empty metodosDePago}">
            <p id="subtituloOpcion">Usted no ha registrado métodos de pago, ¡Hagalo ahora!
            <a href="<c:url value='verPerfil?anadirTarjeta'/>">
                <button class="btn btn-success" type="button" style="margin: 0 auto;" >Añadir tarjeta</button></a></p>
        </c:if>
        </c:if>
            <c:if test="${opcionPerfil eq 'anadirTarjeta'}">
    <form id="login_form" action="<c:url value='anadirTarjeta'/>" method="post">
        <div class="tarjetaDeCredito">
            <tr>
            <tr><h2><label >Seleccione su marca:</label></h2>
           <script type="text/javascript">
function metodoDePago(){
var metodoDePago = document.getElementById("metodoSeleccionado");
var selectedValue = tripType.options[metodoDePago.selectedIndex].value;

}
</script>
                    <select id="metodoSeleccionado" name="metodoSeleccionado" onchange="metodoDePago()"style="width:250px; text-align: left;">
                        <option value="" data-description="Seleccione la marca">Método de pago</option>
                        <option value="Mastercard" data-image="img/master.png" >Mastercard</option>
                        <option value="Visa" data-image="img/visa.png" >Visa</option>
                    </select>
            </tr>
            <tr>
            <tr><h2><label for="cedula"># de tarjeta:</label></h2>
            <input type="text" placeholder="1111222233334444" id="numeroDeTarjeta"
                   name="numeroDeTarjeta" style="width: 243px;"value="${param.numeroDeTarjeta}"/> 
            </tr>
            <tr>
                <td>
            <tr><h2><label>Fecha de vencimiento (mm/aa):</label></h2></tr> 
            <script type="text/javascript" >
                $(document).ready(function() {
                    $('#fechaVencimiento').datepicker({
                        showOn: 'focus',
                        buttonText: 'Selecciona una fecha',
                        buttonImage: 'calendar.png',
                        buttonImageOnly: true,
                        maxDate: '+10y',
                        minDate: 'y',
                        changeMonth: true,
                        changeYear: true,
                        showButtonPanel: true,
                        dateFormat: 'mm/yy',
                        onClose: function(dateText, inst) {
                            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                            $(this).datepicker('setDate', new Date(year, month, 1));
                        }
                    });
                });</script>
            <style>
                .ui-datepicker-calendar {
                    display: none;
                }
            </style>
            <input type="text" id="fechaVencimiento" name="fechaVencimiento" style="width: 243px;" placeholder="${param.fechaVencimiento}" value="${param.fecha}"/>
            </td>
                <input class="btn btn-success" type="submit" value="Agregar">
    </tr>
    <tr>

    </tr>
    <tr>
        </div>
</form> 
<script>
    function createByJson() {
        var jsonData = [
            {description: 'Seleccione la marca', value: '', text: 'Método de pago'},
            {image: '../img/master.png', value: 'Mastercard', text: 'Mastercard'},
            {image: '../img/visa.png', value: 'Visa', text: 'Visa'}
        ];
        $("#byjson").msDropDown({byJson: {data: jsonData, name: 'payments2'}}).data("dd");
    }
    $(document).ready(function(e) {

        $("#ver").html(msBeautify.version.msDropdown);

        //convert
        $("select").msDropdown();
        createByJson();
        $("#tech").data("dd");
    });
    function showValue(h) {
        console.log(h.name, h.value);
    }
    $("#tech").change(function() {
        console.log("by jquery: ", this.value);
    });
//
</script>
        </c:if>
</div>
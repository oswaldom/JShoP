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
        <th><h3>Status</h3></th>
                <th class="nosort"><h3>Detalle</h3></th>
                </tr>
                </thead>
                <tbody>

                    <c:forEach var="ordenCliente" items="${ordenesCliente}" varStatus="iter">
                            <tr>
                            
                                <td><a href="<c:url value='verOrden?${ordenCliente.idOrden}'/>">
                                        <span>${ordenCliente.idOrden}</span>

                                    </a></td>
                                <td><fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss" value="${ordenCliente.fechaCreacionOrden}"/></td>
                                <td style="text-align: right">${ordenCliente.montoTotal}</td>
                                <td style="text-align: center">${ordenCliente.statusOrden}</td>
                                <td><a href="<c:url value='verOrden?${ordenCliente.idOrden}'/>">
                                        <span>Ver detalle</span>

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
                sorter.init("table", 1);
            </script>
        </c:if>
        <c:if test="${empty ordenesCliente}" >
            <p id="subtituloOpcion">Usted no tiene pedidos pendientes.</p>
        </c:if>
           

    </c:if>

    <c:if test="${opcionPerfil eq 'metodosDePago'}">
        <script type="text/javascript">
            function closeLogin() {
                document.getElementById('container').style.display = "none";
                document.getElementById("container").removeChild(document.getElementById("overlay"));
            }
            function loginOverlay() {
                document.getElementById('container').style.display = "block";
                var overlay = document.createElement("div");
                var anadirTarjetaForm = document.getElementById("anadirTarjeta");
                overlay.setAttribute("id", "overlay");
                overlay.style.position = "fixed";
                overlay.style.top = "0px";
                overlay.style.left = "0px";
                overlay.style.right = "0px";
                overlay.style.bottom = "0px";
                overlay.style.backgroundColor = "rgba(150,150,150,0.50)";
                overlay.style.zIndex = 900;
                //innerHTML = "<form> First name: <input type=\"text\" name=\"firstname\"/> <br/>Lastname: <input type=\"text\" name=\"lastname\"/></form><br/><a href='#c' onclick='closeLogin();'>close</a> ";
                overlay.appendChild(anadirTarjeta);
                document.getElementById("container").appendChild(overlay);
            }
        </script>
        <div id="container">
            <form id="anadirTarjeta" action="<c:url value='verPerfil?anadirTarjeta'/>" method="post">
                <div class="tarjetaDeCredito">
                    <tr>
                    <tr><h2><label >Seleccione su marca:</label></h2>
                    <script type="text/javascript">
                        function metodoDePago() {
                            var metodoDePago = document.getElementById("metodoSeleccionado");
                            var selectedValue = tripType.options[metodoDePago.selectedIndex].value;

                        }
                    </script>
                    <select id="metodoSeleccionado" name="metodoSeleccionado" onchange="metodoDePago()"style="width:250px; text-align: left;">
                        <option value="" data-description="Seleccione la marca">Método de pago</option>
                        <option value="Mastercard" data-image="img/Mastercard.png" >Mastercard</option>
                        <option value="Visa" data-image="img/Visa.png" >Visa</option>
                    </select>
                    </tr>
                    <tr>
                    <tr><h2><label for="cedula"># de tarjeta:</label></h2>
                    <input id="inputTextStyle" type="text" placeholder="1111222233334444" id="numeroDeTarjeta"
                           name="numeroDeTarjeta" style="width: 243px;"value="${param.numeroDeTarjeta}"/> 
                    </tr>
                    <tr>
                        <td>
                    <tr><h2><label>Fecha de vencimiento (mm/aa):</label></h2></tr> 
                    <script type="text/javascript" >
                       $(document).ready(function() {
                           $('.fechaVencimiento').datepicker({
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
                    <input  type="text" id="inputTextStyle" class="fechaVencimiento" name="fechaVencimiento" style="width: 243px;" placeholder="${param.fechaVencimiento}" value="${param.fecha}"/>
                    </td>
                    <br>
                    <a class="close_btn" onclick='closeLogin();'>
                    <button class="btn btn-danger" type="button">x</button></a><input class="btn btn-success"  type="submit" value="Agregar">
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
                        {image: '../img/Mastercard.png', value: 'Mastercard', text: 'Mastercard'},
                        {image: '../img/Visa.png', value: 'Visa', text: 'Visa'}
                    ];
                    $("#byjson").msDropDown({byJson: {data: jsonData, name: 'payments2'}}).data("dd");
                }
                $(document).ready(function(e) {

                    $("#ver").html(msBeautify.version.msDropdown);

                    //convert
                    $("#metodoSeleccionado").msDropdown();
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
        </div>

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
                                    <span>Eliminar</span>

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
    sorter.init("metodosDePago", 1);
            </script>
            <div id="controls">
        <div id="perpage">

            <div class="styled-select" >
                <span>Productos por página:</span><br>
                <select style="width: 50px" onchange="sorter.size(this.value)">
                    <option value="5" selected="selected">5</option>
                    <option value="10" >10</option>
                    <option value="15">15</option>
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
        sorter.size(5);
    </script>
            <div>
            <a href="#" onclick="loginOverlay();">
            <button class="btn btn-success" type="button" >Añadir tarjeta</button></a>
                </div>
            <br>
            <br>
            </c:if>

        <c:if test="${empty metodosDePago}">
            <p id="subtituloOpcion">Usted no ha registrado métodos de pago, ¡Hagalo ahora!
                <a href="#" onclick="loginOverlay();">
            <button class="btn btn-success" type="button" >Añadir tarjeta</button></a></p>
                </c:if>
            </c:if>
</div>
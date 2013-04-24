<div id="singleColumn">

    <form id="login_form" action="<c:url value='registrar'/>" method="post">
        <div id="datosLogin">
            <td>
            <tr><h2><label for="cedula">Documento de identidad (Cedula/Pasaporte):</label></h2></tr> 
            <input type="text" maxlength="45" placeholder="V-XX.XXX.XXX" id="cedula"
                   name="cedula" value="${param.cedula}" required/>
            </td>
            <c:if test="${!empty clienteOpenId}">
                <td>
                <tr><h2><label for="nombre">Nombre y apellido:</label></h2></tr> 
                <input type="text" maxlength="45" placeholder="Nombre" id="nombre"
                       name="nombre" value="${clienteOpenId.nombreCliente}"/>

                <input type="text" maxlength="45" placeholder="Apellido" id="apellido"
                       name="apellido" value="${clienteOpenId.apellidoCliente}"/>
                </td>
                <td>
                <tr><h2><label for="correoElectronico">Correo electr€nico:</label></h2></tr> 
                <input type="text" maxlength="45" placeholder="ejemplo@gmail.com" id="correo"
                       name="correo" value="${clienteOpenId.correoElectronico}" required/>
                </td>
            </c:if>
            <c:if test="${empty clienteOpenId}">
                <td>
                <tr><h2><label for="nombre">Nombre y apellido:</label></h2></tr> 
                <input type="text" maxlength="45" placeholder="Nombre" id="nombre"
                       name="nombre" value="${param.nombre}"/>

                <input type="text" maxlength="45" placeholder="Apellido" id="apellido"
                       name="apellido" value="${param.apellido}"/>
                </td>
                <td>
                <tr><h2><label for="correoElectronico">Correo electr€nico:</label></h2></tr> 
                <input type="text" maxlength="45" placeholder="ejemplo@gmail.com" id="correo"
                       name="correo" value="${param.correo}" required/>
                </td>
            </c:if>


            <td>
            <tr><h2><label for="contrasena">Contrase“a:</label>
                <label for="contrasenaVal" style="margin-left:135px">Confirme contrase“a:</label></h2></tr> 
            <input type="password" maxlength="45" id="contrasena"
                   name="contrasena" value="${param.crontrasena}" required/>
            <input type="password" maxlength="45" id="contrasenaVal"
                   name="contrasenaVal" value="${param.contrasenaVer}" required/>
            </td>
            <td>
            <tr><h2><label for="fechaNacimiento">Fecha de nacimiento (dd/mm/aaaa):</label></h2></tr> 
            <script type="text/javascript" >
                $(document).ready(function() {
                    $('#fecha').datepicker({
                        showOn: 'focus',
                        buttonText: 'Selecciona una fecha',
                        buttonImage: 'calendar.png',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true,
                        maxDate: '-18y',
                        minDate: '-90y',
                        showButtonPanel: true
                    });
                });</script>
            <input type="text" id="fecha" name="fecha" placeholder="${fechaPredeterminada}" value="${param.fecha}"/>
            </td>
        </div>
        <div id="datosDireccion">
            <td>
            <tr><h2><label for="direccion">Direcci€n:</label>
                <label for="codPostal" style="margin-left:140px;">C€digo postal:</label></h2></tr> 
            <input type="text" maxlength="45" placeholder="Calle/Av. Edif. Piso. Apto. " id="direccion"
                   name="direccion" value="${param.direccion}" required/>
            <input type="text" maxlength="45" placeholder="1071" id="codPostal"
                   name="codPostal" value="${param.codPostal}" required/>
            <td>
            <tr><h2><label for="pais">PaÃs:</label>
                <label for="estado" style="margin-left:170px;">Estado/Regi€n:</label>
            </h2></tr>
            </td>
            <div class="styled-select">
                <select id="paisSelect" name="paisSelect" placeholder="Seleccione su paÃs">
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
</script>

            </div>
            <br>
            <br>
            <br>
            <br>
            <td colspan="2">
                <input class="btn btn-success" type="submit" style="height: 35px; width: 150px; float: left; margin-left: 140px;" value="Registrarme!">
            </td>
        </div>

    </form>
</div>
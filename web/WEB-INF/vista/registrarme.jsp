<div id="singleColumn">

    <form id="login_form" action="<c:url value='registrar'/>" method="post">
        <table id="formulario_registro">

            <td>
            <tr><h2><label for="cedula">Documento de identidad (Cedula/Pasaporte):</label></h2></tr> 
            <input type="text" maxlength="45" placeholder="V-XX.XXX.XXX" id="reg.cedula"
                   name="reg.cedula" value="${param.cedula}" required=""/>
            </td>
            <td>
            <tr><h2><label for="nombre">Nombre y apellido:</label></h2></tr> 
            <input type="text" maxlength="45" placeholder="Nombre" id="reg.nombre"
                   name="reg.nombre" value="${param.nombre}"/>

            <input type="text" maxlength="45" placeholder="Apellido" id="reg.apellido"
                   name="reg.apellido" value="${param.apellido}"/>
            </td>
            <td>
            <tr><h2><label for="correoElectronico">Correo electrónico:</label></h2></tr> 
            <input type="text" maxlength="45" placeholder="ejemplo@gmail.com" id="reg.correo"
                   name="reg.correo" value="${param.correo}" required=""/>
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
                        maxDate: '-18y',
                        minDate: '-90y',
                        showButtonPanel: true
                    });

                });
            </script>
            <input type="text" id="fecha" placeholder="${fechaPredeterminada}" value="${param.fecha}"/>
            </td>
            <td>
            <tr><h2><label for="direccion">Dirección:</label></h2></tr> 
            <input type="text" maxlength="45" placeholder="Calle/Av. Ejemplo Edif. Edificio Piso. 4 Apto. 4-2" id="reg.correo"
                   name="reg.direccion" value="${param.direccion}" required=""/>

            <div class="styled-select">
                <select id="paisSelect" name="paisSelect">
                    <c:forEach var="pais" items="${listaPais}">
                        <option value="${pais.lugarList}" onchange="">
                            ${pais.nombreLugar}</option>
                        <%--<c:choose>
                            <c:when test="${pais == selected}"><option selected="true">${pais.nombreLugar}</option>
                                <c:set var="listaCiudad" value="pais.lugarList"/></c:when>

                            <c:otherwise><option>${pais.nombreLugar}</option></c:otherwise>
                        </c:choose>--%>
                    </c:forEach>
                </select>
                <select>
                    <c:choose>
                        <c:when test="${isSelected}">
                            <option selected="true">Opt1</option> 
                        </c:when>
                        <c:otherwise>
                            <option>Opt1</option> 
                        </c:otherwise>
                    </c:choose></select>
                <select id="ciudadSelect" name="ciudadSelect">
                    <c:forEach var="ciudad" items="${listaCiudad}">
                        <option value="${ciudad}" 
                                >
                            ${ciudad.nombreLugar}</option>
                        </c:forEach>
                </select>

            </div>
            <script type="text/javascript">
function lCiudad(id, listaciudad) {
    refreshCallback();
    // You can also pass arguments if you need to
    // refreshCallback(id);
}
            </script>
            </td>

            <br>
            prague
            <select name="cityRegion">
                <c:forEach begin="1" end="10" var="regionNumber">
                    <option value="${regionNumber}"
                            <c:if test="${param.cityRegion eq regionNumber}">selected</c:if>>${regionNumber}</option>
                </c:forEach>
            </select>
            </td>
            </tr>
            <tr>
                <td><label for="creditcard">credit card number:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="19"
                           id="creditcard"
                           name="creditcard"
                           value="${param.creditcard}">
                </td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td class="inputField" type="password">
                    <input type="password"
                           size="31"
                           maxlength="19"
                           id="password"
                           name="password"
                           value="${param.password}">
                </td>

            </tr>
            <tr>
                <td><label for="password_ver">Confirmar contrasena:</label></td>
                <td class="inputField">

                    <input type="password"
                           size="31"
                           maxlength="19"
                           id="password_ver"
                           name="password_ver"
                           value="${param.password_ver}">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Registrar">
                </td>
            </tr>
        </table>
    </form>
</div>
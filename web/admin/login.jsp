
<form action="<c:url value='j_security_check'/>" method=post>
    <div id="loginBox">
        <p><strong>Usuario</strong><br>
            <input id="inputTextStyle" type="text" size="20" name="j_username" autofocus="true"></p>
        <br>
        <p><strong>Contrasena</strong>
            <br>
            <input id="inputTextStyle" type="password" size="20" name="j_password"></p>
        <br>
        <button class="btn btn-success" type='submit'><span>Iniciar sesion</span></button>
    </div>
</form>
<%-- 
    Document   : index
    Created on : 15-abr-2013, 4:02:11
    Author     : oswaldomaestra
--%>

<div id="indexLeftColumn">
    <div id="welcomeText">
        <p style="font-size: larger">Texto de bienvenida</p>

        <p>Texto de bienvenida</p>
    </div>
</div>

<div id="indexRightColumn">
    <c:forEach var="categoria" items="${categorias}">
        <div class="categoryBox">
            <a href="<c:url value='categoria?${categoria.idCategoria}'/>">
                <span class="categoryLabel"></span>
                <span class="categoryLabelText">${categoria.nombreCategoria}</span>
                <%--
                <img src="${initParam.categoryImagePath}${category.name}.jpg"
                     alt="${category.name}" class="categoryImage">
                --%>
            </a>
        </div>
    </c:forEach>
</div>
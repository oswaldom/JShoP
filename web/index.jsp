<%-- 
    Document   : index
    Created on : 15-abr-2013, 4:02:11
    Author     : oswaldomaestra
--%>

<div id="indexLeftColumn">
    
    <c:forEach var="categoria" items="${categorias}">
        
        <c:if test="${categoria.fkCategoria == null}">
        <div class="categoryBox">
            <a href="<c:url value='categoria?${categoria.idCategoria}'/>">
                <div class="categoryButton">${categoria.nombreCategoria}</div>
                
                <%--
                <img src="${initParam.categoryImagePath}${category.name}.jpg"
                     alt="${category.name}" class="categoryImage">
                --%>
            </a>
        </div>
            </c:if>
    </c:forEach>
  
</div>

<div id="indexRightColumn">
    
    <div id="welcomeText">
        <p style="font-size: larger">Texto de bienvenida</p>

        <p>Texto de bienvenida</p>
    </div>
    
</div>

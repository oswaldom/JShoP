<%-- 
    Document   : buscar
    Created on : 21-abr-2013, 18:54:36
    Author     : oswaldomaestra
--%>

<div id="categoriaDetalle">

    <p id="categoriaTitulo">${mensaje}</p>
    
    <table cellpadding="0" cellspacing="0" border="0" id="table" class="sortable">
        <thead>
        <th class="nosort"><h3>Imagen</h3></th>
        <th><h3>Nombre</h3></th>
        <th><h3>Descripción</h3></th>
<c:if test="${!empty todasLasCategorias}">
        <th><h3>Categoria</h3></th>
</c:if>
        <th><h3>Precio (Bs. F)</h3></th>
        
        </thead>
        <tbody>
            <c:if test="${empty todasLasCategorias}">
                
                <c:if test="${categoriaSearch.categoriaList.isEmpty()}">
                    <c:forEach var="productos" items="${categoriaSearch.productoCategoriaList}" varStatus="iter">

                        <c:set var="busqueda" value="${busqueda}"></c:set>
                        <c:set var="nomb" value="${productos.producto.nombreProducto}"/>
                        

                        <c:if test="${fn:contains(fn:toLowerCase(nomb), fn:toLowerCase(busqueda) )}">
                            
                                <tr><td><a href="<c:url value='producto?${productos.producto.idProducto}'/>">
                                            <img class ="img50" src="imagenProducto?id=${productos.producto.idProducto}"
                                                 alt="${productos.producto.nombreProducto}">
                                        </a>
                                    </td>
                                    <td><a href="<c:url value='producto?${productos.producto.idProducto}'/>">
                                            ${productos.producto.nombreProducto}
                                        </a>
                                    </td>
                                    <td>${productos.producto.descripcionProducto}</td>
                                    <td>${productos.producto.precioProducto}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </c:if>
                <c:if test="${!empty todasLasCategorias}">
                    <c:forEach var="categoria" items="${todasLasCategorias}" varStatus="iter">
                        <c:if test="${categoria.categoriaList.isEmpty()}">
                            
                            
                            <c:forEach var="productos" items="${categoria.productoCategoriaList}" varStatus="iter">
                               <c:set var="busqueda" value="${busqueda}"></c:set>
                        <c:set var="nomb" value="${productos.producto.nombreProducto}"/>
                        

                        <c:if test="${fn:contains(fn:toLowerCase(nomb), fn:toLowerCase(busqueda) )}">
                                <tr><td><a href="<c:url value='producto?${productos.producto.idProducto}'/>">
                                            <img class ="img50" src="imagenProducto?id=${productos.producto.idProducto}"
                                                 alt="${productos.producto.nombreProducto}">
                                        </a>
                                    </td>
                                    <td><a href="<c:url value='producto?${productos.producto.idProducto}'/>">
                                            ${productos.producto.nombreProducto}
                                        </a>
                                    </td>
                                    <td>${productos.producto.descripcionProducto}</td>
                                    <td><a href="<c:url value='categoria?${productos.categoria.idCategoria}'/>">
                                            ${productos.categoria.nombreCategoria}
                </a></td>
                                    <td>${productos.producto.precioProducto}</td>
                                </tr></c:if>
                            </c:forEach>
</c:if>
                    </c:forEach>
        
                </c:if>
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

    <div id="controls">
        <div id="perpage">
             
            <div class="styled-select" >
                <span>Productos por página:</span><br>
                <select style="width: 50px" onchange="sorter.size(this.value)">
                    <option value="5" selected="selected">5</option>
                    <option value="10" >10</option>
                    <option value="20">20</option>
                </select> </div>

           
        </div>
        <div id="navigation">
            <img src="css/table-img/first.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1, true);" />
            <img src="css/table-img/previous.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1);" />
            <img src="css/table-img/next.gif" width="16" height="16" alt="First Page" onclick="sorter.move(1);" />
            <img src="css/table-img/last.gif" width="16" height="16" alt="Last Page" onclick="sorter.move(1, true);" />
        </div>
        <div id="text">Mostrando página <span id="currentpage"></span> de <span id="pagelimit"></span></div>
    </div>
    <script>
   sorter.size(5);
    </script>
</div>
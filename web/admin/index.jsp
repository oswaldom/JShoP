<%-- 
    Document   : index
    Created on : 15-abr-2013, 11:14:06
    Author     : oswaldomaestra
--%>


<div id="indexLeftColumn">

                

</div>

<c:if test="${!empty productoList}">
    <div id="categoriaDetalle">
    
 
                <div id="container">
                    
                    <form id="product_form" action="<c:url value='agregarProducto'/>" method="post">
                        <a class="close_btn" href="" onclick='closeLogin();'>X</a>
                        
                        <fieldset>
                            <h3>ID proximo producto:
                                <label for="id" style="color:cornflowerblue"><c:out value="${proximoProductoId}"></c:out></label>
                            </h3>
                            <h3>
                                <label for="nombre">Nombre del producto:</label>
                            </h3>
                            <input type="text" class="insert_form_text" name="nombreProducto" required>
                            <h3>
                                <label for="precio">Precio del producto(Bs.F):</label>
                            </h3>
                            <input type="text" class="insert_form_text" name="precioProducto" required>
                            <h3>
                                <label for="descripcion">Descripcion del producto:</label>
                            </h3>
                            
                            <textarea class="insert_form_text" id="textArea" name="descripcionProducto" rows="100" cols="200" required></textarea>
                            <div class="styled-select" style="margin-top:-7px;margin-right: 350px; width:60px;">
                                        <select id="categoriaSeleccionada" name="categoriaSeleccionada">
                                        <c:forEach var="categoria" items="${categorias}">
                                            <c:if test="${categoria.categoriaList.isEmpty()}">
                                                <option value="${categoria.idCategoria}">
                                                    ${categoria.nombreCategoria}</option>
                                                </c:if>
                                            </c:forEach>

                                    </select> 
                                </div>
                        </fieldset>
                        
                        <button class='btn-success' type='submit'>Crear</button>
                    </form>
                </div>
 
    
<div id="categoriaTitulo">Productos 
                    <a href="#" class="btn btn-success" onclick="loginOverlay();">
                        <span>Crear+</span>
                    </a></div>
            
        
        <table cellpadding="0" cellspacing="0" border="0" id="table1" class="sortable">
        <thead>
        <th><h3>Id</h3></th>
        <th><h3>Nombre</h3></th>
        <th><h3>Descripción</h3></th>
        <th><h3>Precio (Bs. F)</h3></th>
        </thead>
        <tbody>
                    <c:forEach var="producto" items="${productoList}" varStatus="iter">

                                <tr><td>${producto.idProducto}
                                    </td>
                                    <td><a href="<c:url value='producto?${producto.idProducto}'/>">
                                            ${producto.nombreProducto}
                                        </a>
                                    </td>
                                    <td>${producto.descripcionProducto}</td>
                                    <td>${producto.precioProducto}</td>
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
        sorter.init("table1", 1);
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
            <img src="../css/table-img/first.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1, true);" />
            <img src="../css/table-img/previous.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1);" />
            <img src="../css/table-img/next.gif" width="16" height="16" alt="First Page" onclick="sorter.move(1);" />
            <img src="../css/table-img/last.gif" width="16" height="16" alt="Last Page" onclick="sorter.move(1, true);" />
        </div>
        <div id="text">Mostrando página <span id="currentpage"></span> de <span id="pagelimit"></span></div>
    </div>
    <script>
   sorter.size(5);
    </script>
                                </div>
</c:if>
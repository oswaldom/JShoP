
<div id="categoriaDetalle">
    
    <div class="productoDetalle">   
        <p id="categoriaTitulo">${categoriaSeleccionada.nombreCategoria}</p>
        <div class="productoImagen">
            <td>
                <img src="${initParam.productoImagenPath}${productoSeleccionado.idProducto}.jpg"
                     alt="${productoSeleccionado.nombreProducto}"
                     >
            </td>
        </div>
        <div class="productoDescripcion">
            <span class="productoTexto">
                <br>
                <h2><strong>${fn:toUpperCase(productoSeleccionado.nombreProducto)}</strong></h2>
            </span>
            <br>
            <h3><strong>Descripción:</strong></h3>
            <p>${productoSeleccionado.descripcionProducto}</p>

            <a href="https://twitter.com/share" class="twitter-share-button" data-url="${urel}" data-text="Encontre lo que buscaba" data-via="JShoP_eC" data-lang="en">Compartir</a>
            <script>!function(d, s, id) {
                    var js, fjs = d.getElementsByTagName(s)[0];
                    if (!d.getElementById(id)) {
                        js = d.createElement(s);
                        js.id = id;
                        js.src = "https://platform.twitter.com/widgets.js";
                        fjs.parentNode.insertBefore(js, fjs);
                    }
                }(document, "script", "twitter-wjs");</script>
            <br>

            <br>${productoSeleccionado.precioProducto}

            <br>
            <form action="<c:url value='agregarAlCarrito'/>" method="post">
                <input type="hidden"
                       name="idProducto"
                       value="${productoSeleccionado.idProducto}">
                <input class="btn btn-success" type="submit"
                       name="submit"
                       value="+">
            </form>
        </div>
                <br>
                <br>
                <div id="productoComentario">
                    <h3>Comentarios:</h3>
                    <c:if test="${!empty productoSeleccionado.productreviewList}">
                    <c:forEach var="comentario" items="${productoSeleccionado.productreviewList}" varStatus="iter">
                        <strong>${comentario.cliente.nombreCliente} ${comentario.cliente.apellidoCliente}:</strong>
                        ${comentario.textoReview}
                    </c:forEach>
                        </c:if>
                        <c:if test="${empty productoSeleccionado.productreviewList}">
                            <h2>No hay comentarios...</h2>
                        </c:if>
                </div><br>
                <div id="productoRelacionado">
            <h3 style="margin:0 auto"><strong>Productos relacionados:</strong></h3>
            <table cellpadding="0" cellspacing="0" border="0" id="table" class="productoRelacionado">

        <tbody>
                <c:forEach var="productos" items="${categoriaSeleccionada.productoCategoriaList}" varStatus="iter">
                    
                    <c:if test="${(iter.index % 3) == 0}">
                        </tr>
                        <tr>
                        </c:if>
                            <c:if test="${productos.producto.idProducto != productoSeleccionado.idProducto}">
                        <c:if test="${(iter.index%2) == 0}">
                            <td></c:if> 
                            <c:if test="${(iter.index%2) != 0}">
                            <td style='background: #FFF'></c:if> 
                                    <a href="<c:url value='producto?${productos.producto.idProducto}'/>">
                                        <div class="productoImagen75f">
                                        <img class='productoImagen75' src="${initParam.productoImagenPath}${productos.producto.idProducto}.jpg"
                                     alt="${productos.producto.nombreProducto}"></div>
                                        <br>
                                ${productos.producto.nombreProducto}
                                <br>
                                <strong>Bs.F. ${productos.producto.precioProducto} </strong>
                            </a>
                        </td>
                        </c:if>
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

    <div id="controls">
        <div id="navigation">
            <img src="css/table-img/first.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1, true);" />
            <img src="css/table-img/previous.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1);" />
            <img src="css/table-img/next.gif" width="16" height="16" alt="First Page" onclick="sorter.move(1);" />
            <img src="css/table-img/last.gif" width="16" height="16" alt="Last Page" onclick="sorter.move(1, true);" />
        </div>
        <div id="text" style='text-align: right;'>Mostrando página <span id="currentpage"></span> de <span id="pagelimit"></span></div>
    </div>
    <script>
        sorter.size(1);
    </script>
    </div>
                </div>
                </div>
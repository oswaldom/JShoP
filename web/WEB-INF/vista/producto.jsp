
<div id="categoriaDetalle">

    <div class="productoDetalle">   
        <p id="categoriaTitulo">${categoriaSeleccionada.nombreCategoria}</p>
        <div class="productoImagen">
            <td>
                <img src="imagenProducto?id=${productoSeleccionado.idProducto}"
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
        <div id="productoComentario" style="margin-left: 10px">

            <h3>Comentarios:</h3>
            <c:if test="${!empty clientesComentan}">
                <c:forEach var="cliente" items="${clientesComentan}" varStatus="iter">


                    <script type="text/javascript">
                        $(function() {
                            $('.expander').simpleexpand();
                        });
                    </script>
                    <div class="expand-container">

                        <strong>${cliente.nombreCliente} ${cliente.apellidoCliente}</strong>
                        <c:forEach var="comentario" items="${cliente.productreviewList}" varStatus="iter2">
                            <c:if test="${iter2.index !=0}">

                                <div class="content">
                                </c:if>

                                <form>
                                    <script type="text/javascript">
                                                    $('input[value=${comentario.rating}]', this.form).attr('checked', 'checked');
                                        
                                    </script>
                                    <c:if test="${iter2.index == 0}">

                                        <input class="star" type="radio" name="srating" value="1" disabled="disabled"/>
                                        <input class="star" type="radio" name="srating" value="2" disabled="disabled"/>
                                        <input class="star" type="radio" name="srating" value="3" disabled="disabled"/>
                                        <input class="star" type="radio" name="srating" value="4" disabled="disabled"/>
                                        <input class="star" type="radio" name="srating" value="5" disabled="disabled"/>
                                        <a class="expander" href="#">Mostrar anteriores...</a>
                                    </c:if>
                                </form>
                                <fmt:formatDate pattern="dd/MM/yyyy" value="${comentario.fechaHora}"/>
                                <fmt:formatDate pattern="hh:mm:ss" value="${comentario.fechaHora}"/>
                                <br>
                                ${comentario.textoReview}
                                <c:if test="${iter2.index ==0}">

                                </c:if>
                                <c:if test="${iter2.index !=0}">
                                </div>
                            </c:if>
                        </c:forEach>

                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty productoSeleccionado.productreviewList}">
                <h2>No hay comentarios...</h2>
            </c:if>
            <br>

        </div><br>
        <c:if test="${loggedIn eq 'Valido'}">
        <div style="margin-left:10px; width:250px; height: 150px">
            <h3 style="margin-bottom: 3px">Califica este producto:</h3>
            <form id="comentarioNuevo" action="<c:url value='calificar'/>" method="post">

                <div class="Clear">
                    <input class="star" type="radio" name="star-rating" value="1"/>
                    <input class="star" type="radio" name="star-rating" value="2"/>
                    <input class="star" type="radio" name="star-rating" value="3"/>
                    <input class="star" type="radio" name="star-rating" value="4"/>
                    <input class="star" type="radio" name="star-rating" value="5"/>
                </div>
                <br>
                <input type="hidden"
                       name="idProducto"
                       value="${productoSeleccionado.idProducto}">
                <textarea name="comentario" id="comentario"></textarea>
                <br>
                <input class="btn btn-info" style="float: right; margin:0 auto;margin-top: 5px;" type="submit"
                       name="submit"
                       value="Calificar">
            </form>
        </div>
                </c:if>
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
                                            <img class='productoImagen75' src="imagenProducto?id=${productos.producto.idProducto}"
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
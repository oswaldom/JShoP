
<div id="categoryRightColumn">

    <div class="productDetail">   
        <div class="productImage">
            <td>
                <img src="${initParam.productImagePath}${productoSeleccionado.idProducto}.jpg"
                     alt="${productoSeleccionado.nombreProducto}"
                     height="250"
                     width="250">
            </td>
        </div>
        <div class="productDescription">
            <span class="productText">
                <p id="productTitle">${fn:toUpperCase(productoSeleccionado.nombreProducto)}</p>
            </span>
            <strong><p style="text-align:left">Descripción:</p></strong>
            <p>${productoSeleccionado.descripcionProducto} descripción larga del producto abcdefghijkl mnopqrstuvwxyz1234 5678910111213141 5!"#$%%$&&/(&)=</p>

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
            <span class="smallText">${productoSeleccionado.descripcionProducto}</span>

            <br>&euro; ${productoSeleccionado.precioProducto}

            <br>
            <form id="slick-login" action="<c:url value='agregarAlCarrito'/>" method="post">
                <input type="hidden"
                       name="idProducto"
                       value="${productoSeleccionado.idProducto}">
                <input type="submit"
                       name="submit"
                       value="add to cart">
            </form>
        </div>
        <div class="relatedProducts">
            <strong><p style="text-align:left">Productos relacionados:</p></strong>
            <c:forEach var="producto"  items="${relatedProducts}" varStatus="iter">

                <td class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                    
                    <c:if test="${producto.idProducto != productoSeleccionado.idProducto}">
                        <a href="producto?${producto.idProducto}">
                    <div class="productImage75">
                        <img src="${initParam.productImagePath}${producto.nombreProducto}.png"
                             alt="${producto.nombreProducto}"
                             width="75">
                        <div class="textpI75">
                            ${producto.nombreProducto}
                        </div>
                    </div></a>
                        
                        
                        <a href="<c:url value='producto?${producto.idProducto}'/>">
                            <span class="productLabel"></span>
                            <span class="productLabelText">${producto.nombreProducto}</span>

                        </a>&euro; ${producto.precioProducto}</td>
                    </c:if>
                </td>

            </c:forEach>
        </div>
    </div>
</div>
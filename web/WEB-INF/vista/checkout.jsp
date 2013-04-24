<%--
    Document   : categoria
    Created on : Feb 10, 2013, 12:20:23 AM
    Author     : oswaldomaestra
--%>



<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Controller so that
     users view the same page when switching between English and Czech. --%>
<c:set var="view" value="/checkout" scope="session"/>


<script src="js/jquery.validate.js" type="text/javascript"></script>

<script type="text/javascript">

    $(document).ready(function(){
        $("#checkoutForm").validate({
            rules: {
                name: "required",
                email: {
                    required: true,
                    email: true
                },
                phone: {
                    required: true,
                    number: true,
                    minlength: 9
                },
                address: {
                    required: true
                },
                creditcard: {
                    required: true,
                    creditcard: true
                }
            }
        });
    });
</script>


<%-- HTML markup starts below --%>

<div id="singleColumn">

    <h2><c:out value="checkout"/></h2>

    <p><c:out value="checkoutText"/></p>

    <c:if test="${!empty orderFailureFlag}">
        <p class="error"><c:out value="orderFailureError"/></p>
    </c:if>

    <form id="checkoutForm" action="<c:url value='realizarCompra'/>" method="post">
        <table id="checkoutTable">
          <c:if test="${!empty validationErrorFlag}">
            <tr>
                <td colspan="2" style="text-align:left">
                    <span class="error smallText"><c:out value="validationErrorMessage"/>

                      <c:if test="${!empty nameError}">
                        <br><span class="indent"><c:out value="nameError"/></span>
                      </c:if>
                      <c:if test="${!empty emailError}">
                        <br><span class="indent"><c:out value="emailError"/></span>
                      </c:if>
                      <c:if test="${!empty phoneError}">
                        <br><span class="indent"><c:out value="phoneError"/></span>
                      </c:if>
                      <c:if test="${!empty addressError}">
                        <br><span class="indent"><c:out value="addressError"/></span>
                      </c:if>
                      <c:if test="${!empty cityRegionError}">
                        <br><span class="indent"><c:out value="cityRegionError"/></span>
                      </c:if>
                      <c:if test="${!empty ccNumberError}">
                        <br><span class="indent"><c:out value="ccNumberError"/></span>
                      </c:if>

                    </span>
                </td>
            </tr>
          </c:if>
            <tr>
                <td><label for="name"><c:out value="customerName"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="name"
                           name="name"
                           value="${param.name}">
                </td>
            </tr>
            <tr>
                <td><label for="email"><c:out value="email"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="email"
                           name="email"
                           value="${param.email}">
                </td>
            </tr>
            <tr>
                <td><label for="phone"><c:out value="phone"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="16"
                           id="phone"
                           name="phone"
                           value="${param.phone}">
                </td>
            </tr>
            <tr>
                <td><label for="address"><c:out value="address"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="address"
                           name="address"
                           value="${param.address}">

                    <br>
                    <c:out value="prague"/>
                    <select name="cityRegion">
                      <c:forEach begin="1" end="10" var="regionNumber">
                        <option value="${regionNumber}"
                                <c:if test="${param.cityRegion eq regionNumber}">selected</c:if>>${regionNumber}</option>
                      </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="creditcard"><c:out value="creditCard"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="19"
                           id="creditcard"
                           name="creditcard"
                           class="creditcard"
                           value="${param.creditcard}">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="<c:out value='submit'/>">
                </td>
            </tr>
        </table>
    </form>

    <div id="infoBox">

        <ul>
            <li><c:out value="nextDayGuarantee"/></li>
            <li><c:out value="deliveryFee1"/>
                <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${initParam.recargoPorEntrega}"/>
                <c:out value="deliveryFee2"/></li>
        </ul>

        <table id="priceBox">
            <tr>
                <td><c:out value="subtotal"/>:</td>
                <td class="checkoutPriceColumn">
                    <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${carrito.subtotal}"/></td>
            </tr>
            <tr>
                <td><c:out value="recargo"/>:</td>
                <td class="checkoutPriceColumn">
                    <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${initParam.recargoPorEntrega}"/></td>
            </tr>
            <tr>
                <td class="total"><c:out value="total"/>:</td>
                <td class="total checkoutPriceColumn">
                    <fmt:formatNumber type="currency" currencySymbol="Bs.F; " value="${carrito.total}"/></td>
            </tr>
        </table>
    </div>
</div>
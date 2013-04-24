/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carritocompra;

import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

/**
 *
 * @author oswaldomaestra
 */
public class CarritoCompra {
    
    List<CarritoCompraItem> items;
    int numeroDeItems;
    double total;

    public CarritoCompra() {
        items = new ArrayList<CarritoCompraItem>();
        numeroDeItems = 0;
        total = 0;
    }

    /**
     * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>'s
     * <code>items</code> list. If item of the specified <code>product</code>
     * already exists in shopping cart list, the quantity of that item is
     * incremented.
     *
     * @param product the <code>Product</code> that defines the type of shopping cart item
     * @see ShoppingCartItem
     */
    public synchronized void agregarItem(Producto producto) {

        boolean nuevoItem = true;

        for (CarritoCompraItem ccItem : items) {

            if (ccItem.getProducto().getIdProducto() == producto.getIdProducto()) {

                nuevoItem = false;
                ccItem.incrementarCantidad();
            }
        }

        if (nuevoItem) {
            CarritoCompraItem ccItem = new CarritoCompraItem(producto);
            items.add(ccItem);
        }
    }

    /**
     * Updates the <code>ShoppingCartItem</code> of the specified
     * <code>product</code> to the specified quantity. If '<code>0</code>'
     * is the given quantity, the <code>ShoppingCartItem</code> is removed
     * from the <code>ShoppingCart</code>'s <code>items</code> list.
     *
     * @param product the <code>Product</code> that defines the type of shopping cart item
     * @param quantity the number which the <code>ShoppingCartItem</code> is updated to
     * @see ShoppingCartItem
     */
    public synchronized void update(Producto producto, String cantidad) {

        short cant = -1;

        // cast quantity as short
        cant = Short.parseShort(cantidad);

        if (cant >= 0) {

            CarritoCompraItem item = null;

            for (CarritoCompraItem ccItem : items) {

                if (ccItem.getProducto().getIdProducto() == producto.getIdProducto()) {

                    if (cant != 0) {
                        // set item quantity to new value
                        ccItem.setCantidad(cant);
                    } else {
                        // if quantity equals 0, save item and break
                        item = ccItem;
                        break;
                    }
                }
            }

            if (item != null) {
                // remove from cart
                items.remove(item);
            }
        }
    }

    /**
     * Returns the list of <code>ShoppingCartItems</code>.
     *
     * @return the <code>items</code> list
     * @see ShoppingCartItem
     */
    public synchronized List<CarritoCompraItem> getItems() {

        return items;
    }

    /**
     * Returns the sum of quantities for all items maintained in shopping cart
     * <code>items</code> list.
     *
     * @return the number of items in shopping cart
     * @see ShoppingCartItem
     */
    public synchronized int getNumeroDeItems() {

        numeroDeItems = 0;

        for (CarritoCompraItem ccItem : items) {

            numeroDeItems += ccItem.getCantidad();
        }

        return numeroDeItems;
    }

    /**
     * Returns the sum of the product price multiplied by the quantity for all
     * items in shopping cart list. This is the total cost excluding the recargo.
     *
     * @return the cost of all items times their quantities
     * @see ShoppingCartItem
     */
    public synchronized double getSubtotal() {

        double monto = 0;

        for (CarritoCompraItem ccItem : items) {

            Producto producto = ccItem.getProducto();
            monto += (ccItem.getCantidad() * producto.getPrecioProducto());
        }

        return monto;
    }

    /**
     * Calculates the total cost of the order. This method adds the subtotal to
     * the designated recargo and sets the <code>total</code> instance variable
     * with the result.
     *
     * @param recargo the designated recargo for all orders
     * @see ShoppingCartItem
     */
    public synchronized void calcularTotal(String recargo) {

        double monto = 0;

        // cast recargo as double
        double r = Double.parseDouble(recargo);

        monto = this.getSubtotal();
        monto += r;

        total = monto;
    }

    /**
     * Returns the total cost of the order for the given
     * <code>ShoppingCart</code> instance.
     *
     * @return the cost of all items times their quantities plus recargo
     */
    public synchronized double getTotal() {

        return total;
    }

    /**
     * Empties the shopping cart. All items are removed from the shopping cart
     * <code>items</code> list, <code>numberOfItems</code> and
     * <code>total</code> are reset to '<code>0</code>'.
     *
     * @see ShoppingCartItem
     */
    public synchronized void clear() {
        items.clear();
        numeroDeItems = 0;
        total = 0;
    }

}

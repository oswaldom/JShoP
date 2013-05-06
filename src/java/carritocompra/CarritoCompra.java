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
    
    public synchronized void update(Producto producto, String cantidad) {

        short cant = -1;

        cant = Short.parseShort(cantidad);

        if (cant >= 0) {

            CarritoCompraItem item = null;

            for (CarritoCompraItem ccItem : items) {

                if (ccItem.getProducto().getIdProducto() == producto.getIdProducto()) {

                    if (cant != 0) {
                        ccItem.setCantidad(cant);
                    } else {
                        item = ccItem;
                        break;
                    }
                }
            }

            if (item != null) {
                items.remove(item);
            }
        }
    }

    public synchronized List<CarritoCompraItem> getItems() {

        return items;
    }

    public synchronized int getNumeroDeItems() {

        numeroDeItems = 0;

        for (CarritoCompraItem ccItem : items) {

            numeroDeItems += ccItem.getCantidad();
        }

        return numeroDeItems;
    }

    public synchronized double getSubtotal() {

        double monto = 0;

        for (CarritoCompraItem ccItem : items) {

            Producto producto = ccItem.getProducto();
            monto += (ccItem.getCantidad() * producto.getPrecioProducto());
        }

        return monto;
    }

    public synchronized void calcularTotal(String recargo) {

        double monto = 0;

        double r = Double.parseDouble(recargo);

        monto = this.getSubtotal();
        monto += r;

        total = monto;
    }

    public synchronized double getTotal() {

        return total;
    }

    public synchronized void clear() {
        items.clear();
        numeroDeItems = 0;
        total = 0;
    }

}

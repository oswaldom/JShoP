/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carritocompra;

import modelo.Producto;

/**
 *
 * @author oswaldomaestra
 */
public class CarritoCompraItem {
    
    Producto producto;
    int cantidad;

    public CarritoCompraItem(Producto producto) {
        this.producto = producto;
        cantidad = 1;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void incrementarCantidad() {
        cantidad++;
    }

    public void decrementarCantidad() {
        cantidad--;
    }

    public double getTotal() {
        double monto = (this.getCantidad() * producto.getPrecioProducto());
        return monto;
    }

}

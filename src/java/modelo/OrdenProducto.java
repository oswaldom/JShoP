/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jesus
 */
@Entity
@Table(name = "orden_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenProducto.buscarTodo", query = "SELECT o FROM OrdenProducto o"),
    @NamedQuery(name = "OrdenProducto.buscarPorIdOrdenProducto", query = "SELECT o FROM OrdenProducto o WHERE o.ordenProductoPK.idOrdenProducto = :idOrdenProducto"),
    @NamedQuery(name = "OrdenProducto.buscarPorCantidad", query = "SELECT o FROM OrdenProducto o WHERE o.cantidad = :cantidad"),
    @NamedQuery(name = "OrdenProducto.buscarPorPrecio", query = "SELECT o FROM OrdenProducto o WHERE o.precio = :precio"),
    @NamedQuery(name = "OrdenProducto.buscarPorPkFkOrden", query = "SELECT o FROM OrdenProducto o WHERE o.ordenProductoPK.pkFkOrden = :pkFkOrden"),
    @NamedQuery(name = "OrdenProducto.buscarPorPkFkProducto", query = "SELECT o FROM OrdenProducto o WHERE o.ordenProductoPK.pkFkProducto = :pkFkProducto")})
public class OrdenProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrdenProductoPK ordenProductoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private double precio;
    @JoinColumn(name = "pk_fk_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "pk_fk_orden", referencedColumnName = "id_orden", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Orden orden;

    public OrdenProducto() {
    }

    public OrdenProducto(OrdenProductoPK ordenProductoPK) {
        this.ordenProductoPK = ordenProductoPK;
    }

    public OrdenProducto(OrdenProductoPK ordenProductoPK, int cantidad, double precio) {
        this.ordenProductoPK = ordenProductoPK;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public OrdenProducto(int idOrdenProducto, int pkFkOrden, int pkFkProducto) {
        this.ordenProductoPK = new OrdenProductoPK(idOrdenProducto, pkFkOrden, pkFkProducto);
    }

    public OrdenProductoPK getOrdenProductoPK() {
        return ordenProductoPK;
    }

    public void setOrdenProductoPK(OrdenProductoPK ordenProductoPK) {
        this.ordenProductoPK = ordenProductoPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordenProductoPK != null ? ordenProductoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenProducto)) {
            return false;
        }
        OrdenProducto other = (OrdenProducto) object;
        if ((this.ordenProductoPK == null && other.ordenProductoPK != null) || (this.ordenProductoPK != null && !this.ordenProductoPK.equals(other.ordenProductoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.OrdenProducto[ ordenProductoPK=" + ordenProductoPK + " ]";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jesus
 */
@Entity
@Table(name = "producto_categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoCategoria.findAll", query = "SELECT p FROM ProductoCategoria p"),
    @NamedQuery(name = "ProductoCategoria.findByIdProductoCategoria", query = "SELECT p FROM ProductoCategoria p WHERE p.productoCategoriaPK.idProductoCategoria = :idProductoCategoria"),
    @NamedQuery(name = "ProductoCategoria.findByPkFkProducto", query = "SELECT p FROM ProductoCategoria p WHERE p.productoCategoriaPK.pkFkProducto = :pkFkProducto"),
    @NamedQuery(name = "ProductoCategoria.findByPkFkCategoria", query = "SELECT p FROM ProductoCategoria p WHERE p.productoCategoriaPK.pkFkCategoria = :pkFkCategoria")})
public class ProductoCategoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoCategoriaPK productoCategoriaPK;
    @JoinColumn(name = "pk_fk_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "pk_fk_categoria", referencedColumnName = "id_categoria", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;

    public ProductoCategoria() {
    }

    public ProductoCategoria(ProductoCategoriaPK productoCategoriaPK) {
        this.productoCategoriaPK = productoCategoriaPK;
    }

    public ProductoCategoria(int idProductoCategoria, int pkFkProducto, int pkFkCategoria) {
        this.productoCategoriaPK = new ProductoCategoriaPK(idProductoCategoria, pkFkProducto, pkFkCategoria);
    }

    public ProductoCategoriaPK getProductoCategoriaPK() {
        return productoCategoriaPK;
    }

    public void setProductoCategoriaPK(ProductoCategoriaPK productoCategoriaPK) {
        this.productoCategoriaPK = productoCategoriaPK;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoCategoriaPK != null ? productoCategoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoCategoria)) {
            return false;
        }
        ProductoCategoria other = (ProductoCategoria) object;
        if ((this.productoCategoriaPK == null && other.productoCategoriaPK != null) || (this.productoCategoriaPK != null && !this.productoCategoriaPK.equals(other.productoCategoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ProductoCategoria[ productoCategoriaPK=" + productoCategoriaPK + " ]";
    }
    
}

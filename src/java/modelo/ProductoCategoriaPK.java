/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jesus
 */
@Embeddable
public class ProductoCategoriaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_producto_categoria")
    private int idProductoCategoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_fk_producto")
    private int pkFkProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_fk_categoria")
    private int pkFkCategoria;

    public ProductoCategoriaPK() {
    }

    public ProductoCategoriaPK(int idProductoCategoria, int pkFkProducto, int pkFkCategoria) {
        this.idProductoCategoria = idProductoCategoria;
        this.pkFkProducto = pkFkProducto;
        this.pkFkCategoria = pkFkCategoria;
    }

    public int getIdProductoCategoria() {
        return idProductoCategoria;
    }

    public void setIdProductoCategoria(int idProductoCategoria) {
        this.idProductoCategoria = idProductoCategoria;
    }

    public int getPkFkProducto() {
        return pkFkProducto;
    }

    public void setPkFkProducto(int pkFkProducto) {
        this.pkFkProducto = pkFkProducto;
    }

    public int getPkFkCategoria() {
        return pkFkCategoria;
    }

    public void setPkFkCategoria(int pkFkCategoria) {
        this.pkFkCategoria = pkFkCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProductoCategoria;
        hash += (int) pkFkProducto;
        hash += (int) pkFkCategoria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoCategoriaPK)) {
            return false;
        }
        ProductoCategoriaPK other = (ProductoCategoriaPK) object;
        if (this.idProductoCategoria != other.idProductoCategoria) {
            return false;
        }
        if (this.pkFkProducto != other.pkFkProducto) {
            return false;
        }
        if (this.pkFkCategoria != other.pkFkCategoria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ProductoCategoriaPK[ idProductoCategoria=" + idProductoCategoria + ", pkFkProducto=" + pkFkProducto + ", pkFkCategoria=" + pkFkCategoria + " ]";
    }
    
}

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
public class OrdenProductoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_orden_producto")
    private int idOrdenProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_fk_orden")
    private int pkFkOrden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_fk_producto")
    private int pkFkProducto;

    public OrdenProductoPK() {
    }

    public OrdenProductoPK(int idOrdenProducto, int pkFkOrden, int pkFkProducto) {
        this.idOrdenProducto = idOrdenProducto;
        this.pkFkOrden = pkFkOrden;
        this.pkFkProducto = pkFkProducto;
    }

    public int getIdOrdenProducto() {
        return idOrdenProducto;
    }

    public void setIdOrdenProducto(int idOrdenProducto) {
        this.idOrdenProducto = idOrdenProducto;
    }

    public int getPkFkOrden() {
        return pkFkOrden;
    }

    public void setPkFkOrden(int pkFkOrden) {
        this.pkFkOrden = pkFkOrden;
    }

    public int getPkFkProducto() {
        return pkFkProducto;
    }

    public void setPkFkProducto(int pkFkProducto) {
        this.pkFkProducto = pkFkProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idOrdenProducto;
        hash += (int) pkFkOrden;
        hash += (int) pkFkProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenProductoPK)) {
            return false;
        }
        OrdenProductoPK other = (OrdenProductoPK) object;
        if (this.idOrdenProducto != other.idOrdenProducto) {
            return false;
        }
        if (this.pkFkOrden != other.pkFkOrden) {
            return false;
        }
        if (this.pkFkProducto != other.pkFkProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.OrdenProductoPK[ idOrdenProducto=" + idOrdenProducto + ", pkFkOrden=" + pkFkOrden + ", pkFkProducto=" + pkFkProducto + " ]";
    }
    
}

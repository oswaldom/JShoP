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
 * @author oswaldomaestra
 */
@Embeddable
public class ProductreviewPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_productreview")
    private int idProductreview;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_fk_cliente")
    private int pkFkCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_fk_producto")
    private int pkFkProducto;

    public ProductreviewPK() {
    }

    public ProductreviewPK(int idProductreview, int pkFkCliente, int pkFkProducto) {
        this.idProductreview = idProductreview;
        this.pkFkCliente = pkFkCliente;
        this.pkFkProducto = pkFkProducto;
    }

    public int getIdProductreview() {
        return idProductreview;
    }

    public void setIdProductreview(int idProductreview) {
        this.idProductreview = idProductreview;
    }

    public int getPkFkCliente() {
        return pkFkCliente;
    }

    public void setPkFkCliente(int pkFkCliente) {
        this.pkFkCliente = pkFkCliente;
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
        hash += (int) idProductreview;
        hash += (int) pkFkCliente;
        hash += (int) pkFkProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductreviewPK)) {
            return false;
        }
        ProductreviewPK other = (ProductreviewPK) object;
        if (this.idProductreview != other.idProductreview) {
            return false;
        }
        if (this.pkFkCliente != other.pkFkCliente) {
            return false;
        }
        if (this.pkFkProducto != other.pkFkProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ProductreviewPK[ idProductreview=" + idProductreview + ", pkFkCliente=" + pkFkCliente + ", pkFkProducto=" + pkFkProducto + " ]";
    }
    
}

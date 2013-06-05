/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jesus
 */
@Entity
@Table(name = "productreview")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productreview.buscarTodo", query = "SELECT p FROM Productreview p"),
    @NamedQuery(name = "Productreview.buscarPorIdProductreview", query = "SELECT p FROM Productreview p WHERE p.productreviewPK.idProductreview = :idProductreview"),
    @NamedQuery(name = "Productreview.buscarPorRating", query = "SELECT p FROM Productreview p WHERE p.rating = :rating"),
    @NamedQuery(name = "Productreview.buscarPorTextoReview", query = "SELECT p FROM Productreview p WHERE p.textoReview = :textoReview"),
    @NamedQuery(name = "Productreview.buscarPorPkFkCliente", query = "SELECT p FROM Productreview p WHERE p.productreviewPK.pkFkCliente = :pkFkCliente"),
    @NamedQuery(name = "Productreview.buscarPorPkFkProducto", query = "SELECT p FROM Productreview p WHERE p.productreviewPK.pkFkProducto = :pkFkProducto"),
@NamedQuery(name = "Productreview.buscarPorPkFk", query = "SELECT p.productreviewPK.idProductreview FROM Productreview p WHERE p.productreviewPK.pkFkCliente = :pkFkCliente and p.productreviewPK.pkFkProducto = :pkFkProducto")
})
public class Productreview implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductreviewPK productreviewPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private int rating;
    @Size(max = 500)
    @Column(name = "texto_review")
    private String textoReview;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
  @JoinColumn(name = "pk_fk_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "pk_fk_cliente", referencedColumnName = "id_cliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    public Productreview() {
    }

    public Productreview(ProductreviewPK productreviewPK) {
        this.productreviewPK = productreviewPK;
    }

    public Productreview(ProductreviewPK productreviewPK, int rating) {
        this.productreviewPK = productreviewPK;
        this.rating = rating;
    }

    public Productreview(int idProductreview, int pkFkCliente, int pkFkProducto) {
        this.productreviewPK = new ProductreviewPK(idProductreview, pkFkCliente, pkFkProducto);
    }

    public ProductreviewPK getProductreviewPK() {
        return productreviewPK;
    }

    public void setProductreviewPK(ProductreviewPK productreviewPK) {
        this.productreviewPK = productreviewPK;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTextoReview() {
        return textoReview;
    }

    public void setTextoReview(String textoReview) {
        this.textoReview = textoReview;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productreviewPK != null ? productreviewPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productreview)) {
            return false;
        }
        Productreview other = (Productreview) object;
        if ((this.productreviewPK == null && other.productreviewPK != null) || (this.productreviewPK != null && !this.productreviewPK.equals(other.productreviewPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Productreview["
                + "productreviewPK=" + productreviewPK.toString() + ","
                + "textoReview="+textoReview+","
                + "rating="+rating+","
                + "fechaHora="+fechaHora+"]";
    }
    
}

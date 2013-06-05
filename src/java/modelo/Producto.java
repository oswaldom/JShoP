/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Jesus
 */
@Entity
@Table(name = "producto")
@XmlRootElement(name = "producto")
@XmlType(propOrder = { "idProducto", "nombreProducto", "descripcionProducto", "precioProducto" })
@NamedQueries({
    @NamedQuery(name = "Producto.buscarTodo", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.buscarPorIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto"),
    @NamedQuery(name = "Producto.buscarPorNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Producto.buscarPorPrecioProducto", query = "SELECT p FROM Producto p WHERE p.precioProducto = :precioProducto"),
    @NamedQuery(name = "Producto.buscarPorDescripcionProducto", query = "SELECT p FROM Producto p WHERE p.descripcionProducto = :descripcionProducto")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_producto")
    private Integer idProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio_producto")
    private double precioProducto;
    @Size(max = 500)
    @Column(name = "descripcion_producto")
    private String descripcionProducto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<ProductoCategoria> productoCategoriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<Productreview> productreviewList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<OrdenProducto> ordenProductoList;

    public Producto() {
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Integer idProducto, String nombreProducto, double precioProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
    }
    
    @XmlElement(name = "id")
//    @XmlAttribute
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }
    
//    @XmlElement(name = "nombre")
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

//    @XmlElement(name = "precio")
    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

//    @XmlElement(name = "descripcion")
    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    @XmlTransient
    public List<ProductoCategoria> getProductoCategoriaList() {
        return productoCategoriaList;
    }

    public void setProductoCategoriaList(List<ProductoCategoria> productoCategoriaList) {
        this.productoCategoriaList = productoCategoriaList;
    }

    @XmlTransient
    public List<Productreview> getProductreviewList() {
        return productreviewList;
    }

    public void setProductreviewList(List<Productreview> productreviewList) {
        this.productreviewList = productreviewList;
    }

    @XmlTransient
    public List<OrdenProducto> getOrdenProductoList() {
        return ordenProductoList;
    }

    public void setOrdenProductoList(List<OrdenProducto> ordenProductoList) {
        this.ordenProductoList = ordenProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Producto[ idProducto=" + idProducto + " ]";
    }
    
}

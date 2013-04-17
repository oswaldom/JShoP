/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jesus
 */
@Entity
@Table(name = "orden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orden.findAll", query = "SELECT o FROM Orden o"),
    @NamedQuery(name = "Orden.findByIdOrden", query = "SELECT o FROM Orden o WHERE o.idOrden = :idOrden"),
    @NamedQuery(name = "Orden.findByMontoTotal", query = "SELECT o FROM Orden o WHERE o.montoTotal = :montoTotal"),
    @NamedQuery(name = "Orden.findByFechaCreacionOrden", query = "SELECT o FROM Orden o WHERE o.fechaCreacionOrden = :fechaCreacionOrden"),
    @NamedQuery(name = "Orden.findByNroConfirmacion", query = "SELECT o FROM Orden o WHERE o.nroConfirmacion = :nroConfirmacion"),
    @NamedQuery(name = "Orden.findByStatusOrden", query = "SELECT o FROM Orden o WHERE o.statusOrden = :statusOrden"),
    @NamedQuery(name = "Orden.findByFechaEntregada", query = "SELECT o FROM Orden o WHERE o.fechaEntregada = :fechaEntregada")})
public class Orden implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_orden")
    private Integer idOrden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_total")
    private double montoTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion_orden")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacionOrden;
    @Column(name = "nro_confirmacion")
    private Integer nroConfirmacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status_orden")
    private String statusOrden;
    @Column(name = "fecha_entregada")
    @Temporal(TemporalType.DATE)
    private Date fechaEntregada;
    @JoinColumn(name = "fk_lugar", referencedColumnName = "id_lugar")
    @ManyToOne(optional = false)
    private Lugar fkLugar;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
    private Cliente fkCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orden")
    private List<OrdenProducto> ordenProductoList;

    public Orden() {
    }

    public Orden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Orden(Integer idOrden, double montoTotal, Date fechaCreacionOrden, String statusOrden) {
        this.idOrden = idOrden;
        this.montoTotal = montoTotal;
        this.fechaCreacionOrden = fechaCreacionOrden;
        this.statusOrden = statusOrden;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Date getFechaCreacionOrden() {
        return fechaCreacionOrden;
    }

    public void setFechaCreacionOrden(Date fechaCreacionOrden) {
        this.fechaCreacionOrden = fechaCreacionOrden;
    }

    public Integer getNroConfirmacion() {
        return nroConfirmacion;
    }

    public void setNroConfirmacion(Integer nroConfirmacion) {
        this.nroConfirmacion = nroConfirmacion;
    }

    public String getStatusOrden() {
        return statusOrden;
    }

    public void setStatusOrden(String statusOrden) {
        this.statusOrden = statusOrden;
    }

    public Date getFechaEntregada() {
        return fechaEntregada;
    }

    public void setFechaEntregada(Date fechaEntregada) {
        this.fechaEntregada = fechaEntregada;
    }

    public Lugar getFkLugar() {
        return fkLugar;
    }

    public void setFkLugar(Lugar fkLugar) {
        this.fkLugar = fkLugar;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
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
        hash += (idOrden != null ? idOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.idOrden == null && other.idOrden != null) || (this.idOrden != null && !this.idOrden.equals(other.idOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Orden[ idOrden=" + idOrden + " ]";
    }
    
}

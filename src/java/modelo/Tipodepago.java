/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author oswaldomaestra
 */
@Entity
@Table(name = "tipodepago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipodepago.findAll", query = "SELECT t FROM Tipodepago t"),
    @NamedQuery(name = "Tipodepago.findByIdTipodepago", query = "SELECT t FROM Tipodepago t WHERE t.idTipodepago = :idTipodepago"),
    @NamedQuery(name = "Tipodepago.findByNumeroTdc", query = "SELECT t FROM Tipodepago t WHERE t.numeroTdc = :numeroTdc"),
    @NamedQuery(name = "Tipodepago.findByFechaVencimiento", query = "SELECT t FROM Tipodepago t WHERE t.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "Tipodepago.findByMarcacomercial", query = "SELECT t FROM Tipodepago t WHERE t.marcacomercial = :marcacomercial")})
public class Tipodepago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipodepago")
    private Integer idTipodepago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_tdc")
    private long numeroTdc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "marcacomercial")
    private String marcacomercial;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
    private Cliente fkCliente;

    public Tipodepago() {
    }

    public Tipodepago(Integer idTipodepago) {
        this.idTipodepago = idTipodepago;
    }

    public Tipodepago(Integer idTipodepago, long numeroTdc, Date fechaVencimiento, String marcacomercial) {
        this.idTipodepago = idTipodepago;
        this.numeroTdc = numeroTdc;
        this.fechaVencimiento = fechaVencimiento;
        this.marcacomercial = marcacomercial;
    }

    public Integer getIdTipodepago() {
        return idTipodepago;
    }

    public void setIdTipodepago(Integer idTipodepago) {
        this.idTipodepago = idTipodepago;
    }

    public long getNumeroTdc() {
        return numeroTdc;
    }

    public void setNumeroTdc(long numeroTdc) {
        this.numeroTdc = numeroTdc;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getMarcacomercial() {
        return marcacomercial;
    }

    public void setMarcacomercial(String marcacomercial) {
        this.marcacomercial = marcacomercial;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipodepago != null ? idTipodepago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipodepago)) {
            return false;
        }
        Tipodepago other = (Tipodepago) object;
        if ((this.idTipodepago == null && other.idTipodepago != null) || (this.idTipodepago != null && !this.idTipodepago.equals(other.idTipodepago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tipodepago[ idTipodepago=" + idTipodepago + " ]";
    }
    
}

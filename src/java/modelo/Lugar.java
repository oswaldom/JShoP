/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jesus
 */
@Entity
@Table(name = "lugar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lugar.findAll", query = "SELECT l FROM Lugar l"),
    @NamedQuery(name = "Lugar.findByIdLugar", query = "SELECT l FROM Lugar l WHERE l.idLugar = :idLugar"),
    @NamedQuery(name = "Lugar.findByNombreLugar", query = "SELECT l FROM Lugar l WHERE l.nombreLugar = :nombreLugar"),
    @NamedQuery(name = "Lugar.buscarPorTipoLugar", query = "SELECT l FROM Lugar l WHERE l.tipoLugar = :tipoLugar")})
public class Lugar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lugar")
    private Integer idLugar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_lugar")
    private String nombreLugar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "tipo_lugar")
    private String tipoLugar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkLugar")
    private List<Orden> ordenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkLugar")
    private List<Cliente> clienteList;
    @OneToMany(mappedBy = "fkLugar")
    private List<Lugar> lugarList;
    @JoinColumn(name = "fk_lugar", referencedColumnName = "id_lugar")
    @ManyToOne
    private Lugar fkLugar;

    public Lugar() {
    }

    public Lugar(Integer idLugar) {
        this.idLugar = idLugar;
    }

    public Lugar(Integer idLugar, String nombreLugar, String tipoLugar) {
        this.idLugar = idLugar;
        this.nombreLugar = nombreLugar;
        this.tipoLugar = tipoLugar;
    }

    public Integer getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(Integer idLugar) {
        this.idLugar = idLugar;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

    @XmlTransient
    public List<Orden> getOrdenList() {
        return ordenList;
    }

    public void setOrdenList(List<Orden> ordenList) {
        this.ordenList = ordenList;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<Lugar> getLugarList() {
        return lugarList;
    }
public List<String> getLugarListNombre() {
    List<String> myList = new ArrayList<String>();
    for(int i=0;i<=lugarList.size();i++)  {
        myList.add(lugarList.get(i).getNombreLugar());
    }  
    return myList;
    }
    public void setLugarList(List<Lugar> lugarList) {
        this.lugarList = lugarList;
    }

    public Lugar getFkLugar() {
        return fkLugar;
    }

    public void setFkLugar(Lugar fkLugar) {
        this.fkLugar = fkLugar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLugar != null ? idLugar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lugar)) {
            return false;
        }
        Lugar other = (Lugar) object;
        if ((this.idLugar == null && other.idLugar != null) || (this.idLugar != null && !this.idLugar.equals(other.idLugar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Lugar[ idLugar=" + idLugar + " ]";
    }
    
}

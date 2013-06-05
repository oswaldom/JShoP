/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios.dominio;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import modelo.Producto;

/**
 *
 * @author mario
 */

@XmlRootElement(name="categoria")

public class CategoriaREST {
//    @XmlElement(name="id")
    private Integer idCategoria;
//    @XmlElement(name="nombre")
    private String nombreCategoria;
//    @XmlAttribute
    
    private List<Producto> productoList;

    public CategoriaREST() {
    }

    public CategoriaREST(Integer idCategoria, String nombreCategoria, List<Producto> productoList) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.productoList = productoList;
    }
    @XmlElement(name="id")
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    @XmlElement(name="nombre")
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    @XmlElementWrapper(name="productos")
    @XmlElement(name="producto")
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    
    
}

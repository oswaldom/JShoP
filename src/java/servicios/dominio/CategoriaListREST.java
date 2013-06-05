/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios.dominio;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mario
 */
@XmlRootElement(name="categorias")
public class CategoriaListREST {
    private List<CategoriaREST> categorias;

    public CategoriaListREST(List<CategoriaREST> categorias) {
        this.categorias = categorias;
    }

    public CategoriaListREST() {
    }

//    @XmlElementWrapper(name="categorias")
    @XmlElement(name="categoria")
    public List<CategoriaREST> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaREST> categorias) {
        this.categorias = categorias;
    }
    
}

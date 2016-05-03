/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.produce.produccion.dao.dominio;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bpalacios
 */
@Entity
@Table(name = "TIPO_USUARIO", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t"),
    @NamedQuery(name = "TipoUsuario.findByIdtipoUsuario", query = "SELECT t FROM TipoUsuario t WHERE t.idtipoUsuario = :idtipoUsuario"),
    @NamedQuery(name = "TipoUsuario.findByNombreTipoUsuario", query = "SELECT t FROM TipoUsuario t WHERE t.nombreTipoUsuario = :nombreTipoUsuario"),
    @NamedQuery(name = "TipoUsuario.findByEstado", query = "SELECT t FROM TipoUsuario t WHERE t.estado = :estado")})
public class TipoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPO_USUARIO")
    private Short idtipoUsuario;
    @Basic(optional = false)
    @Column(name = "NOMBRE_TIPO_USUARIO")
    private String nombreTipoUsuario;
    @Basic(optional = false)
    private short estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUsuario")
    private List<Usuario> usuarioList;

    public TipoUsuario() {
    }

    public TipoUsuario(Short idtipoUsuario) {
        this.idtipoUsuario = idtipoUsuario;
    }

    public TipoUsuario(Short idtipoUsuario, String nombreTipoUsuario, short estado) {
        this.idtipoUsuario = idtipoUsuario;
        this.nombreTipoUsuario = nombreTipoUsuario;
        this.estado = estado;
    }

    public Short getIdtipoUsuario() {
        return idtipoUsuario;
    }

    public void setIdtipoUsuario(Short idtipoUsuario) {
        this.idtipoUsuario = idtipoUsuario;
    }

    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    public void setNombreTipoUsuario(String nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoUsuario != null ? idtipoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.idtipoUsuario == null && other.idtipoUsuario != null) || (this.idtipoUsuario != null && !this.idtipoUsuario.equals(other.idtipoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.produce.produccion.dao.dominio.TipoUsuario[ idtipoUsuario=" + idtipoUsuario + " ]";
    }
    
}

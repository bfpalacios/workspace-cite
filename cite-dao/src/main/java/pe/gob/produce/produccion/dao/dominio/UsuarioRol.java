/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.produce.produccion.dao.dominio;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bpalacios
 */
@Entity
@Table(name = "USUARIO_ROL", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioRol.findAll", query = "SELECT u FROM UsuarioRol u"),
    @NamedQuery(name = "UsuarioRol.findByIdusuarioRol", query = "SELECT u FROM UsuarioRol u WHERE u.idusuarioRol = :idusuarioRol"),
    @NamedQuery(name = "UsuarioRol.findByIdUsuario", query = "SELECT u FROM UsuarioRol u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "UsuarioRol.findByRol", query = "SELECT u FROM UsuarioRol u WHERE u.rol = :rol")})
public class UsuarioRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDUSUARIO_ROL")
    private Long idusuarioRol;
    @Column(name = "ID_USUARIO")
    private String idUsuario;
    private String rol;
    @JoinColumn(name = "IDROL", referencedColumnName = "ID_ROL")
    @ManyToOne(optional = false)
    private Rol idrol;
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    @ManyToOne(optional = false)
    private Usuario idusuario;

    public UsuarioRol() {
    }

    public UsuarioRol(Long idusuarioRol) {
        this.idusuarioRol = idusuarioRol;
    }

    public Long getIdusuarioRol() {
        return idusuarioRol;
    }

    public void setIdusuarioRol(Long idusuarioRol) {
        this.idusuarioRol = idusuarioRol;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Rol getIdrol() {
        return idrol;
    }

    public void setIdrol(Rol idrol) {
        this.idrol = idrol;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuarioRol != null ? idusuarioRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRol)) {
            return false;
        }
        UsuarioRol other = (UsuarioRol) object;
        if ((this.idusuarioRol == null && other.idusuarioRol != null) || (this.idusuarioRol != null && !this.idusuarioRol.equals(other.idusuarioRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.produce.produccion.dao.dominio.UsuarioRol[ idusuarioRol=" + idusuarioRol + " ]";
    }
    
}

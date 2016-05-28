package pe.gob.produce.produccion.model;

import java.util.List;

import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.cite.bo.UsuarioRolBO;

@Component("usuarioModel")
@ViewScoped
public class UsuarioModel {

	private String idUsuario;
	private String clave;
	private String confirmarClave;
	private String nombres;
	private String directorEjecutivo;
	private String paterno;
	private String materno;
	private String correo;
	private String direccion;
	private String telefono;
	private String codCite;
	private String codSede;
	private String codDependencia;
	private String codDepartamento;
	private String codProvincia;
	private String codDistrito;
	private String telefono2;
	private String cargo;
	
	private String portal;
	private String dni;
	private String tipoDoc;
	private String nroDoc;
	private String rubro;
	private String email1;
	private String email2;
	private String rol;
	private String perfil;
	private String emailAdmin;	
	private UbigeoModel ubigeo;
	private List<CITESModel> listCite; 
	private List<DependenciaBO> listDependencia;
	private List<UbigeoModel> listUbigeo;
	private List<UbigeoModel> listProvincia;
	private List<UbigeoModel> listDistrito;
	private List<SedeBO> listarSedes;
	private List<SedeBO> listSedes;
	private UsuarioBO usuario;
	private String codAlumno;
	private int planAlumno;
	private UsuarioRolBO usuarioRol;
	private List<UsuarioBO> rolesUsuario;
	private String codigoDependencia;
	private CITEBO cite;	
	private DependenciaBO dependencia;
	private SedeBO sede;	
	private int dni1;
	
	public String getCodCite() {
		return codCite;
	}

	public void setCodCite(String codCite) {
		this.codCite = codCite;
	}

	public String getCodSede() {
		return codSede;
	}

	public void setCodSede(String codSede) {
		this.codSede = codSede;
	}

	public String getCodDependencia() {
		return codDependencia;
	}

	public void setCodDependencia(String codDependencia) {
		this.codDependencia = codDependencia;
	}
 

	public int getDni1() {
		return dni1;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public CITEBO getCite() {
		return cite;
	}




	public void setCite(CITEBO cite) {
		this.cite = cite;
	}




	public DependenciaBO getDependencia() {
		return dependencia;
	}




	public void setDependencia(DependenciaBO dependencia) {
		this.dependencia = dependencia;
	}




	public SedeBO getSede() {
		return sede;
	}




	public void setSede(SedeBO sede) {
		this.sede = sede;
	}




	public void setDni1(int dni1) {
		this.dni1 = dni1;
	}




	public String getCodigoDependencia() {
		return codigoDependencia;
	}




	public void setCodigoDependencia(String codigoDependencia) {
		this.codigoDependencia = codigoDependencia;
	}


 




	public UsuarioModel () {
		this.idUsuario = null;
		this.clave = null;
	}
	
	


	public String getDirectorEjecutivo() {
		return directorEjecutivo;
	}




	public void setDirectorEjecutivo(String directorEjecutivo) {
		this.directorEjecutivo = directorEjecutivo;
	}




	public String getConfirmarClave() {
		return confirmarClave;
	}




	public void setConfirmarClave(String confirmarClave) {
		this.confirmarClave = confirmarClave;
	}




	public List<UbigeoModel> getListProvincia() {
		return listProvincia;
	}


	public void setListProvincia(List<UbigeoModel> listProvincia) {
		this.listProvincia = listProvincia;
	}


	public List<UbigeoModel> getListDistrito() {
		return listDistrito;
	}


	public void setListDistrito(List<UbigeoModel> listDistrito) {
		this.listDistrito = listDistrito;
	}


	/**
	 * @return the codDepartamento
	 */
	public String getCodDepartamento() {
		return codDepartamento;
	}


	/**
	 * @param codDepartamento the codDepartamento to set
	 */
	public void setCodDepartamento(String codDepartamento) {
		this.codDepartamento = codDepartamento;
	}


	/**
	 * @return the codProvincia
	 */
	public String getCodProvincia() {
		return codProvincia;
	}


	/**
	 * @param codProvincia the codProvincia to set
	 */
	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}


	/**
	 * @return the codDistrito
	 */
	public String getCodDistrito() {
		return codDistrito;
	}


	/**
	 * @param codDistrito the codDistrito to set
	 */
	public void setCodDistrito(String codDistrito) {
		this.codDistrito = codDistrito;
	}


	/**
	 * @return the idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}


	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}


	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}


	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}


	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}


	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


	/**
	 * @return the paterno
	 */
	public String getPaterno() {
		return paterno;
	}


	/**
	 * @param paterno the paterno to set
	 */
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}


	/**
	 * @return the materno
	 */
	public String getMaterno() {
		return materno;
	}


	/**
	 * @param materno the materno to set
	 */
	public void setMaterno(String materno) {
		this.materno = materno;
	}


	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}


	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}


	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}


	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}


	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	/**
	 * @return the telefono2
	 */
	public String getTelefono2() {
		return telefono2;
	}


	/**
	 * @param telefono2 the telefono2 to set
	 */
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}


	/**
	 * @return the portal
	 */
	public String getPortal() {
		return portal;
	}


	/**
	 * @param portal the portal to set
	 */
	public void setPortal(String portal) {
		this.portal = portal;
	}


	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}


	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}


	/**
	 * @return the tipoDoc
	 */
	public String getTipoDoc() {
		return tipoDoc;
	}


	/**
	 * @param tipoDoc the tipoDoc to set
	 */
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}


	/**
	 * @return the nroDoc
	 */
	public String getNroDoc() {
		return nroDoc;
	}


	/**
	 * @param nroDoc the nroDoc to set
	 */
	public void setNroDoc(String nroDoc) {
		this.nroDoc = nroDoc;
	}


	/**
	 * @return the rubro
	 */
	public String getRubro() {
		return rubro;
	}


	/**
	 * @param rubro the rubro to set
	 */
	public void setRubro(String rubro) {
		this.rubro = rubro;
	}


	/**
	 * @return the email1
	 */
	public String getEmail1() {
		return email1;
	}


	/**
	 * @param email1 the email1 to set
	 */
	public void setEmail1(String email1) {
		this.email1 = email1;
	}


	/**
	 * @return the email2
	 */
	public String getEmail2() {
		return email2;
	}


	/**
	 * @param email2 the email2 to set
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}


	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}


	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}


	/**
	 * @return the perfil
	 */
	public String getPerfil() {
		return perfil;
	}


	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}


	/**
	 * @return the emailAdmin
	 */
	public String getEmailAdmin() {
		return emailAdmin;
	}


	/**
	 * @param emailAdmin the emailAdmin to set
	 */
	public void setEmailAdmin(String emailAdmin) {
		this.emailAdmin = emailAdmin;
	}	
	
	
	/**
	 * @return the ubigeo
	 */
	public UbigeoModel getUbigeo() {
		return ubigeo;
	}


	/**
	 * @param ubigeo the ubigeo to set
	 */
	public void setUbigeo(UbigeoModel ubigeo) {
		this.ubigeo = ubigeo;
	}


	/**
	 * @return the listUbigeo
	 */
	public List<UbigeoModel> getListUbigeo() {
		return listUbigeo;
	}


	/**
	 * @param listUbigeo the listUbigeo to set
	 */
	public void setListUbigeo(List<UbigeoModel> listUbigeo) {
		this.listUbigeo = listUbigeo;
	}


	/**
	 * @return the usuario
	 */
	public UsuarioBO getUsuario() {
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}


	/**
	 * @return the codAlumno
	 */
	public String getCodAlumno() {
		return codAlumno;
	}


	/**
	 * @param codAlumno the codAlumno to set
	 */
	public void setCodAlumno(String codAlumno) {
		this.codAlumno = codAlumno;
	}


	/**
	 * @return the planAlumno
	 */
	public int getPlanAlumno() {
		return planAlumno;
	}


	/**
	 * @param planAlumno the planAlumno to set
	 */
	public void setPlanAlumno(int planAlumno) {
		this.planAlumno = planAlumno;
	}


	/**
	 * @return the usuarioRol
	 */
	public UsuarioRolBO getUsuarioRol() {
		return usuarioRol;
	}


	/**
	 * @param usuarioRol the usuarioRol to set
	 */
	public void setUsuarioRol(UsuarioRolBO usuarioRol) {
		this.usuarioRol = usuarioRol;
	}


	/**
	 * @return the rolesUsuario
	 */
	public List<UsuarioBO> getRolesUsuario() {
		return rolesUsuario;
	}


	/**
	 * @param rolesUsuario the rolesUsuario to set
	 */
	public void setRolesUsuario(List<UsuarioBO> rolesUsuario) {
		this.rolesUsuario = rolesUsuario;
	}

	public List<CITESModel> getListCite() {
		return listCite;
	}

	public void setListCite(List<CITESModel> listCite) {
		this.listCite = listCite;
	}
 

	public List<DependenciaBO> getListDependencia() {
		return listDependencia;
	}

	public void setListDependencia(List<DependenciaBO> listDependencia) {
		this.listDependencia = listDependencia;
	}

	public List<SedeBO> getListSedes() {
		return listSedes;
	}

	public void setListSedes(List<SedeBO> listSedes) {
		this.listSedes = listSedes;
	}

	public List<SedeBO> getListarSedes() {
		return listarSedes;
	}

	public void setListarSedes(List<SedeBO> listarSedes) {
		this.listarSedes = listarSedes;
	}
	
	
	 	
}

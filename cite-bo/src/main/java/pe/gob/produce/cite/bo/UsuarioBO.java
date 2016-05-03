package pe.gob.produce.cite.bo;

import java.io.Serializable;
import java.util.List;

public class UsuarioBO implements Serializable {

	private static final long serialVersionUID = 1101118440186893647L;
	
	private String idRol;
	private String role;
	private String idUsuario;
	private String contrasenia;
	private String confirmacionContrasenia;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String correo;
	private String direccion;
	private String telefono;
	private String codAlumno;
	private String dni;
	private String tipoDoc;
	private String rubro;
	private String email1;
	private String email2;
	private String emailAdmin;	
	private EmpresaBO Empresa;
	private UbigeoBO ubigeo;
	private List<UbigeoBO> listUbigeo;
	private List<RolBO> listRol;

	public UsuarioBO() {
	}

	
	public String getConfirmacionContrasenia() {
		return confirmacionContrasenia;
	}


	public void setConfirmacionContrasenia(String confirmacionContrasenia) {
		this.confirmacionContrasenia = confirmacionContrasenia;
	}


	/**
	 * @return the empresa
	 */
	public EmpresaBO getEmpresa() {
		return Empresa;
	}



	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(EmpresaBO empresa) {
		Empresa = empresa;
	}



	/**
	 * @return the ubigeo
	 */
	public UbigeoBO getUbigeo() {
		return ubigeo;
	}



	/**
	 * @param ubigeo the ubigeo to set
	 */
	public void setUbigeo(UbigeoBO ubigeo) {
		this.ubigeo = ubigeo;
	}



	/**
	 * @return the listUbigeo
	 */
	public List<UbigeoBO> getListUbigeo() {
		return listUbigeo;
	}



	/**
	 * @param listUbigeo the listUbigeo to set
	 */
	public void setListUbigeo(List<UbigeoBO> listUbigeo) {
		this.listUbigeo = listUbigeo;
	}



	/**
	 * @return the idRol
	 */
	public String getIdRol() {
		return idRol;
	}

	/**
	 * @param idRol the idRol to set
	 */
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
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
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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
	 * @return the listRol
	 */
	public List<RolBO> getListRol() {
		return listRol;
	}

	/**
	 * @param listRol the listRol to set
	 */
	public void setListRol(List<RolBO> listRol) {
		this.listRol = listRol;
	}

	
	
}

package pe.gob.produce.cite.bo;

import java.io.Serializable;

public class SedeBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String codigo;
	private String codigoCite;
	private String descripcion;
	private String descripcionCite;
	private String telefono;
	private String celular;
	private String jefatura;
	private String email;
	private String direccion;
	private UbigeoBO ubigeo; 
	
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public UbigeoBO getUbigeo() {
		return ubigeo;
	}
	public void setUbigeo(UbigeoBO ubigeo) {
		this.ubigeo = ubigeo;
	}
	public String getCodigoCite() {
		return codigoCite;
	}
	public void setCodigoCite(String codigoCite) {
		this.codigoCite = codigoCite;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcionCite() {
		return descripcionCite;
	}
	public void setDescripcionCite(String descripcionCite) {
		this.descripcionCite = descripcionCite;
	}
	
}

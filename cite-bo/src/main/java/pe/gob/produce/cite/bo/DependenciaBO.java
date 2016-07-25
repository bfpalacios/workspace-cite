package pe.gob.produce.cite.bo;

import java.io.Serializable;


public class DependenciaBO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id; 
	private String codigo;
	private String descripcion;
	private String estado;
	private SedeBO sede;
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
	public SedeBO getSede() {
		return sede;
	}
	public void setSede(SedeBO sede) {
		this.sede = sede;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}

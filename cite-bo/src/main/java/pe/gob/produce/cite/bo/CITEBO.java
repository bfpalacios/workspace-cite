package pe.gob.produce.cite.bo;

import java.io.Serializable;
import java.util.Date;

public class CITEBO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String descripcion;
	private Date fecha; 
	private SedeBO sede;
	private String estado;
	
	public SedeBO getSede() {
		return sede;
	}
	public void setSede(SedeBO sede) {
		this.sede = sede;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
	

}

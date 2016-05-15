package pe.gob.produce.cite.bo;

import java.io.Serializable;

public class SedeBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String descripcion;
	private UbigeoBO ubigeo;
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
	
}

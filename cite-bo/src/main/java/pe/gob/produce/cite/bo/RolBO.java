package pe.gob.produce.cite.bo;

import java.io.Serializable;

public class RolBO implements Serializable {

	private static final long serialVersionUID = -5011765534397588828L;
	
	
	private Integer id;
	private String nombre;
	private String descripcion;

	public RolBO() {
	}

	public RolBO(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}

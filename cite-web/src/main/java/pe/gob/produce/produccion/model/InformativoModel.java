package pe.gob.produce.produccion.model;

import org.primefaces.model.StreamedContent;

public class InformativoModel {
	private String id;
	private String titulo;
	private String descripcionCorta;
	private String descripcion;
	private String fecha;
	private StreamedContent imagen;	
	
	public InformativoModel(){		
	}
	
	public InformativoModel(String id, String titulo, String descripcionCorta, String descripcion, String fecha,
			StreamedContent imagen) {
		this.id = id;
		this.titulo = titulo;
		this.descripcionCorta = descripcionCorta;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.imagen = imagen;
	}	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public StreamedContent getImagen() {
		return imagen;
	}
	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}
}
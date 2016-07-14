package pe.gob.produce.produccion.model;

import java.util.Date;

import javax.faces.bean.ViewScoped;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

@Component("informativoModel")
@ViewScoped
public class InformativoModel {
	private String id;
	private String titulo;
	private String descripcionCorta;
	private String descripcion;
	private String fecha;
	private Date fechaCalendario;
	private StreamedContent imagen;

	public InformativoModel() {
	}

	public InformativoModel(String id, String titulo, String descripcionCorta,
			String descripcion, String fecha, StreamedContent imagen) {
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
	public Date getFechaCalendario() {
		return fechaCalendario;
	}

	public void setFechaCalendario(Date fechaCalendario) {
		this.fechaCalendario = fechaCalendario;
	}

	public StreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}
}

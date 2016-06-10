package pe.gob.produce.produccion.model;

import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;

@Component("portadaModel")
@ViewScoped
public class EventoModel {
	private Integer id;
	private String titulo;
	private String descripcion;
	private String fecha;
	private String hora;
	
	public EventoModel(){
		
	}
	
	public EventoModel(Integer id, String titulo, String descripcion,
			String fecha, String hora) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.hora = hora;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
}

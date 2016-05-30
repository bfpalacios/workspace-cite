package pe.gob.produce.cite.bo;

import java.util.Date;

public class EventoBO {
	private Integer id;
	private String titulo;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private int todoElDia;
	public EventoBO(Integer id, String titulo, String descripcion,
			Date fechaInicio, Date fechaFin, int todoElDia) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.todoElDia = todoElDia;
	}
	public EventoBO(){
		
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
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getTodoElDia() {
		return todoElDia;
	}
	public void setTodoElDia(int todoElDia) {
		this.todoElDia = todoElDia;
	}	
}

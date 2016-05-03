package pe.gob.produce.cite.bo;

import java.util.Date;

public class CotizacionBO {
	
	private int codigo;
	private String secuencial;
	private int idCite;	
	private int sede;	
	private Date fecha;
	private int estado;
	private Double costoTotal;
	private ServicioBO servicio;
	private UsuarioBO usuario;	

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Double getCostoTotal() {
		return costoTotal;
	}
	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
	}
	
	public int getSede() {
		return sede;
	}
	public void setSede(int sede) {
		this.sede = sede;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	public int getIdCite() {
		return idCite;
	}
	public void setIdCite(int idCite) {
		this.idCite = idCite;
	}
	public ServicioBO getServicio() {
		return servicio;
	}
	public void setServicio(ServicioBO servicio) {
		this.servicio = servicio;
	}
	public UsuarioBO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
}

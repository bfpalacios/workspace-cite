package pe.gob.produce.cite.bo;

import java.util.Date;

public class CotizacionDetalleBO {
	
	private static final long serialVersionUID = -5011765534397588828L;
	
	private int codigo;
	private String secuencial;
	private int idCite;	
	private int sede;
	private ServicioBO servicio;	
	
	public ServicioBO getServicio() {
		return servicio;
	}
	public void setServicio(ServicioBO servicio) {
		this.servicio = servicio;
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
	public int getSede() {
		return sede;
	}
	public void setSede(int sede) {
		this.sede = sede;
	}

}

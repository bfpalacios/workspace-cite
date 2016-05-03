package com.empresa.proyecto.dto;

import java.util.Date;

public class CotizacionDTO {

	private String numeroCotizacion;
	private String razonSocial;
	private String solicitante;
	private Date fecha;
	private String citeDestino;
	private Double costoTotal;
	private Integer estado;
	private String descEstado;
	

	public String getDescEstado() {
		return descEstado;
	}

	public void setDescEstado(String descEstado) {
		
		this.descEstado = descEstado;
	}

	public CotizacionDTO() {
	}

	public String getNumeroCotizacion() {
		return numeroCotizacion;
	}

	public void setNumeroCotizacion(String numeroCotizacion) {
		this.numeroCotizacion = numeroCotizacion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;	
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCiteDestino() {
		return citeDestino;
	}

	public void setCiteDestino(String citeDestino) {
		this.citeDestino = citeDestino;
	}

	public Double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

}

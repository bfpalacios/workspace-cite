package pe.gob.produce.produccion.model;

import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;

@Component("citesModel")
@RequestScoped
public class CITESModel {
	
	
	private String secuencial;
	private int codigo;
	private String codigoCite;
	private String descripcion;
	private SedeBO sedeBO;
	private DependenciaBO dependencia;
	private String sede;
	private String condicion;
	private String fecha;
	private UsuarioModel usuarioModel;
	public String getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}
	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}
	public String getCodigoCite() {
		return codigoCite;
	}
	public void setCodigoCite(String codigoCite) {
		this.codigoCite = codigoCite;
	}
	public SedeBO getSedeBO() {
		return sedeBO;
	}
	public void setSedeBO(SedeBO sedeBO) {
		this.sedeBO = sedeBO;
	}
	public DependenciaBO getDependencia() {
		return dependencia;
	}
	public void setDependencia(DependenciaBO dependencia) {
		this.dependencia = dependencia;
	}
		
}

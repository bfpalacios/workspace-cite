package pe.gob.produce.produccion.model;

import java.util.List;

import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

import pe.gob.produce.cite.bo.CITEBO;

@Component("serviceModel")
@RequestScoped
public class ServicioModel {

	private String codigo;
	private String sede;
	private String nombre;
	private String descripcion;
	private String nombreSolicitante;
	private String cargo;
	private String telefono;
	private String email;
	private String descripcionCITE;
	private String codigoCITE;
	private String unidad;
	private String requisito;
	private String valorDeVenta;
	private String precioDeVenta;
	private String totalPrecioDeVenta;

	private List<CITEBO> listarCITE;
	
	
	public String getSede() {
		return sede;
	}


	public void setSede(String sede) {
		this.sede = sede;
	}


	public String getTotalPrecioDeVenta() {
		return totalPrecioDeVenta;
	}

	public void setTotalPrecioDeVenta(String totalPrecioDeVenta) {
		this.totalPrecioDeVenta = totalPrecioDeVenta;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getRequisito() {
		return requisito;
	}

	public void setRequisito(String requisito) {
		this.requisito = requisito;
	}

	public String getValorDeVenta() {
		return valorDeVenta;
	}

	public void setValorDeVenta(String valorDeVenta) {
		this.valorDeVenta = valorDeVenta;
	}

	public String getPrecioDeVenta() {
		return precioDeVenta;
	}

	public void setPrecioDeVenta(String precioDeVenta) {
		this.precioDeVenta = precioDeVenta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreSolicitante() {
		return nombreSolicitante;
	}

	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescripcionCITE() {
		return descripcionCITE;
	}

	public void setDescripcionCITE(String descripcionCITE) {
		this.descripcionCITE = descripcionCITE;
	}

	public String getCodigoCITE() {
		return codigoCITE;
	}

	public void setCodigoCITE(String codigoCITE) {
		this.codigoCITE = codigoCITE;
	}

	public List<CITEBO> getListarCITE() {
		return listarCITE;
	}

	public void setListarCITE(List<CITEBO> listarCITE) {
		this.listarCITE = listarCITE;
	}

}

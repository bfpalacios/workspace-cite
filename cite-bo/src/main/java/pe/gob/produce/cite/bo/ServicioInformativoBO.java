package pe.gob.produce.cite.bo;

import java.io.Serializable;
import java.util.Date;

public class ServicioInformativoBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date fecha;
	private String tituloInformativo;
	private TipoDocumentoCiteBO tipoDocumento;
	private String descInformativo;
	private String descCortaInformativo; 
	private byte[] archivoInformativo;
	 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public byte[] getArchivoInformativo() {
		return archivoInformativo;
	}
	public void setArchivoInformativo(byte[] archivoInformativo) {
		this.archivoInformativo = archivoInformativo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTituloInformativo() {
		return tituloInformativo;
	}
	public void setTituloInformativo(String tituloInformativo) {
		this.tituloInformativo = tituloInformativo;
	}
	public String getDescInformativo() {
		return descInformativo;
	}
	public void setDescInformativo(String descInformativo) {
		this.descInformativo = descInformativo;
	}
	public String getDescCortaInformativo() {
		return descCortaInformativo;
	}
	public void setDescCortaInformativo(String descCortaInformativo) {
		this.descCortaInformativo = descCortaInformativo;
	}
	public TipoDocumentoCiteBO getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumentoCiteBO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
		

}

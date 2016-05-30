package pe.gob.produce.cite.bo;

import java.util.Date;

public class NoticiaBO {
 private int id;
 private String tituloInformativo;
 private String descInformativo;
 private String descCortaInformativo;
 private Date fecha;
 private String archivoInformativo;
 
public NoticiaBO(){
	
}
 
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
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
public Date getFecha() {
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}
public String getArchivoInformativo() {
	return archivoInformativo;
}
public void setArchivoInformativo(String archivoInformativo) {
	this.archivoInformativo = archivoInformativo;
}
 
 
}

package pe.gob.produce.produccion.services;

import java.util.List;

import pe.gob.produce.cite.bo.ServicioBO;

public interface ServicioServices {
	
	public List<ServicioBO> buscarServicio(String codigo, String nombre, int idCite);
	public void nuevoServicio(ServicioBO servicio  );
}

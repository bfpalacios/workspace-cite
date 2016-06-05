package pe.gob.produce.produccion.dao;

import java.util.List;

import pe.gob.produce.cite.bo.ServicioInformativoBO;

public interface InformativoDAO {
	public List<ServicioInformativoBO> listarNoticias();
}

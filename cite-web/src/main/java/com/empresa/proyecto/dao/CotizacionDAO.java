package com.empresa.proyecto.dao;

import java.util.List;

import com.empresa.proyecto.dto.CotizacionDTO;
import com.empresa.proyecto.dto.ServicioDTO;
import com.empresa.proyecto.dto.UsuarioDTO;

public interface CotizacionDAO {

	public List<CotizacionDTO> lsCotizacionByUsuario(Integer idusuario);

	public List<ServicioDTO> lsServicioByCotizacion(Integer idcotizacion);

	public void generarOrdenPago(Integer idcotizacion, String rutacomprobante);
	
	public CotizacionDTO getCotizacion(Integer idcotizacion);
	
	public UsuarioDTO getUsuario(String usuario);
	
}

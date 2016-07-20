package pe.gob.produce.produccion.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.RolBO;
import pe.gob.produce.cite.bo.UbigeoBO;
import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.dao.UsuarioIDao;
import pe.gob.produce.produccion.dao.dominio.Usuario;
import pe.gob.produce.produccion.dao.dominio.UsuarioRol;
import pe.gob.produce.produccion.dao.jdbc.ComunDAOImpl;
import pe.gob.produce.produccion.services.UsuarioServices;
import pe.gob.produce.produccion.services.transformer.UsuarioTransformerToBO;
 

@Service("usuarioServices")
@Transactional(value="transactionManager")
public class UsuarioServicesImpl implements UsuarioServices {

	@Autowired
	private UsuarioIDao usuarioDao;
	@Autowired
	private UsuarioTransformerToBO usuarioTransformerToBO;
	private ComunDAOImpl comunDAO;
	
	public UsuarioServicesImpl(){
		setComunDAO(new ComunDAOImpl());
	}
	
	public UsuarioBO obtenerUsuario(String usuario) throws Exception {
		Usuario usuarioEntidad = usuarioDao.obtenerUsuario(usuario);
		UsuarioBO usuarioBO = usuarioTransformerToBO.transformer(usuarioEntidad);
		List<UsuarioRol> listUsuarioRol =  usuarioEntidad.getUsuarioRolList();
		usuarioBO.setListRol(new ArrayList<RolBO>());
		for (UsuarioRol usuarioRol : listUsuarioRol) {
			usuarioBO.getListRol().add(new RolBO(usuarioRol.getRol()));
		}
		return usuarioBO;
	}
	
	public List<UsuarioBO> obtenerRoles(int proceso){	
		try {
			return getComunDAO().obtenerRoles(proceso);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public void grabarUsuario(UsuarioBO usuarioNuevo) throws Exception{
		usuarioDao.grabarUsuario(usuarioNuevo);
	}
	
	
	public String buscarUsuario(String codUsuario) throws Exception{
		return usuarioDao.buscarUsuario(codUsuario);
	}
	
	public String buscarUsuarioEquivalencia(String codUsuario) throws Exception{
		return usuarioDao.buscarUsuarioEquivalencia(codUsuario);
	}
	
	public ComunDAOImpl getComunDAO() {
		return comunDAO;
	}

	public void setComunDAO(ComunDAOImpl comunDAO) {
		this.comunDAO = comunDAO;
	}

	@Override
	public List<UbigeoBO> listUbigeo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioBO> buscarUsuarioCite(String codUsuario,
			String nomUsuario) throws Exception {
		List<UsuarioBO> listaUsuarios = null;
		try {
			listaUsuarios = usuarioDao.buscarUsuarioCite(codUsuario, nomUsuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUsuarios;
	}

	@Override
	public Integer eliminarUsuario(Integer id) {
		// TODO Auto-generated method stub
		return usuarioDao.eliminarUsuario(id);
	}
}

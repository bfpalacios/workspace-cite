package pe.gob.produce.produccion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.cite.bo.UsuarioBO;
import pe.gob.produce.produccion.core.dao.DAOImpl;
import pe.gob.produce.produccion.core.dao.jdbc.Conexion;
import pe.gob.produce.produccion.core.util.TipoInformativo;
import pe.gob.produce.produccion.dao.dominio.Usuario;

@Repository("usuarioDao")
@Transactional
public class UsuarioDaoImpl extends DAOImpl<Usuario, String> implements
		UsuarioIDao {

	public Usuario obtenerUsuario(String usuario) throws Exception {
		Map<String, Object> mapaParametro = new HashMap<>();
		mapaParametro.put("idUsuario", usuario);
		List<Usuario> listUsuario = super.obtenerListEntidadPorParametro(
				Usuario.class, mapaParametro);
		if (listUsuario == null) {
			return null;
		}
		return listUsuario.get(0);
	}

	public void grabarUsuario(UsuarioBO usuarioNuevo) throws SQLException {

		int idUsuario;
		String numeroDocumento = usuarioNuevo.getDni();
		String estado = "1";
		String ubigeo = usuarioNuevo.getCodigoUbigeo();

		Connection con = null;
		CallableStatement cstm = null;

		con = Conexion.obtenerConexion();
		cstm = con
				.prepareCall("{call SP_Nuevo_Usuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setString(1, usuarioNuevo.getIdUsuario());
		cstm.setString(2, usuarioNuevo.getContrasenia());
		cstm.setString(3, usuarioNuevo.getNombres());
		cstm.setString(4, usuarioNuevo.getCargo());
		cstm.setString(5, usuarioNuevo.getCodCITE());
		cstm.setString(6, usuarioNuevo.getCodSede());
		cstm.setString(7, usuarioNuevo.getCodDependencia());

		cstm.setInt(8, Integer.parseInt(usuarioNuevo.getIdRol()));
		cstm.setString(9, usuarioNuevo.getTelefono());
		cstm.setString(10, usuarioNuevo.getTelefono2());
		cstm.setString(11, usuarioNuevo.getEmailAdmin());
		cstm.setString(12, usuarioNuevo.getEmail1());
		cstm.setInt(13, 1);// cambiar por el estado
		cstm.setInt(14, Integer.parseInt(usuarioNuevo.getIdRol()));
		cstm.setString(15, usuarioNuevo.getJefeInmediato());
		cstm.setString(16, usuarioNuevo.getTelefonoJefeInmediato());

		cstm.registerOutParameter(17, java.sql.Types.INTEGER);

		cstm.execute();

		idUsuario = cstm.getInt(17);
		System.out.println("id de usuario " + String.valueOf(idUsuario));
		System.out.println("dni " + usuarioNuevo.getDni());

		if (!numeroDocumento.equals("")) {
			// 1 activo
			grabarUsuarioDocumentos(idUsuario, numeroDocumento, estado);

		}

		grabarUsuarioRecupera(idUsuario, usuarioNuevo.getIdUsuario(),
				usuarioNuevo.getContrasenia(), ubigeo, numeroDocumento,
				usuarioNuevo.getFechaNac());

	}

	public void grabarUsuarioRecupera(int idUsuario, String codUsuario,
			String contrasenia, String ubigeo, String dni, java.util.Date date)
			throws SQLException {

		Connection con = null;
		CallableStatement cstm = null;
		Date dateCite = new Date(date.getTime());

		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Nuevo_Usuario_Recupera(?,?,?,?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setInt(1, idUsuario);
		cstm.setString(2, codUsuario);
		cstm.setString(3, contrasenia);
		cstm.setString(4, ubigeo);
		cstm.setString(5, dni);
		cstm.setString(6, dateCite.toString());

		cstm.execute();

	}

	public void grabarUsuarioDirecciones(int idUsuario, String ubigeo,
			String direccion) throws SQLException {

		Connection con = null;
		CallableStatement cstm = null;

		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call Insertar_Usuario_Direcciones(?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setInt(1, idUsuario);
		cstm.setString(2, ubigeo);
		cstm.setString(3, direccion);

		cstm.execute();

	}

	public void grabarUsuarioDocumentos(int idUsuario, String numeroDocumento,
			String estado) throws SQLException {

		Connection con = null;
		CallableStatement cstm = null;

		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call Insertar_Usuario_Documentos(?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setInt(1, idUsuario);
		cstm.setString(2, numeroDocumento);
		cstm.setString(3, estado);
		cstm.execute();

	}

	@Override
	public String buscarUsuario(String codUsuario) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buscarUsuarioEquivalencia(String codUsuario) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioBO> buscarUsuarioCite(String codUsuario,
			String nomUsuario) throws Exception {
		// TODO Auto-generated method stub
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<UsuarioBO> listaUsuario = new ArrayList<>();
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.BuscarUsuarioCites(?,?)}");

			pstmt.setString(1, codUsuario);
			pstmt.setString(2, nomUsuario);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				UsuarioBO usuario = new UsuarioBO();
				usuario.setIdUsuario(rs.getString(1));
				usuario.setCodUsuario(rs.getString(2));
				usuario.setNombres(rs.getString(3));
				usuario.setCargo(rs.getString(4));

				usuario.setNombreCITE(rs.getString(5));
				usuario.setNombreDependencia(rs.getString(6));
				usuario.setTelefono(rs.getString(7));
				usuario.setTelefono2(rs.getString(8));
				usuario.setEmail1(rs.getString(9));
				usuario.setEmailAdmin(rs.getString(10));

				listaUsuario.add(usuario);
			}
		} catch (Exception e) {
			System.out.println("No data found for: " + codUsuario + nomUsuario);
		}
		return listaUsuario;
	}

	@Override
	public Integer eliminarUsuario(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String recuperarContrasenia(UsuarioBO usuario) throws Exception {
		// TODO Auto-generated method stub
		// Date fecNac = new Date(usuario.getFechaNac().getTime());
		System.out.println("Fec naci " + usuario.getFechaNac().getTime());
		System.out.println("Fec nac formato:"
				+ new java.sql.Date(usuario.getFechaNac().getTime()));
		System.out
				.println("Fec nac formato to string :"
						+ new java.sql.Date(usuario.getFechaNac().getTime())
								.toString());
		System.out.println("codigo ubigeo:" + usuario.getCodigoUbigeo());
		System.out.println("Usuario DNI: " + usuario.getCodUsuario()
				+ usuario.getDni());

		Connection con = null;
		CallableStatement cstm = null;

		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call dbo.RecuperarContrasenia(?,?,?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setString(1, usuario.getCodUsuario());
		cstm.setString(2, usuario.getDni());
		cstm.setString(3, usuario.getCodigoUbigeo());
		cstm.setDate(4, new java.sql.Date(usuario.getFechaNac().getTime()));

		cstm.registerOutParameter(5, java.sql.Types.VARCHAR);

		cstm.execute();

		String contrasenia = cstm.getString(5);

		System.out.println("contrasenia: " + contrasenia);

		return contrasenia;
	}

	@Override
	public int validarDNIUsuario(String dni) throws Exception {
		int idValida;

		Connection con = null;
		CallableStatement cstm = null;

		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Validar_DNIUsuario(?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setString(1, dni);

		cstm.registerOutParameter(2, java.sql.Types.INTEGER);

		cstm.execute();

		idValida = cstm.getInt(2);

		// si es idValida 0 no existe el codigo a ingresar
		// si es idValida 1 si existe el codigo a ingresar

		return idValida;
	}

	@Override
	public int validarCodigoUsuario(String codUsuario) throws Exception {
		int idValida;

		Connection con = null;
		CallableStatement cstm = null;

		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Validar_CodigoUsuario(?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setString(1, codUsuario);

		cstm.registerOutParameter(2, java.sql.Types.INTEGER);

		cstm.execute();

		idValida = cstm.getInt(2);

		// si es idValida 0 no existe el codigo a ingresar
		// si es idValida 1 si existe el codigo a ingresar

		return idValida;
	}

}

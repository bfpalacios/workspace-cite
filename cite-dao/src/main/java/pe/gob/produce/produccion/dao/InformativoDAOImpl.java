package pe.gob.produce.produccion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.core.dao.jdbc.BaseDAO;
import pe.gob.produce.produccion.core.dao.jdbc.Conexion;
import pe.gob.produce.produccion.core.util.TipoInformativo;

@Repository("informativoDAO")
@Transactional
public class InformativoDAOImpl extends BaseDAO implements InformativoDAO {

	public InformativoDAOImpl() {
	}

	@Override
	public List<ServicioInformativoBO> listarNoticias(int numNoticias) {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<ServicioInformativoBO> listaNoticias = new ArrayList<ServicioInformativoBO>();
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con
					.prepareStatement("{call dbo.ListarNoticias(?)}");
			pstmt.setInt(1, numNoticias);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ServicioInformativoBO noticia = new ServicioInformativoBO();
				noticia.setId(rs.getInt(1));
				noticia.setTituloInformativo(rs.getString(2));
				noticia.setDescInformativo(rs.getString(3));
				noticia.setDescCortaInformativo(rs.getString(4));
				noticia.setFecha(rs.getDate(5));
				noticia.setArchivoInformativo(rs.getBytes(6));
				listaNoticias.add(noticia);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return listaNoticias;
	}

	@Override
	public List<ServicioInformativoBO> listarNoticiasPorMes(int anio, int mes) {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<ServicioInformativoBO> listaNoticias = new ArrayList<>();

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(anio, mes, 1);
			Date fechaInicioConsulta = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date fechaFinConsulta = calendar.getTime();

			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con
					.prepareStatement("{call dbo.ListarNoticiasPorMes(?,?)}");
			pstmt.setDate(1, new java.sql.Date(fechaInicioConsulta.getTime()));
			pstmt.setDate(2, new java.sql.Date(fechaFinConsulta.getTime()));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ServicioInformativoBO noticia = new ServicioInformativoBO();
				noticia.setId(rs.getInt(1));
				noticia.setTituloInformativo(rs.getString(2));
				noticia.setDescInformativo(rs.getString(3));
				noticia.setDescCortaInformativo(rs.getString(4));
				noticia.setFecha(rs.getDate(5));
				noticia.setArchivoInformativo(rs.getBytes(6));
				listaNoticias.add(noticia);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return listaNoticias;
	}

	@Override
	public List<ServicioInformativoBO> listarPublicaciones(int numNoticias) {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<ServicioInformativoBO> listaPublicaciones = new ArrayList<ServicioInformativoBO>();
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con
					.prepareStatement("{call dbo.ListarPublicaciones(?)}");
			pstmt.setInt(1, numNoticias);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ServicioInformativoBO publicacion = new ServicioInformativoBO();
				publicacion.setId(rs.getInt(1));
				publicacion.setTituloInformativo(rs.getString(2));
				publicacion.setDescInformativo(rs.getString(3));
				publicacion.setDescCortaInformativo(rs.getString(4));
				publicacion.setFecha(rs.getDate(5));
				publicacion.setArchivoInformativo(rs.getBytes(6));
				listaPublicaciones.add(publicacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return listaPublicaciones;
	}

	@Override
	public List<ServicioInformativoBO> listarPublicacionesPorMes(int anio,
			int mes) {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<ServicioInformativoBO> listaPublicaciones = new ArrayList<>();

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(anio, mes, 1);
			Date fechaInicioConsulta = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date fechaFinConsulta = calendar.getTime();

			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con
					.prepareStatement("{call dbo.ListarPublicacionesPorMes(?,?)}");
			pstmt.setDate(1, new java.sql.Date(fechaInicioConsulta.getTime()));
			pstmt.setDate(2, new java.sql.Date(fechaFinConsulta.getTime()));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ServicioInformativoBO publicacion = new ServicioInformativoBO();
				publicacion.setId(rs.getInt(1));
				publicacion.setTituloInformativo(rs.getString(2));
				publicacion.setDescInformativo(rs.getString(3));
				publicacion.setDescCortaInformativo(rs.getString(4));
				publicacion.setFecha(rs.getDate(5));
				publicacion.setArchivoInformativo(rs.getBytes(6));
				listaPublicaciones.add(publicacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return listaPublicaciones;
	}

	@Override
	public ServicioInformativoBO obtenerInformativo(int id, TipoInformativo tipo) {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		ServicioInformativoBO informativo = null;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			if (tipo == TipoInformativo.NOTICIA) {
				pstmt = con.prepareStatement("{call dbo.ObtenerNoticia(?)}");
			} else if (tipo == TipoInformativo.PUBLICACION) {
				pstmt = con.prepareStatement("{call dbo.ObtenerPublicacion(?)}");
			}
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				informativo = new ServicioInformativoBO();
				informativo.setId(rs.getInt(1));
				informativo.setTituloInformativo(rs.getString(2));
				informativo.setDescInformativo(rs.getString(3));
				informativo.setDescCortaInformativo(rs.getString(4));
				informativo.setFecha(rs.getDate(5));
				informativo.setArchivoInformativo(rs.getBytes(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return informativo;
	}

	@Override
	public List<ServicioInformativoBO> buscarInformativo(String titulo,
			Date fecha, TipoInformativo tipo) {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<ServicioInformativoBO> listaInformativo = new ArrayList<>();
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			if (tipo == TipoInformativo.NOTICIA) {
				pstmt = con.prepareStatement("{call dbo.BuscarNoticias(?,?)}");
			} else if (tipo == TipoInformativo.PUBLICACION) {
				pstmt = con.prepareStatement("{call dbo.BuscarPublicaciones(?,?)}");
			}
			pstmt.setString(1, titulo);
			if(fecha != null){
				pstmt.setDate(2, new java.sql.Date(fecha.getTime()));
			}else{
				pstmt.setDate(2, null);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ServicioInformativoBO informativo = new ServicioInformativoBO();
				informativo.setId(rs.getInt(1));
				informativo.setTituloInformativo(rs.getString(2));
				informativo.setDescInformativo(rs.getString(3));
				informativo.setDescCortaInformativo(rs.getString(4));
				informativo.setFecha(rs.getDate(5));
				informativo.setArchivoInformativo(rs.getBytes(6));
				listaInformativo.add(informativo);
			}
		} catch (Exception e) {
			System.out.println("No data found for: " + titulo + fecha);
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return listaInformativo;
	}

	@Override
	public Integer actualizarInformativo(ServicioInformativoBO informativo,
			TipoInformativo tipo) {
		Connection con = null;
		Statement statement = null;
		int rs = 0;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			if (tipo == TipoInformativo.NOTICIA) {
				pstmt = con.prepareStatement("{call dbo.ActualizarNoticia(?,?,?,?,?,?)}");
			} else if (tipo == TipoInformativo.PUBLICACION) {
				pstmt = con.prepareStatement("{call dbo.ActualizarPublicacion(?,?,?,?,?,?)}");
			}
			pstmt.setString(1, informativo.getTituloInformativo());
			pstmt.setString(2, informativo.getDescCortaInformativo());
			pstmt.setString(3, informativo.getDescInformativo());
			pstmt.setDate(4, new java.sql.Date(informativo.getFecha().getTime()));
			pstmt.setBytes(5, informativo.getArchivoInformativo());
			pstmt.setInt(6, informativo.getId());
			rs = pstmt.executeUpdate();			
		} catch (Exception e) {
			System.out.println("No data updated for: " + informativo.getTituloInformativo() + informativo.getFecha());
		} finally {
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return rs;
	}

	@Override
	public Integer eliminarInformativo(Integer id, TipoInformativo tipo) {
		Connection con = null;
		Statement statement = null;
		int rs = 0;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			if (tipo == TipoInformativo.NOTICIA) {
				pstmt = con.prepareStatement("{call dbo.EliminarNoticia(?)}");
			} else if (tipo == TipoInformativo.PUBLICACION) {
				pstmt = con.prepareStatement("{call dbo.EliminarPublicacion(?)}");
			}
			pstmt.setInt(1, id);
			rs = pstmt.executeUpdate();			
		} catch (Exception e) {
			System.out.println("No data deleted for: " + id);
		} finally {
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return rs;
	}
}
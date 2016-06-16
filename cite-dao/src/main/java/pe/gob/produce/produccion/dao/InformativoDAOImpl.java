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

@Repository("informativoDAO")
@Transactional
public class InformativoDAOImpl extends BaseDAO implements InformativoDAO {
		
	public InformativoDAOImpl(){		
	}
	@Override
	public List<ServicioInformativoBO> listarNoticias(int numNoticias) {
		Connection con = null;		
		Statement statement = null;
		ResultSet rs = null;
		List<ServicioInformativoBO> listaNoticias = new ArrayList<ServicioInformativoBO>();		
		try{			
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con.prepareStatement("{call dbo.ListarNoticias(?)}");
			pstmt.setInt(1, numNoticias);
		    rs = pstmt.executeQuery();			
			while(rs.next()){				
				ServicioInformativoBO noticia = new ServicioInformativoBO();
				noticia.setId(rs.getInt(1));
				noticia.setTituloInformativo(rs.getString(2));
				noticia.setDescInformativo(rs.getString(3));
				noticia.setDescCortaInformativo(rs.getString(4));
				noticia.setFecha(rs.getDate(5));
				noticia.setArchivoInformativo(rs.getBytes(6));
				listaNoticias.add(noticia);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
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
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(anio, mes, 1);
			Date fechaInicioConsulta = calendar.getTime();
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	        Date fechaFinConsulta = calendar.getTime();
			
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con.prepareStatement("{call dbo.ListarNoticiasPorMes(?,?)}");
			pstmt.setDate(1, new java.sql.Date(fechaInicioConsulta.getTime()));
			pstmt.setDate(2, new java.sql.Date(fechaFinConsulta.getTime()));
		    rs = pstmt.executeQuery();			
			while(rs.next()){				
				ServicioInformativoBO noticia = new ServicioInformativoBO();
				noticia.setId(rs.getInt(1));
				noticia.setTituloInformativo(rs.getString(2));
				noticia.setDescInformativo(rs.getString(3));
				noticia.setDescCortaInformativo(rs.getString(4));
				noticia.setFecha(rs.getDate(5));
				noticia.setArchivoInformativo(rs.getBytes(6));
				listaNoticias.add(noticia);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
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
		try{			
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con.prepareStatement("{call dbo.ListarPublicaciones(?)}");
			pstmt.setInt(1, numNoticias);
		    rs = pstmt.executeQuery();			
			while(rs.next()){				
				ServicioInformativoBO publicacion = new ServicioInformativoBO();
				publicacion.setId(rs.getInt(1));
				publicacion.setTituloInformativo(rs.getString(2));
				publicacion.setDescInformativo(rs.getString(3));
				publicacion.setDescCortaInformativo(rs.getString(4));
				publicacion.setFecha(rs.getDate(5));
				publicacion.setArchivoInformativo(rs.getBytes(6));
				listaPublicaciones.add(publicacion);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
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
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(anio, mes, 1);
			Date fechaInicioConsulta = calendar.getTime();
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	        Date fechaFinConsulta = calendar.getTime();
			
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con.prepareStatement("{call dbo.ListarPublicacionesPorMes(?,?)}");
			pstmt.setDate(1, new java.sql.Date(fechaInicioConsulta.getTime()));
			pstmt.setDate(2, new java.sql.Date(fechaFinConsulta.getTime()));
		    rs = pstmt.executeQuery();			
			while(rs.next()){				
				ServicioInformativoBO publicacion = new ServicioInformativoBO();
				publicacion.setId(rs.getInt(1));
				publicacion.setTituloInformativo(rs.getString(2));
				publicacion.setDescInformativo(rs.getString(3));
				publicacion.setDescCortaInformativo(rs.getString(4));
				publicacion.setFecha(rs.getDate(5));
				publicacion.setArchivoInformativo(rs.getBytes(6));
				listaPublicaciones.add(publicacion);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}		
		return listaPublicaciones;
	}
}
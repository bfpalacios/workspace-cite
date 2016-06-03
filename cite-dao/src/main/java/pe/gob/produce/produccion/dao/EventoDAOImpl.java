package pe.gob.produce.produccion.dao;

import java.sql.CallableStatement;
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

import pe.gob.produce.cite.bo.EventoBO;
import pe.gob.produce.produccion.core.dao.jdbc.BaseDAO;
import pe.gob.produce.produccion.core.dao.jdbc.Conexion;

@Repository("eventoDAO")
@Transactional
public class EventoDAOImpl extends BaseDAO implements EventoDAO {
	
	private static int cantidadDeEventos = 4;

	public EventoDAOImpl(){
		
	}
	
	@Override
	public List<EventoBO> listarEventosPorMes(int anio, int mes)
			throws Exception {
		Connection con = null;		
		Statement statement = null;
		ResultSet rs = null;
		List<EventoBO> listaEventos = new ArrayList<EventoBO>();
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(anio, mes, 1);
			Date fechaInicioConsulta = calendar.getTime();
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	        Date fechaFinConsulta = calendar.getTime();
			
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con.prepareStatement("{call dbo.ListarEventosPorMes(?,?)}");
			pstmt.setDate(1, (java.sql.Date) fechaInicioConsulta);
			pstmt.setDate(2, (java.sql.Date) fechaFinConsulta);
		    rs = pstmt.executeQuery();			
			while(rs.next()){				
				EventoBO evento = new EventoBO();
				evento.setId(rs.getInt(1));
				evento.setTitulo(rs.getString(2));
				evento.setDescripcion(rs.getString(3));
				evento.setFechaInicio(rs.getDate(4));
				evento.setFechaFin(rs.getDate(5));
				evento.setTodoElDia(rs.getInt(6));
				listaEventos.add(evento);
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
		return listaEventos;
	}


	@Override
	public List<EventoBO> listarEventos() throws Exception {
		Connection con = null;		
		Statement statement = null;
		ResultSet rs = null;
		List<EventoBO> listaEventos = new ArrayList<EventoBO>();		
		try{			
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con.prepareStatement("{call dbo.ListarEventos(?)}");
			pstmt.setInt(1, cantidadDeEventos);
		    rs = pstmt.executeQuery();			
			while(rs.next()){				
				EventoBO evento = new EventoBO();
				evento.setId(rs.getInt(1));
				evento.setTitulo(rs.getString(2));
				evento.setDescripcion(rs.getString(3));
				evento.setFechaInicio(rs.getDate(4));
				evento.setFechaFin(rs.getDate(5));
				evento.setTodoElDia(rs.getInt(6));
				listaEventos.add(evento);
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
		return listaEventos;
	}
	
	@Override
	public void grabarEvento(EventoBO nuevoEvento) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = Conexion.obtenerConexion();
			cstm = con.prepareCall("{call InsertarEvento(?,?,?,?,?)}");		
			cstm.setQueryTimeout(3);
			cstm.setString(1, nuevoEvento.getTitulo());		
			cstm.setString(2, nuevoEvento.getDescripcion());
			cstm.setDate(3, new java.sql.Date(nuevoEvento.getFechaInicio().getTime())); 
			cstm.setDate(4, new java.sql.Date(nuevoEvento.getFechaFin().getTime()));
			cstm.setInt(5, nuevoEvento.getTodoElDia());	
			
			cstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			this.cerrarStatement(cstm);
			this.cerrarConexion(con);
		}
		
	}
}

package pe.gob.produce.produccion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.EventoBO;
import pe.gob.produce.produccion.core.dao.jdbc.BaseDAO;
import pe.gob.produce.produccion.core.dao.jdbc.Conexion;

@Repository("eventoDAO")
@Transactional
public class EventoDAOImpl extends BaseDAO implements EventoDAO {

	@Override
	public List<EventoBO> listarEventos(String anio, String mes)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void grabarEvento(EventoBO nuevoEvento) throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		try {
			con = Conexion.obtenerConexion();
			cstm = con.prepareCall("{call Insertar_Evento(?,?,?,?,?)}");		
			cstm.setQueryTimeout(3);
			cstm.setString(1, nuevoEvento.getTitulo());		
			cstm.setString(2, nuevoEvento.getDescripcion());
			cstm.setDate(3, (Date) nuevoEvento.getFechaInicio());
			cstm.setDate(4, (Date) nuevoEvento.getFechaFin());
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

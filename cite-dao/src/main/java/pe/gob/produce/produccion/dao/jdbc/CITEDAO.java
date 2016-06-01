package pe.gob.produce.produccion.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.produccion.core.dao.jdbc.BaseDAO;
import pe.gob.produce.produccion.core.dao.jdbc.Conexion;
import pe.gob.produce.produccion.dao.CITEIDAO;


@Repository("citeDAO")
@Transactional
public class CITEDAO extends BaseDAO implements CITEIDAO  {

	@Override
	public List<CITEBO> listarCites() throws Exception {
		Connection con = null;
		
		Statement statement = null;
		ResultSet rs = null;
		String query = null;
		List<CITEBO> listaCites = new ArrayList<CITEBO>();
		
		try{
			con = Conexion.obtenerConexion();
			query = "SELECT ID_CITE, NOMBRE_CITE FROM dbo.CITE";
			statement = con.createStatement();	
			
			rs = statement.executeQuery(query);
			
			while(rs.next()){				
				CITEBO cite = new CITEBO();
				cite.setCodigo(rs.getString(1));
				cite.setDescripcion(rs.getString(2));
				listaCites.add(cite);
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
		return listaCites;
	}

	@Override
	public void grabarNuevaSede(SedeBO sede) throws Exception {
			
			Connection con = null;
			CallableStatement cstm = null;
			
			con = Conexion.obtenerConexion();
			cstm = con.prepareCall("{call SP_Nueva_Sede(?,?,?,?,?,?,?)}");		
			cstm.setQueryTimeout(3);
			cstm.setString(1, sede.getCodigo());		
			cstm.setString(2, sede.getDescripcion());
			cstm.setString(3, sede.getCodigoCite());
			cstm.setString(4, sede.getJefatura());
			cstm.setString(5, sede.getEmail());
			cstm.setString(6, sede.getTelefono());
			cstm.setString(7, sede.getCelular());
			
			cstm.execute();
			
	}

	@Override
	public void grabarNuevaDependencia(DependenciaBO dependencia)
			throws Exception {
			Connection con = null;
			CallableStatement cstm = null;
			
			con = Conexion.obtenerConexion();
			cstm = con.prepareCall("{call SP_Nueva_Dependencia(?,?,?)}");
			cstm.setQueryTimeout(3);
			cstm.setString(1, dependencia.getCodigo());		
			cstm.setString(2, dependencia.getDescripcion());
			cstm.setString(3, dependencia.getSede().getCodigo());
		
			cstm.execute();
		
	}

	@Override
	public List<SedeBO> listarSedes() throws Exception {
Connection con = null;
		
		Statement statement = null;
		ResultSet rs = null;
		String query = null;
		List<SedeBO> listaSedes = new ArrayList<SedeBO>();
		
		try{
			con = Conexion.obtenerConexion();
			query = "SELECT ID_SEDE, NOMBRE_SEDE FROM dbo.SEDE";
			statement = con.createStatement();	
			
			rs = statement.executeQuery(query);
			
			while(rs.next()){				
				SedeBO sede = new SedeBO();
				sede.setCodigo(rs.getString(1));
				sede.setDescripcion(rs.getString(2));
				listaSedes.add(sede);
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
		return listaSedes;
	}

	@Override
	public void grabarNuevaCite(CITEBO cite) throws Exception {

		Connection con = null;
		CallableStatement cstm = null;
		Date dateCite = new Date(cite.getFecha().getTime());
		
		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Nueva_Cite(?,?,?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setString(1, cite.getCodigo());		
		cstm.setString(2, cite.getDescripcion());
		cstm.setString(3, cite.getEstado());
		cstm.setDate(4, dateCite);
		cstm.setString(5, cite.getCodigoUbigeo());
	
		cstm.execute();
	
	}
	

}

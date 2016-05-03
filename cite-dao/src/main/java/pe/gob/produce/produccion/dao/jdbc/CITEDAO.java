package pe.gob.produce.produccion.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.CITEBO;
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
			query = "SELECT * FROM dbo.CITE";
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
	
	

}

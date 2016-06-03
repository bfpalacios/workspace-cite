package pe.gob.produce.produccion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.InformativoBO;
import pe.gob.produce.produccion.core.dao.jdbc.BaseDAO;
import pe.gob.produce.produccion.core.dao.jdbc.Conexion;

@Repository("informativoDAO")
@Transactional
public class InformativoDAOImpl extends BaseDAO implements InformativoDAO {
	
	private static int cantidadDeNoticias = 4;

	@Override
	public List<InformativoBO> listarNoticias() {
		Connection con = null;		
		Statement statement = null;
		ResultSet rs = null;
		List<InformativoBO> listaNoticias = new ArrayList<InformativoBO>();		
		try{			
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = con.prepareStatement("{call dbo.ListarNoticias(?)}");
			pstmt.setInt(1, cantidadDeNoticias);
		    rs = pstmt.executeQuery();			
			while(rs.next()){				
				InformativoBO noticia = new InformativoBO();
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
}
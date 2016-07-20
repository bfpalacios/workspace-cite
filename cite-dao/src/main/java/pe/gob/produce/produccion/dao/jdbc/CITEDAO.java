package pe.gob.produce.produccion.dao.jdbc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.produce.cite.bo.CITEBO;
import pe.gob.produce.cite.bo.DependenciaBO;
import pe.gob.produce.cite.bo.SedeBO;
import pe.gob.produce.cite.bo.ServicioInformativoBO;
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
			cstm = con.prepareCall("{call SP_Nueva_Sede(?,?,?,?,?,?,?,?)}");		
			cstm.setQueryTimeout(3);
			cstm.setString(1, sede.getCodigo());		
			cstm.setString(2, sede.getDescripcion());
			cstm.setString(3, sede.getCodigoCite());
			cstm.setString(4, sede.getJefatura());
			cstm.setString(5, sede.getEmail());
			cstm.setString(6, sede.getTelefono());
			cstm.setString(7, sede.getCelular());
			cstm.setString(8, sede.getDireccion());
			
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

	@Override
	public void grabarInformativo(ServicioInformativoBO servicioInformativo)
			throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		Date dateCite = new Date(servicioInformativo.getFecha().getTime());
		
		InputStream informativo = new ByteArrayInputStream(servicioInformativo.getArchivoInformativo());
		
		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Insertar_Detalle_Infomativo(?,?,?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setString(1, servicioInformativo.getTituloInformativo());		
		cstm.setString(2, servicioInformativo.getDescInformativo());
		cstm.setString(3, servicioInformativo.getDescCortaInformativo());
		cstm.setDate(4, dateCite);
		cstm.setBlob(5, informativo, servicioInformativo.getArchivoInformativo().length);
	
		cstm.execute();
		
	}

	@Override
	public void grabarPublicaciones(ServicioInformativoBO servicioInformativo)
			throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		Date dateCite = new Date(servicioInformativo.getFecha().getTime());
		
		InputStream informativo = new ByteArrayInputStream(servicioInformativo.getArchivoInformativo());
		
		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Insertar_Publicaciones(?,?,?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setString(1, servicioInformativo.getTituloInformativo());		
		cstm.setString(2, servicioInformativo.getDescInformativo());
		cstm.setString(3, servicioInformativo.getDescCortaInformativo());
		cstm.setDate(4, dateCite);
		cstm.setBlob(5, informativo, servicioInformativo.getArchivoInformativo().length);
	
		cstm.execute();
		
	}

	@Override
	public List<CITEBO> buscarCites(String codigo, String nombre,
			java.util.Date fecha) throws Exception {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<CITEBO> listaCites = new ArrayList<>();
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.BuscarCites(?,?,?)}");
			pstmt.setString(1, codigo);
			pstmt.setString(2, nombre);
			if(fecha != null){
				pstmt.setDate(3, new java.sql.Date(fecha.getTime()));
			}else{
				pstmt.setDate(3, null);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CITEBO cite = new CITEBO();
				cite.setId(rs.getInt(1));
				cite.setCodigo(rs.getString(2));
				cite.setDescripcion(rs.getString(3));
				cite.setEstado(rs.getString(4));
				cite.setFecha(rs.getDate(5));
				cite.setCodigoUbigeo(rs.getString(6));
				listaCites.add(cite);
			}
		} catch (Exception e) {
			System.out.println("No data found for: " + codigo + " " + nombre + " " + fecha);
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return listaCites;
	}

	
}

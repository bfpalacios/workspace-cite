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
import pe.gob.produce.cite.bo.TipoDocumentoCiteBO;
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

	@Override
	public Integer actualizarCite(CITEBO cite) throws Exception {
		Connection con = null;
		Statement statement = null;
		int rs = 0;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.ActualizarCite(?,?,?,?,?,?)}");
			
			pstmt.setString(1, cite.getCodigo());
			pstmt.setString(2, cite.getDescripcion());			
			pstmt.setString(3, cite.getEstado());
			pstmt.setDate(4, new java.sql.Date(cite.getFecha().getTime()));
			pstmt.setString(5, cite.getCodigoUbigeo());
			pstmt.setInt(6, cite.getId());
			rs = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			System.out.println("No Cite updated for: " + cite.getDescripcion() + " " + cite.getFecha());
			throw new Exception(e);
		} finally {
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return rs;
	}

	@Override
	public Integer eliminarCite(Integer id) throws Exception {
		Connection con = null;
		Statement statement = null;
		int rs = 0;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.EliminarCite(?)}");			
			pstmt.setInt(1, id);
			rs = pstmt.executeUpdate();			
		} catch (Exception e) {
			System.out.println("No Cite deleted for: " + id);
		} finally {
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return rs;
	}

	@Override
	public List<SedeBO> buscarSedes(String codigoUbigeo, String nombre,
			String codigo) throws Exception {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<SedeBO> listaSedes = new ArrayList<>();
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.BuscarSedes(?,?,?)}");
			pstmt.setString(1, codigoUbigeo);
			pstmt.setString(2, nombre);
			pstmt.setString(3, codigo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SedeBO sede = new SedeBO();
				sede.setId(rs.getInt(1));
				sede.setCodigoCite(rs.getString(2));
				sede.setCodigo(rs.getString(3));
				sede.setDescripcion(rs.getString(4));
				sede.setTelefono(rs.getString(5));
				sede.setCelular(rs.getString(6));
				sede.setJefatura(rs.getString(7));
				sede.setEmail(rs.getString(8));
				listaSedes.add(sede);
			}
		} catch (Exception e) {
			System.out.println("No Sede found for: " + codigoUbigeo + " " + nombre + " " + codigo);
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return listaSedes;
	}

	@Override
	public Integer actualizarSede(SedeBO sede) throws Exception {
		Connection con = null;
		Statement statement = null;
		int rs = 0;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.ActualizarSede(?,?,?,?,?,?,?,?)}");
			
			pstmt.setString(1, sede.getCodigoCite());
			pstmt.setString(2, sede.getCodigo());
			pstmt.setString(3, sede.getDescripcion());			
			pstmt.setString(4, sede.getTelefono());
			pstmt.setString(5, sede.getCelular());
			pstmt.setString(6, sede.getJefatura());			
			pstmt.setString(7, sede.getEmail());
			pstmt.setInt(8, sede.getId());
			rs = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			System.out.println("No Sede updated for: " + sede.getDescripcion() + " " + sede.getCodigo());
			throw new Exception(e);
		} finally {
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return rs;
	}

	@Override
	public Integer eliminarSede(Integer id) throws Exception {
		Connection con = null;
		Statement statement = null;
		int rs = 0;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.EliminarSede(?)}");			
			pstmt.setInt(1, id);
			rs = pstmt.executeUpdate();			
		} catch (Exception e) {
			System.out.println("No Sede deleted for: " + id);
		} finally {
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return rs;
	}

	@Override
	public List<DependenciaBO> buscarDependencias(String codigo, String nombre,
			String codigoSede) throws Exception {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		List<DependenciaBO> listaDependencias = new ArrayList<>();
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.BuscarDependencias(?,?,?)}");			
			pstmt.setString(1, codigo);
			pstmt.setString(2, nombre);
			pstmt.setString(3, codigoSede);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DependenciaBO dep = new DependenciaBO();
				dep.setId(rs.getInt(1));
				dep.setDescripcion(rs.getString(2));
				dep.setCodigo(rs.getString(3));
				String codSede = rs.getString(4);
				List<SedeBO> listaSedes = this.buscarSedes("","",codSede);
				if(listaSedes.size() > 0){
					dep.setSede(listaSedes.get(0));
				}
				listaDependencias.add(dep);
			}
		} catch (Exception e) {
			System.out.println("No Dependencia found for: " + codigo + " " + nombre + " " + codigoSede);
		} finally {
			this.cerrarResultSet(rs);
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return listaDependencias;
	}

	@Override
	public Integer actualizarDependencia(DependenciaBO dependencia)
			throws Exception {
		Connection con = null;
		Statement statement = null;
		int rs = 0;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.ActualizarDependencia(?,?,?,?)}");
			
			pstmt.setString(1, dependencia.getCodigo());
			pstmt.setString(2, dependencia.getDescripcion());
			pstmt.setString(3, dependencia.getSede().getCodigo());	
			pstmt.setInt(4, dependencia.getId());
			rs = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			System.out.println("No Dependencia updated for: " + dependencia.getDescripcion() + " " + dependencia.getCodigo());
			throw new Exception(e);
		} finally {
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return rs;
	}

	@Override
	public Integer eliminarDependencia(Integer id) throws Exception {
		Connection con = null;
		Statement statement = null;
		int rs = 0;
		try {
			con = Conexion.obtenerConexion();
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement("{call dbo.EliminarDependencia(?)}");			
			pstmt.setInt(1, id);
			rs = pstmt.executeUpdate();			
		} catch (Exception e) {
			System.out.println("No Dependencia deleted for: " + id);
		} finally {
			this.cerrarSentenceStatement(statement);
			this.cerrarConexion(con);
		}
		return rs;
	}

	@Override
	public List<TipoDocumentoCiteBO> listarTipoDocumentoCiteBO()
			throws Exception {
Connection con = null;
		
		Statement statement = null;
		ResultSet rs = null;
		String query = null;
		List<TipoDocumentoCiteBO> listaTipoDocumentoCiteBO = new ArrayList<TipoDocumentoCiteBO>();
		
		try{
			con = Conexion.obtenerConexion();
			query = "SELECT CODIGO_TIPO_DOCUMENTO_CITE, NOMBRE_TIPO_DOCUMENTO_CITE FROM dbo.TIPO_DOCUMENTO_CITE";
			statement = con.createStatement();	
			
			rs = statement.executeQuery(query);
			
			while(rs.next()){				
				TipoDocumentoCiteBO tipoDoc = new TipoDocumentoCiteBO();
				tipoDoc.setCodigo(rs.getString(1));
				tipoDoc.setDescripcion(rs.getString(2));
				listaTipoDocumentoCiteBO.add(tipoDoc);
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
		return listaTipoDocumentoCiteBO;
	}

	@Override
	public void grabarDocumentosCites(ServicioInformativoBO servicioInformativo)
			throws Exception {
		Connection con = null;
		CallableStatement cstm = null;
		Date dateCite = new Date(servicioInformativo.getFecha().getTime());
		
		InputStream informativo = new ByteArrayInputStream(servicioInformativo.getArchivoInformativo());
		
		con = Conexion.obtenerConexion();
		cstm = con.prepareCall("{call SP_Insertar_DocumentosCite(?,?,?,?)}");
		cstm.setQueryTimeout(3);
		cstm.setString(1, servicioInformativo.getTituloInformativo());		
		cstm.setString(2, servicioInformativo.getTipoDocumento().getCodigo());
		cstm.setDate(3, dateCite);
		cstm.setBlob(4, informativo, servicioInformativo.getArchivoInformativo().length);
	
		cstm.execute();
		
	}

	
}

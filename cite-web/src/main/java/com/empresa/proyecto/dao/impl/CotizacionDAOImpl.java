package com.empresa.proyecto.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.proyecto.dao.CotizacionDAO;
import com.empresa.proyecto.dto.CotizacionDTO;
import com.empresa.proyecto.dto.ServicioDTO;
import com.empresa.proyecto.dto.UsuarioDTO;


@Repository("cotizacionDAO")
@Transactional
public class CotizacionDAOImpl implements CotizacionDAO {

	// IoC
	private JdbcTemplate jdbcTemplate;

	public void setConexion(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<CotizacionDTO> lsCotizacionByUsuario(Integer idusuario) {

		String sql = " SELECT C.IDCOTIZACION, U.IDUSUARIO, U.APELLIDO_PAT + ' ' + U.APELLIDO_MAT + ' ' + U.NOMBRES AS SOLICITANTE,U.NOMBRES AS NOMBRE, "
				+ " C.FECHA, (SELECT A.NOMBRE_CITE FROM CITE A INNER JOIN CITE_SEDE B ON A.IDCITE=B.IDCITE WHERE B.IDCITE_SEDE = C.IDCITE_SEDE) CITE_DESTINO , "
				+ " C.TOTAL, C.ESTADO FROM COTIZACION C INNER JOIN USUARIO U ON C.IDUSUARIO=U.IDUSUARIO  WHERE U.IDUSUARIO = ?";
		
		RowMapper rowMapper = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				CotizacionDTO c = new CotizacionDTO();
								
				c.setNumeroCotizacion("" + rs.getInt("IDCOTIZACION"));
				c.setRazonSocial(rs.getString("NOMBRE"));
				c.setSolicitante(rs.getString("SOLICITANTE"));
				c.setFecha(rs.getDate("FECHA"));
				c.setCiteDestino(rs.getString("CITE_DESTINO"));
				c.setCostoTotal(rs.getDouble("TOTAL"));
				if (rs.getInt("ESTADO") == 1) {
					c.setDescEstado("Registrado");
				}
				if (rs.getInt("ESTADO") == 2) {
					c.setDescEstado("Aprobado");
				}
				if (rs.getInt("ESTADO") == 3) {
					c.setDescEstado("Archivado");
				}
				
				return c;
			}

			
		};

		List<CotizacionDTO> ls = jdbcTemplate.query(sql, rowMapper, idusuario);
		return ls;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ServicioDTO> lsServicioByCotizacion(Integer idcotizacion) {

		String sql = " SELECT D.IDDETALLECOTIZACION, S.SERVICIO_DESC, S.VALOR_VENTA, S.PRECIO_VENTA, "
				+ " (SELECT UNIDAD_DESC FROM UNIDAD_SERVICIO WHERE IDUNIDAD_SERVICIO=S.UNIDAD_SERVICIO) AS UNIDAD "
				+ " FROM COTIZACION_DETALLE D INNER JOIN SERVICIO S ON D.IDSERVICIO = S.IDSERVICIO "
				+ " WHERE D.IDCOTIZACION = ?";

		RowMapper rowMapper = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				ServicioDTO s = new ServicioDTO();
				s.setCodigo(rs.getInt("IDDETALLECOTIZACION"));
				s.setNombreServicio(rs.getString("SERVICIO_DESC"));
				s.setValorVenta(rs.getDouble("VALOR_VENTA"));
				s.setPrecioVenta(rs.getDouble("PRECIO_VENTA"));
				s.setUnidad(rs.getString("UNIDAD"));
				return s;
			}
		};

		List<ServicioDTO> ls = jdbcTemplate.query(sql, rowMapper, idcotizacion);
		return ls;

	}

	@Override
	public void generarOrdenPago(Integer idcotizacion, String rutacomprobante) {
		String sql = "  INSERT INTO ORDEN_PAGO(IDCOTIZACION, COMPROBANTE) VALUES(?, ?)";
		int ctos = jdbcTemplate.update(sql, idcotizacion, rutacomprobante);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public CotizacionDTO getCotizacion(Integer idcotizacion) {

		String sql = "  SELECT IDCOTIZACION, ESTADO, FECHA, TOTAL " + "	FROM COTIZACION WHERE IDCOTIZACION = ? ";

		RowMapper mapper = new RowMapper() {
			@Override
			public CotizacionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CotizacionDTO c = new CotizacionDTO();
				c.setNumeroCotizacion("" + rs.getInt("IDCOTIZACION"));
				c.setEstado(rs.getInt("ESTADO"));
				c.setFecha(rs.getDate("FECHA"));
				c.setCostoTotal(rs.getDouble("TOTAL"));
				return c;
			}
		};

		CotizacionDTO cotizacion = (CotizacionDTO) jdbcTemplate.queryForObject(sql, mapper, idcotizacion);
		return cotizacion;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public UsuarioDTO getUsuario(String usuario) {

		String sql = "  SELECT IDUSUARIO, ID_USUARIO, NOMBRES + ' ' + APELLIDO_PAT + ' ' + APELLIDO_MAT AS NOMBRE_COMPLETO, "
				+ " TELEFONO1, EMAIL_ADMIN, NOMBRE_CARGO " + " FROM USUARIO WHERE ID_USUARIO = ? ";

		RowMapper mapper = new RowMapper() {
			@Override
			public UsuarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UsuarioDTO u = new UsuarioDTO();
				u.setIdusuario(rs.getInt("IDUSUARIO"));
				u.setUsuario(rs.getString("ID_USUARIO"));
				u.setNombrecompleto(rs.getString("NOMBRE_COMPLETO"));
				u.setTelefono(rs.getString("TELEFONO1"));
				u.setCorreo(rs.getString("EMAIL_ADMIN"));
				u.setCargo(rs.getString("NOMBRE_CARGO"));
				return u;
			}
		};

		UsuarioDTO user = (UsuarioDTO) jdbcTemplate.queryForObject(sql, mapper, usuario);
		return user;

	}

}

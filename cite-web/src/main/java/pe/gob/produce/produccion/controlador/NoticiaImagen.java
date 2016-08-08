package pe.gob.produce.produccion.controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.core.util.TipoInformativo;
import pe.gob.produce.produccion.services.InformativoServices;

@Controller("noticiaImagen")
@ViewScoped
public class NoticiaImagen {

	@Autowired
	private InformativoServices informativoService;

	private int id;
	private TipoInformativo tipoInformativo;

	@PostConstruct
	public void init() {
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String noticiaIdUi = context.getExternalContext()
					.getRequestParameterMap().get("noticiaId");
			if (StringUtils.isNotEmpty(noticiaIdUi)) {
				ServicioInformativoBO informativo = informativoService
						.obtenerInformativo(Integer.parseInt(noticiaIdUi),
								TipoInformativo.NOTICIA);
				if (informativo != null) {
					return new DefaultStreamedContent(new ByteArrayInputStream(
							informativo.getArchivoInformativo()));
				} else {
					throw new IOException("Error cargando imagen");
				}
			} else {
				return new DefaultStreamedContent();
			}
		}
	}

	public StreamedContent getStreamPdf(int publicacionId) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		id = publicacionId;
		
		System.out.println("datos descargar archivo id "+ id + "- " + publicacionId + "-"+ this.tipoInformativo );
		
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			ServicioInformativoBO informativo = null;
			if(this.tipoInformativo == TipoInformativo.PUBLICACION){
				informativo = informativoService
						.obtenerInformativo(id, TipoInformativo.PUBLICACION);
			} else if(this.tipoInformativo == TipoInformativo.DOCUMENTO){
				informativo = informativoService
						.obtenerInformativo(id, TipoInformativo.DOCUMENTO);
			} else {
				throw new IOException("Error cargando archivo: No existe TipoInformativo");
			}
			
			if (informativo != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						informativo.getArchivoInformativo()),"application/pdf", informativo.getTituloInformativo() + ".pdf");
			} else {
				throw new IOException("Error cargando archivo");
			}
		}
	}
	
	public StreamedContent getStreamPdfPub(int publicacionId) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		//id = publicacionId;
		
		System.out.println("datos descargar archivo id "+ id + "- " + publicacionId + "-"+ this.tipoInformativo );
		
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			ServicioInformativoBO informativo = null;
			informativo = informativoService
						.obtenerInformativo(id, TipoInformativo.PUBLICACION);
			
			
			if (informativo != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						informativo.getArchivoInformativo()), "application/pdf", informativo.getTituloInformativo() + ".pdf");
			} else {
				throw new IOException("Error cargando archivo");
			}
		}
	}
	
	public StreamedContent getStreamPdfDoc(int publicacionId) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		id = publicacionId;
		
		System.out.println("datos descargar archivo id "+ id + "- " + publicacionId);
		
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			ServicioInformativoBO informativo = null;
			informativo = informativoService
						.obtenerInformativo(id, TipoInformativo.DOCUMENTO);
			
			
			if (informativo != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						informativo.getArchivoInformativo()),"application/pdf", informativo.getTituloInformativo() + ".pdf");
			} else {
				throw new IOException("Error cargando archivo");
			}
		}
	}

	
	public StreamedContent getStream() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String publicacionIdUi = context.getExternalContext()
					.getRequestParameterMap().get("publicacionId");
			String documentoIdUi = context.getExternalContext()
					.getRequestParameterMap().get("documentoId");
			if (StringUtils.isNotEmpty(publicacionIdUi)) {
				this.id = Integer.parseInt(publicacionIdUi);
				this.tipoInformativo = TipoInformativo.PUBLICACION;
				ServicioInformativoBO informativo = informativoService
						.obtenerInformativo(id,
								TipoInformativo.PUBLICACION);
				if (informativo != null) {
					return new DefaultStreamedContent(new ByteArrayInputStream(
							informativo.getArchivoInformativo()));
				} else {
					throw new IOException("Error cargando archivo pdf para publicacion");
				}
			} else if (StringUtils.isNotEmpty(documentoIdUi)) {
				this.id = Integer.parseInt(documentoIdUi);
				this.tipoInformativo = TipoInformativo.DOCUMENTO;
				ServicioInformativoBO informativo = informativoService
						.obtenerInformativo(id,
								TipoInformativo.DOCUMENTO);
				if (informativo != null) {
					return new DefaultStreamedContent(new ByteArrayInputStream(
							informativo.getArchivoInformativo()));
				} else {
					throw new IOException("Error cargando archivo pdf para documento");
				}
			}else {
				return new DefaultStreamedContent();
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoInformativo getTipoInformativo() {
		return tipoInformativo;
	}

	public void setTipoInformativo(TipoInformativo tipoInformativo) {
		this.tipoInformativo = tipoInformativo;
	}

}

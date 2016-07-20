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
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			ServicioInformativoBO informativo = informativoService
					.obtenerInformativo(id, TipoInformativo.PUBLICACION);
			if (informativo != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						informativo.getArchivoInformativo()));
			} else {
				throw new IOException("Error cargando archivo pdf");
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
			if (StringUtils.isNotEmpty(publicacionIdUi)) {
				this.id = Integer.parseInt(publicacionIdUi);
				ServicioInformativoBO informativo = informativoService
						.obtenerInformativo(Integer.parseInt(publicacionIdUi),
								TipoInformativo.PUBLICACION);
				if (informativo != null) {
					return new DefaultStreamedContent(new ByteArrayInputStream(
							informativo.getArchivoInformativo()));
				} else {
					throw new IOException("Error cargando archivo pdf");
				}
			} else {
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

}

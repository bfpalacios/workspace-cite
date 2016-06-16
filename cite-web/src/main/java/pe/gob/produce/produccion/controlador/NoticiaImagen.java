package pe.gob.produce.produccion.controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.ServicioInformativoBO;
import pe.gob.produce.produccion.services.InformativoServices;

//@ManagedBean
//@ApplicationScoped
@Controller("noticiaImagen")
@ViewScoped
public class NoticiaImagen {

	@Autowired
	private InformativoServices informativoService;

	private List<ServicioInformativoBO> listaNoticias;
	private List<ServicioInformativoBO> listaPublicaciones;

	@PostConstruct
	public void init() {
		listaNoticias = informativoService.listarNoticias(4);
		listaPublicaciones = informativoService.listarPublicaciones(4);
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String noticiaIdUi = context.getExternalContext()
					.getRequestParameterMap().get("noticiaId");
			String publicacionIdUi = context.getExternalContext()
					.getRequestParameterMap().get("publicacionId");
			String noticiaId = null;
			String publicacionId = null;
			byte[] imagenByte = null;

			if (noticiaIdUi != null) {
				for (ServicioInformativoBO informativo : listaNoticias) {
					noticiaId = informativo.getId().intValue() + "";
					if (noticiaId.equals(noticiaIdUi)) {
						imagenByte = informativo.getArchivoInformativo();
						break;
					}
				}
			}
			if (publicacionIdUi != null) {
				for (ServicioInformativoBO informativo : listaPublicaciones) {
					publicacionId = informativo.getId().intValue() + "";
					if (publicacionId.equals(publicacionIdUi)) {
						imagenByte = informativo.getArchivoInformativo();
						break;
					}
				}
			}
			if (imagenByte != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						imagenByte));
			} else {
				throw new IOException("Error cargando imagen");
			}
		}
	}
}

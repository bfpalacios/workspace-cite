package pe.gob.produce.produccion.controlador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.gob.produce.cite.bo.EventoBO;
import pe.gob.produce.produccion.model.EventoModel;
import pe.gob.produce.produccion.services.EventoServices;
import pe.gob.produce.produccion.services.InformativoServices;

@Controller("portadaMBean")
@ViewScoped
public class PortadaMBean {

	@Autowired
	private EventoServices eventoService;

	@Autowired
	private InformativoServices informativoService;

	private List<EventoModel> listaEventos;

	@SuppressWarnings("static-access")
	@PostConstruct
	public void init() {
		try {
			listaEventos = new ArrayList<>();
			for (EventoBO eventoBO : eventoService.listarEventos()) {
				System.out.println(eventoBO.getFechaInicio());
				EventoModel eventoModel = new EventoModel();
				eventoModel.setId(eventoBO.getId());
				eventoModel.setTitulo(eventoBO.getTitulo());
				eventoModel.setDescripcion(eventoBO.getDescripcion());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(eventoBO.getFechaInicio());
				String fecha = completarCadena(
						calendar.get(calendar.DAY_OF_MONTH) + "", 2)
						+ " " + obtenerMes(calendar.get(calendar.MONTH) + 1);
				eventoModel.setFecha(fecha);
				String hora = completarCadena(calendar.get(calendar.HOUR) + "",
						2);
				String min = completarCadena(
						calendar.get(calendar.MINUTE) + "", 2);
				String horaEvento = hora + ":" + min + " " + calendar.get(calendar.AM_PM);
				eventoModel.setHora(horaEvento);
				listaEventos.add(eventoModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String completarCadena(String cad, int tamanio) {
		if (cad.length() < tamanio) {
			return StringUtils.leftPad(cad, tamanio, "0");
		}
		return cad;
	}

	private String obtenerMes(int mes) {
		switch (mes) {
		case 1:
			return "ENE";
		case 2:
			return "FEB";
		case 3:
			return "MAR";
		case 4:
			return "ABR";
		case 5:
			return "MAY";
		case 6:
			return "JUN";
		case 7:
			return "JUL";
		case 8:
			return "AGO";
		case 9:
			return "SET";
		case 10:
			return "OCT";
		case 11:
			return "NOV";
		case 12:
			return "DIC";
		default:
			return "MES INVALIDO";
		}
	}

	// constructor
	public PortadaMBean() {
		System.out.println("::::: LOADING PortadaMBean ::::::::");
		// portadaModel = new PortadaModel();
	}

	public String cargarBienvenida() {
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/bienvenida.xhtml";
	}

	public String cargarRedNoticias() {
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/redNoticias.xhtml";
	}

	public String cargarCalendarioEventos() {
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/calendarioEventos.xhtml";
	}

	public String cargarPublicacionesInformativas() {
		return "/paginas/ModuloProduccion/cite/portadaPrincipal/publicacionesInformativas.xhtml";
	}

	public List<EventoModel> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(List<EventoModel> listaEventos) {
		this.listaEventos = listaEventos;
	}

}

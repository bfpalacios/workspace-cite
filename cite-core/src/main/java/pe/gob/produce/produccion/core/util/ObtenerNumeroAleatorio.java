package pe.gob.produce.produccion.core.util;

import java.util.Random;

public class ObtenerNumeroAleatorio {
	
	
	public int obtenerNumeroAleatorioEntero()
	{
		 Random rnd = new Random();
		 
		 
		return (int)(rnd.nextDouble() * 100 + 10);  
	}
}

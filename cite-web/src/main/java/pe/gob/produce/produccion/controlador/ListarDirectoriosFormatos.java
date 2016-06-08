package pe.gob.produce.produccion.controlador;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ListarDirectorios
 */
@WebServlet("/ListarDirectoriosFormatos")
public class ListarDirectoriosFormatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String FORMATO_PDF="pdf";
	public static final String FORMATO_XLS="xls";
	public static final String FORMATO_CONTENT_TYPE="application/";
	public static final String FORMATO_CONTENT_HEADER="Content-disposition";
	public static final String FORMATO_ATTACHMENT="attachment; filename=";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarDirectoriosFormatos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      response.setContentType("text/html");
	      System.out.println("doGet");
	      String url=Constantes.urlFormatos;

	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      
	      File dir = new File(url);
	      String[] ficheros = dir.list();
	      
	      String urlsite="";
	     
	      
	      for(int i=request.getRequestURL().toString().length();i>0;i--){
		  if(request.getRequestURL().toString().substring(i-1, i).equals("/")){
		      urlsite=request.getRequestURL().toString().substring(0, i);
		      break;
		  }
	      }
	      
		      
	      for (int x=0;x<ficheros.length;x++){
		  out.println("<a href=\""+urlsite+"DownloadFile?doc="+ficheros[x]+"\" style=\"text-align: left; color:#084B8A; font-size:13px; font-family:Arial; padding-left: 25px; background-image: url('"+urlsite+"Imagenes/"+ficheros[x].substring(ficheros[x].indexOf(".")+1,ficheros[x].length())+".png'); border:none; width: 30px; height: 30px; background-repeat: no-repeat;\">"+ficheros[x]+"</a><p><p>");
	  
	      }
	      
	      
	}
	
	public void mostrarArchivos(){
	    
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	


}

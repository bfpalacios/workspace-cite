package pe.gob.produce.produccion.controlador;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadFile
 */
@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    
	    String doc = request.getParameter("doc");
	    
	    String url=Constantes.url+doc;
	    
	    txt(doc,url,response);
//            if (doc.endsWith(".txt") || doc.endsWith(".TXT")){
//        	txt(doc,url,response);
//            }else if (doc.endsWith(".pfx") || doc.endsWith(".pfx")){
//        	excel(doc,url,response);
//            }else if (doc.endsWith(".doc") || doc.endsWith(".DOC")){
//        	excel(doc,url,response);
//            }else if (doc.endsWith(".docm") || doc.endsWith(".DOCM")){
//        	excel(doc,url,response);
//            }else if (doc.endsWith(".xlsx") || doc.endsWith(".XLSX")){
//        	excel(doc,url,response);
//            }else if(doc.indexOf(".")==-1){
//        	
//            }
	    
	    
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public void txt(String nombre,String ruta,HttpServletResponse response){
	        
	        try{
	            
	            FileInputStream archivo = new FileInputStream(ruta);
	            System.out.println(ruta);
	   
	            int longitud = archivo.available();
	            byte[] datos = new byte[longitud];
	            archivo.read(datos);
	            archivo.close();
	            response.setContentType("application/octet-stream");
	            response.setHeader("Content-Disposition","attachment;filename="+nombre) ;
	            ServletOutputStream ouputStream = response.getOutputStream();
	            ouputStream.write(datos);
	            ouputStream.flush();
	            ouputStream.close();
	   	 
	        }catch(Exception e){
	            
	        }
	         
	    
	}
	
	public void excel(String nombre,String ruta,HttpServletResponse response){
	        
	        try{
	            
	            FileInputStream archivo = new FileInputStream(ruta);
	   
	            int longitud = archivo.available();
	            byte[] datos = new byte[longitud];
	            archivo.read(datos);
	            archivo.close();
	            response.setContentType("application/octet-stream");
	            response.setHeader("Content-Disposition","attachment;filename="+nombre) ;
	            ServletOutputStream ouputStream = response.getOutputStream();
	            ouputStream.write(datos);
	            ouputStream.flush();
	            ouputStream.close();
	   	 
	        }catch(Exception e){
	            
	        }
	              
	    
	}
	
	public void pdf(String nombre,String ruta,HttpServletResponse response){
	        
	        try{
	            
	            FileInputStream archivo = new FileInputStream(ruta);
	   
	            int longitud = archivo.available();
	            byte[] datos = new byte[longitud];
	            archivo.read(datos);
	            archivo.close();
	            response.setContentType("application/octet-stream");
	            response.setHeader("Content-Disposition","attachment;filename="+nombre) ;
	            ServletOutputStream ouputStream = response.getOutputStream();
	            ouputStream.write(datos);
	            ouputStream.flush();
	            ouputStream.close();
	   	 
	        }catch(Exception e){
	            
	        }
	         
	    
	}
	public void word(String nombre,String ruta, HttpServletResponse response){
	        
	        try{
	            
	            FileInputStream archivo = new FileInputStream(ruta);
	   
	            int longitud = archivo.available();
	            byte[] datos = new byte[longitud];
	            archivo.read(datos);
	            archivo.close();
	            response.setContentType("application/octet-stream");
	            response.setHeader("Content-Disposition","attachment;filename="+nombre) ;
	            ServletOutputStream ouputStream = response.getOutputStream();
	            ouputStream.write(datos);
	            ouputStream.flush();
	            ouputStream.close();
	   	 
	        }catch(Exception e){
	            
	        }
	         
	}


}

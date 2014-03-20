package RasgoContenido;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import IdPalabra.idPalabra;

public class rasgocontenido {
	//Atributos
	String _rasgo;

	//Funciones
	public rasgocontenido(){_rasgo="";}
	
	public rasgocontenido(String rasgo){_rasgo=rasgo;}
	
	public void setRasgo(String rasgo){_rasgo=rasgo;}
	
	public String getRasgo(){return _rasgo;}
	
	public boolean añadirRasgo( Connection conn){
		boolean añadir=false;
		if(consultarUnRasgo(conn)==-1){
			try { 
	           
	            Statement st = conn.createStatement(); 
	            st.executeUpdate("INSERT INTO RASGOCONTENIDO (RasgoContenido) " + 
	                "VALUES ('"+_rasgo+"')"); 
	            añadir=true;
	            System.out.println("Se ha añadido el Rasgo a la base de datos");
	            
	            	            
	        } catch (Exception e) { 
	            System.err.println("Got an exception! "); 
	            System.err.println(e.getMessage()); 
	        } 		
		}
		else
			System.out.println("el rasgo ya existe, no se puede añadir");
		
		return añadir;
	}
	
	public boolean eliminarRasgo(Connection conn) throws SQLException{
		boolean eliminado = false;
		if(consultarUnRasgo(conn)!=-1){
			try { 
	            Statement st = conn.createStatement(); 
	            st.executeUpdate( "DELETE FROM RASGOCONTENIDO " +
	                    "WHERE RasgoContenido =\""+_rasgo+"\"");
	            _rasgo="";
	            System.out.println("Se ha eliminado el rasgo de contenido de la base de datos");
	            eliminado = true;
	        } catch (Exception e) { 
	            System.err.println("Got an exception! "); 
	            System.err.println(e.getMessage()); 
	        } 
		}
		else
			System.out.println("El rasgo de contenido no se ha podido eliminar de la bbdd porque no existe");
		return eliminado;
	}

	public ArrayList<idPalabra> consultarTodosRasgos(Connection conn) {
		ArrayList<idPalabra> misRasgos=new ArrayList<idPalabra>();
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			rs = st.executeQuery("SELECT * FROM RASGOCONTENIDO");
	        while ( rs.next() ) {
	            String palabra = rs.getString("RasgoContenido");
	            String id = rs.getString("idRasgoContenido");
	            misRasgos.add(new idPalabra(Integer.parseInt(id),palabra));
	            System.out.println(palabra+"--"+id);
	        }
		} catch (Exception e) { 
          System.err.println("Got an exception! "); 
          System.err.println(e.getMessage()); 
      } 
        return misRasgos;
	}
	
	public int consultarUnRasgo(Connection conn){
		//boolean existe=false;
		int existe=-1;
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			
			rs = st.executeQuery("SELECT idRasgoContenido FROM RASGOCONTENIDO WHERE RasgoContenido='"+_rasgo+"'");
			if(rs.next())
				existe=rs.getInt(1);
		} catch (Exception e) { 
	          System.err.println("Got an exception! "); 
	          System.err.println(e.getMessage()); 
	      } 
      return existe;
	}

	public void introducirRasgo() throws IOException{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader (isr);
		String miRasgo="";
		System.out.println("Introduzca rasgo a añadir o borrar:");		
		miRasgo=br.readLine();
		_rasgo=miRasgo;

	}
	
	public ArrayList<String> consultaRasgosCat(Connection conn, int cat){
		Statement st;
        ArrayList<String> misRasgos = new ArrayList<String>();
		try {
			st = conn.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT RasgoContenido FROM (SELECT DISTINCT LEMARASGO.idRasgocontenido " +
						"FROM LEMARASGO, (SELECT idLema FROM CATEGORIASLEMAS WHERE idCategoria ='" + cat +"') AS sub2 "+
						"WHERE LEMARASGO.idLema = sub2.idLema)AS sub1, RASGOCONTENIDO "+
						"WHERE RASGOCONTENIDO.idRasgoContenido = sub1.idRasgocontenido");
			
			while ( rs.next() ) {
		            String palabra = rs.getString(1);
					misRasgos.add(palabra);
		       }
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return misRasgos;
	}
	
	public String consultarRasgoPorId(int idRasgo,Connection conn) throws SQLException{
		
		String existe="";
		Statement st = conn.createStatement(); 
		ResultSet rs;
		
		rs = st.executeQuery("SELECT RasgoContenido FROM RASGOCONTENIDO WHERE idRasgoContenido='"+idRasgo+"'");
		if(rs.next())
			existe=rs.getString(1);
      return existe;
	}
	
	public boolean modificarRasgoNombre(String nuevoRasgo, Connection conn) throws SQLException{
		boolean modificar=false;
		if(consultarUnRasgo(conn)!=-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("UPDATE RASGOCONTENIDO SET RasgoContenido='"+nuevoRasgo+"' WHERE RasgoContenido ='"+_rasgo+"'" );
				modificar=true;
				System.out.println("Se ha modificado el rasgo de contenido");
			} catch (Exception e){
				System.err.println(e.getMessage());
			}
		}
		else
			System.out.println("No se ha podido modificar el rasgo de contenido porque no existe en la bbdd");
		
		return modificar;
		
	}
	
}

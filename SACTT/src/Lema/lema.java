package Lema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

import IdPalabra.idPalabra;

public class lema {
	//Atributos
	String _lema;
	
	//Funciones
	public lema(){_lema="";}
	
	public lema(String lema){_lema=lema;}
	
	public void setLema(String Lema){_lema=Lema;}
	
	public String getLema(){return _lema;}
	
	public boolean añadirLema( Connection conn){
		boolean añadir=false;
		if(consultarUnLemaNombre(conn)==-1){
			try { 
	          
	            Statement st = conn.createStatement(); 
	            st.executeUpdate("INSERT INTO LEMA (lemaPalabra) " + 
	                "VALUES ('"+_lema+"')"); 
	            añadir=true;
	            System.out.println("Se ha añadido el lema a la base de datos");
	            
	            
	        } catch (Exception e) { 
	            System.err.println("Got an exception! "); 
	            System.err.println(e.getMessage()); 
	        } 	
		}
		else
			System.out.println("No se ha podido añadir el lema a la bbdd porque ya existe");
		return añadir;
	}
	
	public boolean eliminarLema( Connection conn) throws SQLException{
		boolean eliminado = false;
		if(consultarUnLema(conn)!=-1){
			try { 
				Statement st = conn.createStatement(); 
				st.executeUpdate( "DELETE FROM LEMA " +
						"WHERE lemaPalabra =\""+_lema+"\"");

				System.out.println("Se ha eliminado el lema de la base de datos");
				eliminado = true;

			} catch (Exception e) { 
				System.err.println("Got an exception! "); 
				System.err.println(e.getMessage()); 
			} 	
		}
		else
			System.out.println("El lema no se ha podido eliminar de la bbdd porque no existe");
		
		return eliminado;
	}
	
	public  ArrayList<idPalabra> devolverLemas(Connection conn){
		ArrayList<idPalabra> misAreas=new ArrayList<idPalabra>();
		try{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM LEMA");
			while ( res.next() ) {
				String palabra = res.getString("lemaPalabra");
				int id = res.getInt("idLema");
				misAreas.add(new idPalabra(id,palabra));
				System.out.println(palabra+"--"+id);
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
		}

		return misAreas;
	}
	public static ArrayList<idPalabra> consultarTodosLemas(Connection conn){
		ArrayList<idPalabra> misLemas=new ArrayList<idPalabra>();
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			rs = st.executeQuery("SELECT * FROM LEMA");
	        while ( rs.next() ) {
	            String palabra = rs.getString("lemaPalabra");
	            String id = rs.getString("idLema");
	            misLemas.add(new idPalabra(Integer.parseInt(id),palabra));
	            System.out.println(palabra+" -- "+id);
	        }

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		} 	
        return misLemas;
	}
	
	public String consultarUnLemaPorId(int id, Connection conn) {
		String miLema="";
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			
			rs = st.executeQuery("SELECT lemaPalabra FROM LEMA WHERE idLema='"+id+"'");
			if(rs.next())
				miLema=rs.getString(1);
		}

		catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		} 	
        return miLema;
	}
	
	public int consultarUnLema( Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		
		rs = st.executeQuery("SELECT idLema FROM LEMA WHERE lemaPalabra='"+_lema+"'");
		if(rs.next())
			existe=rs.getInt(1);
        return existe;
	}
	
	public int consultarUnLemaNombre(Connection conn){
		int existe=-1;
		
		try {
			Statement st;
			st = conn.createStatement();
			ResultSet rs;			
			rs = st.executeQuery("SELECT * FROM LEMA WHERE lemaPalabra='"+_lema+"'");
			if(rs.next())
				existe=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		return existe;
	}
	
	public boolean modificarLemaNombre(String nuevoLema, Connection conn) throws SQLException{
		boolean modificar=false;
		if(consultarUnLemaNombre(conn)!=-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("UPDATE LEMA SET lemaPalabra='"+nuevoLema+"' WHERE lemaPalabra ='"+_lema+"'" );
				modificar=true;
				System.out.println("Se ha modificado el lema");
			} catch (Exception e){
				System.err.println(e.getMessage());
			}
		}
		else
			System.out.println("No se ha podido modificar el lema porque no existe en la bbdd");
		
		return modificar;
		
	}
	
	/*public ArrayList<idPalabra> consultarUnLema( Connection conn) throws SQLException{
		int existe=-1;
		ArrayList<idPalabra> lemas = new ArrayList<idPalabra>();
		Statement st = conn.createStatement(); 
		ResultSet rs;
		
		rs = st.executeQuery("SELECT * FROM LEMA WHERE lemaPalabra='"+_lema+"'");
		if(rs.next())
			existe=rs.getInt(1);
		while ( rs.next() ) {
			String lem = rs.getString("lemaPalabra");
			String id = rs.getString("idLema");
			lemas.add(new idPalabra(Integer.parseInt(id),lem));
			
		}

        return lemas;
	}*/
	
	public void introducirLema() throws IOException{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader (isr);
		System.out.println("Introduzca lema a añadir o borrar:");		
		_lema=br.readLine();
		
	}
	
}

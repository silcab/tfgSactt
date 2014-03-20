package LemaRasgo;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import IdPalabra.idPalabra;
import Lema.lema;
import RasgoContenido.rasgocontenido;

public class lemaRasgo {
	//Atributos
	lema _lema;
	rasgocontenido _rasgo;
	
	
	//Funciones
	public lemaRasgo(){
		_lema=new lema();
		_rasgo=new rasgocontenido();
	}
	public lemaRasgo (lema milema,  rasgocontenido mirasgo){
		
		_lema=milema;
		_rasgo=mirasgo;
	}
	public void setRasgo(rasgocontenido rasgo){
		_rasgo=rasgo;
	}
	
	public void setLema(lema lem){
		_lema=lem;
		
	}
	
	/*Añadir o Eliminar*/
	public boolean añadirLemaRasgo(Connection conn,String lema, String rasgo){
		boolean añadir=false;
		//ArrayList<idPalabra> misLemas;
		try{
			lema milema=new lema(lema);
			int idLema=milema.consultarUnLema(conn);
			rasgocontenido mirasgo =new rasgocontenido(rasgo);
			int idRasgo =mirasgo.consultarUnRasgo(conn);
			//_lema.introducirLema();
			//_rasgo.introducirRasgo();
			//Si el rasgo existe, no lo añado
			//if(_rasgo.consultarUnRasgo(conn)==-1)
				//_rasgo.añadirRasgo(conn);
			//_lema.añadirLema(conn);
			//ATENCION A LOS LEMAS IGUALES!!!!
			//idRasgo=_rasgo.consultarUnRasgo(conn);
			//misLemas=_lema.consultarTodosLemas(conn);
			//System.out.println(idLema+"--"+_lema.getLema());
			
			Statement st = conn.createStatement();	
			st.executeUpdate("INSERT INTO LEMARASGO (idLema, idRasgoContenido) VALUES ('" + idLema + "'" +"," +"'" + idRasgo + "')");
			añadir=true;
			System.out.println("Se ha añadido la relación entre el rasgo de contenido y el lema a la base de datos");
		} catch (Exception e){
	    	System.err.println(e.getMessage());
	    }
		return añadir;
	}
	
	public  boolean eliminarLemaRasgo(int idRasgo, int idLema,Connection conn) throws IOException, SQLException{
		//_lema.introducirLema();
		//_rasgo.introducirRasgo();
		//int idRasgo = _rasgo.consultarUnRasgo(conn);
		//int idLema = _lema.consultarUnLema(conn);
		boolean eliminado = false;
		if ((idRasgo != -1)&&(idLema != -1)){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("DELETE FROM LEMARASGO " + " WHERE (idLema, idRasgoContenido )=" + "('" + idLema + "'" +"," +"'" + idRasgo + "')");
				System.out.println("Se ha eliminado la relación entre el lema y el rasgo de contenido");
				eliminado = true;
		    } catch (Exception e){
		    	System.err.println(e.getMessage());
		    }
		}
		else
			System.out.println("No se ha podido eliminar la relación entre ese rasgo de contenido y ese lema");
		
		return eliminado;
	}
	
	public  ArrayList<Integer> devolverRasgosDeLema(int idLema,Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		ArrayList<Integer> rasgosLema=new ArrayList<Integer>();
		try { 
			rs = st.executeQuery("SELECT idRasgoContenido FROM LEMARASGO WHERE idLema='"+idLema+"'");
			while(rs.next()){
				existe=rs.getInt(1);
				rasgosLema.add(existe);
				System.out.println("Rasgo "+existe);
			}

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		return rasgosLema; 		
	}
	
	public  ArrayList<Integer> devolverLemasDeRasgo(int idRasgo,Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		ArrayList<Integer> lemasRasgo=new ArrayList<Integer>();
		try { 
			rs = st.executeQuery("SELECT idLema FROM LEMARASGO WHERE idRasgoContenido='"+idRasgo+"'");
			while(rs.next()){
				existe=rs.getInt(1);
				lemasRasgo.add(existe);
				System.out.println("Lema "+existe);
			}

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		return lemasRasgo; 		
	}
	
	//FALTA DEVOLVER EL RESULT DE LO ENCONTRADO
	public void devolverTodosLemaRasgo(Connection conn){
		
		try{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM LEMARASGO");
	    } catch (Exception e){
	    	System.err.println(e.getMessage());
	    }
	}
	


}
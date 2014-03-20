package Area;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

import IdPalabra.idPalabra;

public class area {
	//Atributos
	String _area;

	//Funciones
	public area(){
		_area="";
	}
	public area(String area){_area=area;}
	public void setArea(String area){
		_area=area;
	}
	public String getArea(){return _area;}

	public boolean añadirArea(Connection conn) throws SQLException{
		boolean añadir=false;
		if(consultarUnAreaNombre(conn)==-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("INSERT INTO AREA (nombreArea) VALUES ('" + _area + "')");
				añadir=true;
				System.out.println("Se ha añadido el area");
			} catch (Exception e){
				System.err.println(e.getMessage());
			}
		}
		else
			System.out.println("No se ha podido añadir el area a la bbdd porque ya existe");
		
		return añadir;
		
	}

	public boolean eliminarArea(Connection conn) throws SQLException{
		boolean eliminado = false;
		if(consultarUnAreaNombre(conn)!=-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("DELETE FROM AREA " + " WHERE nombreArea =\"" + _area + "\"");
				_area="";
				System.out.println("Se ha eliminado el area");
				eliminado = true;
			} catch (Exception e){
				System.err.println(e.getMessage());
			}
		}
		else
			System.out.println("El area no se ha podido eliminar porque no existe en la bbdd");
		
		return eliminado;

	}

	public static ArrayList<idPalabra> devolverAreas(Connection conn){
		ArrayList<idPalabra> misAreas=new ArrayList<idPalabra>();
		try{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM AREA");
			while ( res.next() ) {
				String palabra = res.getString("nombreArea");
				String id = res.getString("idArea");
				misAreas.add(new idPalabra(Integer.parseInt(id),palabra));
				//System.out.println(palabra+"--"+id);
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
		}

		return misAreas;
	}


	public int consultarUnArea(Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try{
			rs = st.executeQuery("SELECT idArea FROM AREA WHERE nombreArea='"+_area+"'");
			if(rs.next())
				existe=rs.getInt(1);
		} catch (Exception e){

			System.err.println(e.getMessage());
		}
		return existe;
	}

	public int consultarUnAreaNombre(Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try{
			rs = st.executeQuery("SELECT * FROM AREA WHERE nombreArea='"+_area+"'");
			if(rs.next()){
				existe=rs.getInt(1);
			}
		//	System.out.println(existe);

		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		return existe;
	}
	
	public boolean modificarAreaNombre(String nuevoArea, Connection conn) throws SQLException{
		boolean modificar=false;
		if(consultarUnAreaNombre(conn)!=-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("UPDATE AREA SET nombreArea='"+nuevoArea+"' WHERE nombreArea ='"+_area+"'" );
				modificar=true;
				System.out.println("Se ha modificado el area");
			} catch (Exception e){
				System.err.println(e.getMessage());
			}
		}
		else
			System.out.println("No se ha podido modificar el área porque no existe en la bbdd");
		
		return modificar;
		
	}


	public void pedirArea(){

		System.out.println("Introduzca el nombre del área:");
		Scanner nombreArea = new Scanner(System.in);
		_area = nombreArea.nextLine();
	}

}

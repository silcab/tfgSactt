package Categoria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

import Area.area;
import IdPalabra.idPalabra;

public class categoria {
	//Atributos
	String _categoria;

	//Funciones
	public categoria(){		
		_categoria="";
	}
	public categoria(String cat){		
		_categoria=cat;
	}

	public void setCategoria(String categoria){
		_categoria=categoria;		
	}

	public String getCategoria(){return _categoria;}

	public boolean añadirCategoria(Connection conn,String miAreaElegida) throws SQLException{	
		boolean añadir=false;
		area miarea=new area();
		miarea.setArea(miAreaElegida);
		//miarea.pedirArea();
		int miArea=miarea.consultarUnArea(conn);
			if(consultarUnaCategoriaConArea(miArea,conn)==-1){
				try { 

					Statement st = conn.createStatement(); 
					st.executeUpdate("INSERT INTO CATEGORIA (categoriaPalabra,idA)" +  "VALUES ('"+_categoria+"'"+ ", '"+miArea +"')" );
					añadir=true;
					//System.out.println("Se ha añadido la categoria a la base de datos");

				} catch (Exception e) { 
					System.err.println("Got an exception! "); 
					System.err.println(e.getMessage()); 
				} 
			}
			//else
				//System.out.println("Ya existe una categoría con ese nombre relacionada con el área elegida");
		
		
		
		return añadir;
	}

	public boolean eliminarCategoria( Connection conn, area miarea) throws SQLException{
		boolean eliminado = false;
		//area miarea=new area();
		//miarea.pedirArea();
		int miAreaElegida=miarea.consultarUnArea(conn);
		if(miAreaElegida!=-1){
			if(consultarUnaCategoriaConArea(miAreaElegida,conn)!=-1){
				try { 
					Statement st = conn.createStatement(); 
					st.executeUpdate( "DELETE FROM CATEGORIA " +
							"WHERE categoriaPalabra =\""+_categoria+"\" AND idA =\""+miAreaElegida+"\"");
					
					System.out.println("Se ha eliminado la categoria de la base de datos");
					_categoria="";
					eliminado=true;

				} catch (Exception e) { 
					System.err.println("Got an exception! "); 
					System.err.println(e.getMessage()); 
				} 	
			}
			else
				System.out.println("La categoria no se ha podido eliminar porque no existe en el area elegida");
		}
		else
			System.out.println("El area elegida no existe en la bbdd");
	
		return eliminado;
	}

	public  ArrayList<idPalabra> consultarTodasCategorias(Connection conn){
		ArrayList<idPalabra> misCategorias=new ArrayList<idPalabra>();
		try{
				Statement st = conn.createStatement(); 
				ResultSet rs;
				rs = st.executeQuery("SELECT * FROM CATEGORIA");
				while ( rs.next() ) {
					String palabra = rs.getString("categoriaPalabra");
					String id = rs.getString("idCategoria");
					misCategorias.add(new idPalabra(Integer.parseInt(id),palabra));
					//System.out.println(palabra+"--"+id);
				}
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
		return misCategorias;
	}

	public int consultarUnaCategoriaConArea(int idArea,Connection conn) {
		int existe=-1;
		
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			rs = st.executeQuery("SELECT idCategoria,categoriaPalabra,idA FROM CATEGORIA WHERE idA='"+idArea+"' AND categoriaPalabra='"+_categoria+"'");
			while(rs.next()){
				existe=rs.getInt(3);
				//System.out.println(existe);
			}

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		return existe; 		
	}
	
	public ArrayList<idPalabra> consultarCategoriasConArea(String nomArea, Connection conn){
		int existe=-1;
		
		ArrayList<idPalabra> misCategorias=new ArrayList<idPalabra>();
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			String prueba = "SELECT idCategoria,categoriaPalabra,idA FROM CATEGORIA INNER JOIN AREA ON (AREA.idArea = CATEGORIA.idA) WHERE AREA.nombreArea = '" + nomArea + "')";
			rs = st.executeQuery("SELECT idCategoria,categoriaPalabra,idA FROM CATEGORIA INNER JOIN AREA ON (AREA.idArea = CATEGORIA.idA) WHERE AREA.nombreArea = '" + nomArea + "'");
			while ( rs.next() ) {
				String palabra = rs.getString("categoriaPalabra");
				String id = rs.getString("idCategoria");
				misCategorias.add(new idPalabra(Integer.parseInt(id),palabra));
				System.out.println(palabra+"--"+id);
			}

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		
		
		return misCategorias; 		
	}

	public int consultarUnaCategoria( Connection conn){
		int existe=-1;
		
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			rs = st.executeQuery("SELECT idCategoria FROM CATEGORIA WHERE categoriaPalabra='"+_categoria+"'");
			while(rs.next())
				existe=rs.getInt(1);

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		return existe; 		
	}


	public int consultarUnaCategoriaArea( Connection conn, String area, String cat) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try { 
			rs = st.executeQuery("SELECT CATEGORIA.idCategoria FROM (SELECT idArea FROM AREA WHERE nombreArea = '" +
						area + "') as areaSel INNER JOIN CATEGORIA WHERE ((CATEGORIA.idA = areaSel.idArea) " +
						"&& (CATEGORIA.categoriaPalabra = '" + cat +"'))");
			while(rs.next())
				existe=rs.getInt(1);

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		return existe; 		
	}
	
	public String consultarCategoriaPorId( int idCat, Connection conn) throws SQLException{
		String existe="";
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try { 
			rs = st.executeQuery("SELECT categoriaPalabra FROM CATEGORIA WHERE idCategoria='"+idCat+"'");
			while(rs.next())
				existe=rs.getString(1);

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		return existe; 		
	}
	
	public boolean modificarCategoriaNombre(String nuevaCategoria, Connection conn, area miarea) throws SQLException{
		boolean modificar = false;
		int miAreaElegida=miarea.consultarUnArea(conn);
		if(miAreaElegida!=-1){
			if(consultarUnaCategoriaConArea(miAreaElegida,conn)!=-1){
				try{
					Statement st = conn.createStatement();
					st.executeUpdate("UPDATE CATEGORIA SET categoriaPalabra='"+nuevaCategoria+"' WHERE categoriaPalabra ='"+_categoria+"' AND idA =\""+miAreaElegida+"\"" );
					modificar=true;
					System.out.println("Se ha modificado la categoría");
				} catch (Exception e){
					System.err.println(e.getMessage());
				}
			}
		}
			else
				System.out.println("No se ha podido modificar la categoría porque no existe en la bbdd");

			return modificar;

		}
	
	
	
	
	
	/*public void introducirCategoria() throws IOException{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader (isr);
		String miCategoria="";
		System.out.println("Introduzca categoria a añadir o borrar:");		
		miCategoria=br.readLine();
		_categoria=miCategoria;

	}*/


	
}
package categoriasLemas;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import Categoria.categoria;
import IdPalabra.idPalabra;
import Lema.lema;
import RasgoContenido.rasgocontenido;

public class categoriasLemas {
	//Atributos
	categoria _categoria;
	lema _lema;

	//Funciones
	public categoriasLemas(){
		_categoria=new categoria();
		_lema=new lema();
	}
	
	public void setCategoria(categoria categ){
		_categoria=categ;
	}

	public void setLema(lema lem){
		_lema=lem;
	}



	/*Añadir o Eliminar*/
	public boolean añadirLemaCategoria(Connection conn,String categoria, String lema){
		boolean añadir=false;
		ArrayList<idPalabra> misLemas;
		try{
			
			lema milema=new lema(lema);
			int idLema=milema.consultarUnLema(conn);
			categoria micategoria =new categoria(categoria);
			int idCategoria =micategoria.consultarUnaCategoria(conn);
			//_lema.introducirLema();
			//_categoria.introducirCategoria();

			//Si la categoria existe, la consulto, y si no la añado
			//if(_categoria.consultarUnaCategoria(conn)==-1)
				//_categoria.añadirCategoria(conn);
				
			//ATENCION A LOS LEMAS IGUALES!!!!
			//cONSULTAR TODOS LOS LEMAS
			//GENERAR UN ARRAY CON EL NOMBRE DE ESE LEMA Y SU ID
		//	idCategoria=_categoria.consultarUnaCategoria(conn);
		//	if(idCategoria != -1){
			//	_lema.añadirLema(conn);
			//	System.out.println(idCategoria);
			//	misLemas=_lema.consultarTodosLemas(conn);
				//System.out.println(idLema+"--"+_lema.getLema());

				Statement st = conn.createStatement();	
				st.executeUpdate("INSERT INTO CATEGORIASLEMAS (idCategoria, idLema) VALUES ('" + idCategoria + "'" +"," +"'" + idLema + "')");
				System.out.println("Se ha añadido la relación entre la categoria y el lema a la base de datos");
				añadir=true;
			//}
			//else
				//System.out.println("No se ha podido establecer la relación entre la categoría y el lema");
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		return añadir;
	}

	public static boolean eliminarLemaCategoria(int idCat, int idLema, Connection conn) throws IOException, SQLException{

		boolean eliminado = false;
		System.out.println("idCat "+idCat+" idLema "+idLema);
		if ((idCat != -1)&&(idLema != -1)){
			try{
				Statement st = conn.createStatement();	
				st.executeUpdate("DELETE FROM CATEGORIASLEMAS " + " WHERE (idCategoria, idLema )=" + "('" + idCat + "'" +"," +"'" + idLema + "')");
				System.out.println("Se ha eliminado la relación entre el lema y la categoría");
				eliminado = true;
			} catch (Exception e){
				System.err.println(e.getMessage());
			}
		}
		else
			System.out.println("No se ha podido eliminar la relación entre esa categoría y ese lema");
		
		return eliminado;
	}


	//Falta devolver toda la Select
	public void devolverTodosLemaCategoria(Connection conn){

		try{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM CATEGORIASLEMAS");
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	
	public ArrayList<Integer> consultarLemasdeunaCategoria(int categoria, Connection conn){
		int existe=-1;
		
		ArrayList<Integer> misLemas=new ArrayList<Integer>();
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			//String prueba = "SELECT idCategoria,categoriaPalabra,idA FROM CATEGORIA INNER JOIN AREA ON (AREA.idArea = CATEGORIA.idA) WHERE AREA.nombreArea = '" + nomArea + "')";
			rs = st.executeQuery("SELECT idLema FROM CATEGORIASLEMAS WHERE CATEGORIASLEMAS.idCategoria = '" + categoria + "'");
			while ( rs.next() ) {
				String id = rs.getString("idLema");
				misLemas.add(Integer.parseInt(id));

			}

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		
		
		return misLemas; 		
	}
	
	public static ArrayList<Integer> devolverLemasDeCategoria(int idCat,Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		ArrayList<Integer> lemasCat=new ArrayList<Integer>();
		try { 
			rs = st.executeQuery("SELECT idLema FROM CATEGORIASLEMAS WHERE idCategoria='"+idCat+"'");
			while(rs.next()){
				existe=rs.getInt(1);
				lemasCat.add(existe);
				System.out.println("Lema "+existe);
			}

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		return lemasCat; 		
	}
	
	public static ArrayList<Integer> devolverCategoriasDeLema(int idLema,Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		ArrayList<Integer> catsLema=new ArrayList<Integer>();
		try { 
			rs = st.executeQuery("SELECT idCategoria FROM CATEGORIASLEMAS WHERE idLema='"+idLema+"'");
			while(rs.next()){
				existe=rs.getInt(1);
				catsLema.add(existe);
				System.out.println("Lema "+existe);
			}

		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
		}
		return catsLema; 		
	}
	


}
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



	/*A�adir o Eliminar*/
	public boolean a�adirLemaCategoria(Connection conn,String categoria, String lema){
		boolean a�adir=false;
		ArrayList<idPalabra> misLemas;
		try{
			
			lema milema=new lema(lema);
			int idLema=milema.consultarUnLema(conn);
			categoria micategoria =new categoria(categoria);
			int idCategoria =micategoria.consultarUnaCategoria(conn);
			//_lema.introducirLema();
			//_categoria.introducirCategoria();

			//Si la categoria existe, la consulto, y si no la a�ado
			//if(_categoria.consultarUnaCategoria(conn)==-1)
				//_categoria.a�adirCategoria(conn);
				
			//ATENCION A LOS LEMAS IGUALES!!!!
			//cONSULTAR TODOS LOS LEMAS
			//GENERAR UN ARRAY CON EL NOMBRE DE ESE LEMA Y SU ID
		//	idCategoria=_categoria.consultarUnaCategoria(conn);
		//	if(idCategoria != -1){
			//	_lema.a�adirLema(conn);
			//	System.out.println(idCategoria);
			//	misLemas=_lema.consultarTodosLemas(conn);
				//System.out.println(idLema+"--"+_lema.getLema());

				Statement st = conn.createStatement();	
				st.executeUpdate("INSERT INTO CATEGORIASLEMAS (idCategoria, idLema) VALUES ('" + idCategoria + "'" +"," +"'" + idLema + "')");
				System.out.println("Se ha a�adido la relaci�n entre la categoria y el lema a la base de datos");
				a�adir=true;
			//}
			//else
				//System.out.println("No se ha podido establecer la relaci�n entre la categor�a y el lema");
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		return a�adir;
	}

	public static boolean eliminarLemaCategoria(int idCat, int idLema, Connection conn) throws IOException, SQLException{

		boolean eliminado = false;
		System.out.println("idCat "+idCat+" idLema "+idLema);
		if ((idCat != -1)&&(idLema != -1)){
			try{
				Statement st = conn.createStatement();	
				st.executeUpdate("DELETE FROM CATEGORIASLEMAS " + " WHERE (idCategoria, idLema )=" + "('" + idCat + "'" +"," +"'" + idLema + "')");
				System.out.println("Se ha eliminado la relaci�n entre el lema y la categor�a");
				eliminado = true;
			} catch (Exception e){
				System.err.println(e.getMessage());
			}
		}
		else
			System.out.println("No se ha podido eliminar la relaci�n entre esa categor�a y ese lema");
		
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
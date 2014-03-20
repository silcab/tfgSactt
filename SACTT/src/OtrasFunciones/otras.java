package OtrasFunciones;


import java.sql.SQLException;
import Area.*;
import Categoria.*;
import ConexionBBDD.conexion;
import Lema.*;
import RasgoContenido.rasgocontenido;
public class otras {
	
	public void busquedaACiegas(String palabra, conexion conn) throws SQLException{
		area miarea = new area();
		categoria micategoria=new categoria();
		lema miLema=new lema();
		rasgocontenido miRasgo=new rasgocontenido();
		if(miarea.consultarUnArea(conn.getMiConexion())!=-1)
			System.out.println("Es un area");
		else if(micategoria.consultarUnaCategoria(conn.getMiConexion())!=-1)
			System.out.println("Es una categoria");
		else if(miLema.consultarUnLema(conn.getMiConexion())!=1)
			System.out.println("Es un lema");
		else if(miRasgo.consultarUnRasgo(conn.getMiConexion())!=-1)
			System.out.println("Es un Rasgo");
		else
			System.out.println("No existe");
	
	}
	
}

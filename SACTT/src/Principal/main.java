package Principal;
import java.io.IOException;
import java.sql.SQLException;

import categoriasLemas.*;
import Area.area;
import Categoria.categoria;
import ConexionBBDD.*;
import Lema.lema;
import LemaRasgo.lemaRasgo;
import OtrasFunciones.consulta;
import OtrasFunciones.otras;
import RasgoContenido.*;


public class main {

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, SQLException, IOException {
		
		conexion miconexion=new conexion();
		//Iniciar conexion con la base de datos
		miconexion.iniciarBBDD();
		//Creacion de todas las tablas
		//creartablas mistablas=new creartablas(miconexion);
		
		
		//ESTAS PRUEBAS ES MEJOR IR COMENTANDOLAS SEGUN LA ZONA QUE SE NECESITE PROBAR
		
		
		/*
		//PRUEBAS PARA AÑADIR ,ELIMINAR,CONSULTAR LEMA
		lema miLema=new lema();
		miLema.introducirLema();
		//miLema.setLema("Lema2");
		miLema.añadirLema(miconexion.getMiConexion());
		//miLema.consultarTodosLemas( miconexion.getMiConexion());
		miLema.introducirLema();
		miLema.eliminarLema(miconexion.getMiConexion());
		*/
		
		/*
		//PRUEBAS PARA AÑADIR, ELIMINAR,CONSULTAR AREA
		area miarea=new area();
		miarea.pedirArea();
		//miarea.setArea("area1");
		miarea.añadirArea( miconexion.getMiConexion());
		miarea.pedirArea();
		miarea.eliminarArea( miconexion.getMiConexion());
		*/
		
		/*
		//PRUEBAS PARA AÑADIR, ELIMINAR,CONSULTAR RASGO DE CONTENIDO
		rasgocontenido mirasgo=new rasgocontenido();
		mirasgo.introducirRasgo();
		//mirasgo.setRasgo("rasgo1");
		mirasgo.añadirRasgo(miconexion.getMiConexion());
		//mirasgo.consultarTodosRasgos(miconexion.getMiConexion());
		mirasgo.introducirRasgo();
		mirasgo.eliminarRasgo(miconexion.getMiConexion());
		*/
		
		
		/*
		//PRUEBAS LEMA_RASGO
		System.out.println("\n");
		System.out.println("LEMA RASGO");
		lemaRasgo miLemaRasgo = new lemaRasgo();
		miLemaRasgo.añadirLemaRasgo(miconexion.getMiConexion());
		miLemaRasgo.eliminarLemaRasgo(miconexion.getMiConexion());
		*/
		
		/*
		//PRUEBAS CATEGORIA
		categoria micategoria=new categoria();
		micategoria.introducirCategoria();
		micategoria.añadirCategoria(miconexion.getMiConexion());
		micategoria.introducirCategoria();
		micategoria.eliminarCategoria(miconexion.getMiConexion());
		System.out.println("\n");
		*/
		
		
		/*
		//PRUEBAS LEMA_CATEGORIAS
		categoriasLemas miCategoriasLemas=new categoriasLemas();
		System.out.println("Añadir un lema a una categoria:");
		miCategoriasLemas.añadirLemaCategoria(miconexion.getMiConexion());
		miCategoriasLemas.eliminarLemaCategoria(miconexion.getMiConexion());
		*/
		
		//PRUEBA BUSQUEDA A CIEGAS
		/*otras prueba = new otras();
		prueba.busquedaACiegas("lemaNuevo", miconexion);*/
		
		//PRUEBA Consulta
		/*consulta cons = new consulta();
		cons.pedirArea();
		cons.pedirCategoria();
		cons.pedirRasgos();
		cons.rasgosAIndices(miconexion.getMiConexion());
		cons.consultar(miconexion.getMiConexion());*/
		
		miconexion.cerrarBBDD();

	}

}

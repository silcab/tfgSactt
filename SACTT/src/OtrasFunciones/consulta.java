package OtrasFunciones;


import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.Scanner;

import Categoria.categoria;
import RasgoContenido.rasgocontenido;

public class consulta {
	private String Area, Categoria;
	private ArrayList<String> Rasgos;
	private ArrayList<Integer> IndRasgos = new ArrayList<Integer>();
	
	public consulta(ArrayList<String> rasgos, String nomArea, String nomCat ){
		Rasgos = new ArrayList<String>(rasgos);
		Area = nomArea;
		Categoria = nomCat;
	}
	
	
	public ArrayList<String> consultar(Connection conn){
		Statement st;
		String cadenaRasgos = "";
		for (int i=0; i<IndRasgos.size(); i++){
			cadenaRasgos = cadenaRasgos + "idRasgoContenido = " + IndRasgos.get(i);
			if (i < IndRasgos.size()-1){
				cadenaRasgos = cadenaRasgos + " OR ";
			}
		}
		
		ArrayList<String> ret = new ArrayList<String>();
		
		try {
			st = conn.createStatement();
			
			int idcat = this.idCategoria(conn);
			ResultSet res = st.executeQuery("SELECT idLema, COUNT( * ) "
					+ "FROM (SELECT LEMARASGO.idLema, LEMARASGO.idRasgocontenido FROM LEMARASGO, "
					+ "(SELECT idLema FROM CATEGORIASLEMAS WHERE idCategoria =" + idcat 
					+ " ) AS sub2 WHERE LEMARASGO.idLema = sub2.idLema) AS sub1 WHERE ("
					+ cadenaRasgos + ") " + "GROUP BY idLema");
			
			int ind = -1, count = 0;
			ArrayList<Integer> indRes = new ArrayList<Integer>();
			while (res.next()){
				//Buscamos el registro de mayor count y lo añadimos
				if (res.getInt(2) > count){
					ind = res.getInt(1);
					indRes.clear();
					indRes.add(ind);
					count = res.getInt(2);
				}else if(res.getInt(2) == count){
					//Si encontramos otro registro con igual count, lo añadimos tambien
					ind = res.getInt(1);
					indRes.add(ind);
				}
			}
			
			
			
			if (Rasgos.size()>count){
				System.out.println("Ningún resultado para los rasgos introducidos.");
				ret.add("Ningún resultado para los rasgos introducidos.");
			}else{
				System.out.println("El/los lema(s) encontrado(s) son: ");
				for (int i = 0; i<indRes.size(); i++){
					ResultSet lemaRes = st.executeQuery("SELECT lemaPalabra FROM LEMA WHERE ( idLema = " + indRes.get(i) + ")");
					lemaRes.next();
					System.out.println(lemaRes.getString(1));
					ret.add(lemaRes.getString(1));
				}
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}
	
	public void pedirArea(){
		
		System.out.println("Introduzca el nombre del área:");
		Scanner nombreArea = new Scanner(System.in);
		Area = nombreArea.nextLine();
	}
	
	public void pedirCategoria(){
		
		System.out.println("Introduzca el nombre de la categoría:");
		Scanner nombreCat = new Scanner(System.in);
		Categoria = nombreCat.nextLine();
	}
	
	public void pedirRasgos(){
		
		String lista = "";
		System.out.println("Introduzca los rasgos separados por comas:");
		Scanner listaRasgos = new Scanner(System.in);
		lista = listaRasgos.next();
		Rasgos = new ArrayList<String>(Arrays.asList(lista.split(",")));
	}
	
	public void rasgosAIndices(Connection conn){
		for (int i=0; i<Rasgos.size(); i++){
			IndRasgos.add(new rasgocontenido(Rasgos.get(i)).consultarUnRasgo(conn));
		}
	}
	
	private int idCategoria(Connection conn){
		categoria cat = new categoria(Categoria);
		ResultSet rs;
		int idcat = -1;
		try {
			Statement st = conn.createStatement();
			String prueba = "SELECT idCategoria FROM CATEGORIA WHERE ((categoriaPalabra = '" + Categoria + "') AND idA IN (SELECT idArea FROM AREA WHERE (nombreArea ='"  + Area + "') )";
			rs = st.executeQuery("SELECT idCategoria FROM CATEGORIA WHERE ((categoriaPalabra = '" + Categoria + "') AND idA IN (SELECT idArea FROM AREA WHERE (nombreArea ='"  + Area + "') ))");
			rs.next();
			idcat = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idcat;
	}

}

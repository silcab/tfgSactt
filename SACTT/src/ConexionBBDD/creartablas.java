package ConexionBBDD;

import java.sql.*;

public class creartablas {

	//public conexion miConexion;
	 Statement stmt = null;

	public creartablas(conexion miConexion) throws SQLException{
		//miConexion=new conexion();
		
		  System.out.println("Creating table in given database...");
	      stmt = miConexion.getMiConexion().createStatement();
	      
	      String sqlLema = "CREATE TABLE LEMA " +
	    		  	   "(idLema INT NOT NULL AUTO_INCREMENT,"+
	                   "lemaPalabra VARCHAR(255), " +
	                   " PRIMARY KEY ( idLema ))ENGINE=innodb"; 

	      stmt.executeUpdate(sqlLema);
	      
	     String sqlArea = "CREATE TABLE AREA " +
                  "(idArea INT NOT NULL AUTO_INCREMENT,"+
	              "nombreArea VARCHAR(255), " +
                  " PRIMARY KEY ( idArea )) ENGINE=innodb"; 

	      stmt.executeUpdate(sqlArea);
	      
	      String sqlCategoria = "CREATE TABLE CATEGORIA " +
                  "(idCategoria INT NOT NULL AUTO_INCREMENT,"+
	              "categoriaPalabra VARCHAR(255), " +
	              "idA INT NOT NULL, " +
	              " CONSTRAINT fk_idA FOREIGN KEY (idA) REFERENCES AREA(idArea) ON UPDATE CASCADE ON DELETE RESTRICT,"+
                  " PRIMARY KEY ( idCategoria )) ENGINE=innodb"; 

	      stmt.executeUpdate(sqlCategoria);

	      
	      String sqlRasgoContenido = "CREATE TABLE RASGOCONTENIDO " +
	    		  "(idRasgoContenido INT NOT NULL AUTO_INCREMENT,"+
                  "RasgoContenido VARCHAR(255), " +
                  " PRIMARY KEY ( idRasgoContenido ))ENGINE=innodb"; 

	      stmt.executeUpdate(sqlRasgoContenido);
	      
	      String sqlLemaCategoria = "CREATE TABLE CATEGORIASLEMAS " +
                  "(idCategoria int not NULL, " +
                  "idLema int not NULL, " +
                  "PRIMARY KEY ( idCategoria,idLema ),"+
                  "FOREIGN KEY ( idCategoria) REFERENCES CATEGORIA(idCategoria),"+
                  "FOREIGN KEY ( idLema) REFERENCES LEMA(idLema))ENGINE=innodb"; 
	    
	      stmt.executeUpdate(sqlLemaCategoria);
	      
	     String sqlLemaRasgo = "CREATE TABLE LEMARASGO " +
                  "(idLema int not NULL, " +
                  "idRasgoContenido int not NULL, " +
                  "PRIMARY KEY ( idLema,idRasgoContenido ),"+
                  "FOREIGN KEY ( idLema) REFERENCES LEMA(idLema),"+
                  "FOREIGN KEY ( idRasgoContenido) REFERENCES RASGOCONTENIDO(idRasgoContenido))ENGINE=innodb"; 
	    
	      stmt.executeUpdate(sqlLemaRasgo);
	      
	      
	    System.out.println("Tablas creadas correctamente");
		
	}
}

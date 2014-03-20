package ConexionBBDD;

import java.sql.*;

public class conexion
{
	   static String bd ;
	   static String login;
	   static String password;
	   static String url;
	   Connection conn ;
	public conexion(){
		 bd = "sactt";
		 login = "root";
		 password = "";
		 url = "jdbc:mysql://localhost/"+bd;
		 conn=null;
	}
	
	public Connection getMiConexion(){
		
		return conn;
	}
   public void iniciarBBDD() throws InstantiationException, IllegalAccessException{
	   

	      try
	      {
	         Class.forName("com.mysql.jdbc.Driver").newInstance();

	         conn = DriverManager.getConnection(url,login,password);

	         if (conn != null)
	         {
	            System.out.println("Conexión a base de datos "+url+" ... Ok");
	           // conn.close();
	         }
	      }
	      catch(SQLException ex)
	      {
	         System.out.println(ex);
	      }
	      catch(ClassNotFoundException ex)
	      {
	         System.out.println(ex);
	      }
	   
	   
   }
   public void cerrarBBDD() throws SQLException{
	   
	   conn.close();
   }

}
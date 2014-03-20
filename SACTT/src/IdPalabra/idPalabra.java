package IdPalabra;

public class idPalabra {
	//Atributos
	int _id;
	String _palabra;
	
	
	//Funciones
	public idPalabra(){
		_id=0;
		_palabra="";	
	}
	public idPalabra(int id, String palabra){
		_id=id;
		_palabra=palabra;
	}
	public void setIDPalabra(int id, String palabra){
		_id=id;
		_palabra=palabra;
		
	}
	public int getId(){return _id;}
	public String getPalabra(){return _palabra;}
}

//import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
//import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;

import categoriasLemas.categoriasLemas;


import Area.area;
import Categoria.categoria;
import ConexionBBDD.conexion;
import IdPalabra.idPalabra;
import Lema.lema;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;
//import RasgoContenido.rasgocontenido;
import com.mxrck.autocompleter.TextAutoCompleter;

public class FrameAñadirLemaRasgo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnCancelar;
	JButton btnAceptar;
	JComboBox<String> comboBoxLema;
	JComboBox comboBoxCategoria;
	JComboBox comboBoxArea;
	public  SwingAction action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	private JTextField textFieldRasgo;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameAñadirLemaRasgo frame = new FrameAñadirLemaRasgo(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public FrameAñadirLemaRasgo(Connection conn) {
		setTitle("Asociar Lema a un Rasgo");
		_conn=conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAsociarLema = new JLabel("Elegir Lema:");
		lblAsociarLema.setBounds(10, 75, 139, 14);
		contentPane.add(lblAsociarLema);
		
		JLabel lblAsociarRasgoDe = new JLabel("Asociar Rasgo de Contenido:");
		lblAsociarRasgoDe.setBounds(10, 112, 173, 14);
		contentPane.add(lblAsociarRasgoDe);
		
		comboBoxLema = new JComboBox<String>();
		
		comboBoxLema.setBounds(209, 75, 215, 20);
		contentPane.add(comboBoxLema);
		
		 btnAceptar = new JButton("Aceptar");
		 btnAceptar.addActionListener(this.action);
		btnAceptar.setBounds(209, 155, 89, 23);
		contentPane.add(btnAceptar);
		
		 btnCancelar = new JButton("Cancelar");
		 btnCancelar.addActionListener(this.action);
		 btnCancelar.setBounds(335, 155, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblElegirrea = new JLabel("Elegir \u00C1rea:");
		lblElegirrea.setBounds(10, 21, 80, 14);
		contentPane.add(lblElegirrea);
		
		comboBoxCategoria = new JComboBox();
		comboBoxCategoria.setBounds(209, 49, 215, 20);
		contentPane.add(comboBoxCategoria);
		
		
		comboBoxArea = new JComboBox();
		comboBoxArea.setBounds(209, 18, 215, 20);
		contentPane.add(comboBoxArea);
		comboBoxArea.addItem("   ");
		
		textFieldRasgo = new JTextField();
		textFieldRasgo.setBounds(209, 109, 215, 20);
		contentPane.add(textFieldRasgo);
		textFieldRasgo.setColumns(10);
		textFieldRasgo.setEnabled(false);
		cargarComboArea();
		
		cargarTextFieldRasgos();
		ItemListener itemListener = new ItemListener() {
			//int contador=0;
			public void itemStateChanged(ItemEvent itemEvent) {
				//contador++;
				//System.out.println("pasa"+contador);
				//comboBoxCategoria.removeAllItems();
				//System.out.println(comboBoxArea.getSelectedIndex() );
				if (comboBoxArea.getSelectedIndex() >= 0){
					//System.out.println(comboBoxArea.getSelectedItem().toString());
					categoria c = new categoria();
					ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
					
					cats = c.consultarCategoriasConArea(comboBoxArea.getSelectedItem().toString(), miconexion.getMiConexion());
					//System.out.println(cats.size());
					if(cats.size()==0){
						comboBoxLema.removeAllItems();
						comboBoxCategoria.removeAllItems();
						comboBoxCategoria.setEnabled(false);
						comboBoxLema.setEnabled(false);
						textFieldRasgo.setEnabled(false);
						textFieldRasgo.setText(null);
					}
					else{
						System.out.println("pasa");
						comboBoxCategoria.setEnabled(true);
						comboBoxLema.setEnabled(false);
						comboBoxCategoria.removeAllItems();
						textFieldRasgo.setEnabled(false);
						textFieldRasgo.setText(null);
						comboBoxCategoria.addItem("   ");
						for(int i=0; i<cats.size(); i++){
							comboBoxCategoria.addItem(cats.get(i).getPalabra());
							
						}
					}
				}
			}
		};
		

		comboBoxArea.addItemListener(itemListener);
		
		JLabel lblElegirCategora = new JLabel("Elegir Categor\u00EDa:");
		lblElegirCategora.setBounds(10, 46, 96, 14);
		contentPane.add(lblElegirCategora);
		
	
		
		ItemListener itemListener2 = new ItemListener() {
			//int contador=0;
			public void itemStateChanged(ItemEvent itemEvent) {
			//System.out.println(comboBoxCategoria.getSelectedIndex());
			 if (comboBoxCategoria.getSelectedIndex() > 0){
				//System.out.println("pasa");
					categoriasLemas lemaCateg = new categoriasLemas();
					categoria micategoria=new categoria(comboBoxCategoria.getSelectedItem().toString());
					int idCateg=micategoria.consultarUnaCategoria(_conn);
					ArrayList<Integer> lemas = new ArrayList<Integer>();
					lemas = lemaCateg.consultarLemasdeunaCategoria(idCateg, miconexion.getMiConexion());
					
					if(lemas.size()==0){
						comboBoxLema.removeAllItems();
						comboBoxLema.setEnabled(false);
						textFieldRasgo.setEnabled(false);
						textFieldRasgo.setText(null);
					}
					else{
						comboBoxLema.setEnabled(true);
						comboBoxLema.removeAllItems();
						textFieldRasgo.setEnabled(true);
						textFieldRasgo.setText(null);
						lema milema=new lema();
						comboBoxLema.addItem("  ");
						for(int i=0; i<lemas.size(); i++){
							comboBoxLema.addItem(milema.consultarUnLemaPorId(lemas.get(i),_conn).toString());
							
						}
						//cargarTextFieldRasgos();
					}
					
					
					
				}
				
			}
		};
		
	
		comboBoxCategoria.addItemListener(itemListener2);
		
		
		
		
	
	}
	
	   public void cargarTextFieldRasgos(){
		   textFieldRasgo.setText("");
			TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( textFieldRasgo );
			rasgocontenido mirasgo =new rasgocontenido();
			ArrayList<idPalabra> misRasgos=new ArrayList<idPalabra>();
			misRasgos=mirasgo.consultarTodosRasgos(_conn);
			 for(int i=0; i<misRasgos.size(); i++){
				 textAutoAcompleter.addItem(misRasgos.get(i).getPalabra());
		       	 }	
			/*textAutoAcompleter.addItem("segunda prueba");
			textAutoAcompleter.addItem("autoAcompletar");
			textAutoAcompleter.addItem("Java");*/

			//textAutoAcompleter.setMode(-1); // prefijo, viene por defecto
			//textAutoAcompleter.setMode(-1); // infijo
			//textAutoAcompleter.setMode(1); // sufijo
			textAutoAcompleter.setCaseSensitive(true); // Sensible a mayúsculas
			//textAutoAcompleter.setCaseSensitive(false); //No sensible a mayúsculas
			
		   
		   
		   
	   }
	
	public void cargarComboArea(){
	   		 area miArea =new area();
	       	 ArrayList<idPalabra>misAreas=new ArrayList<idPalabra>();
	       	misAreas=area.devolverAreas(_conn);
	       	 for(int i=0; i<misAreas.size(); i++){
	       		comboBoxArea.addItem(misAreas.get(i).getPalabra());
	       	 }	
	       	 
		}
	   

	private class SwingAction extends AbstractAction {
			
		//	private static final  serialVersionUID = 1L;
			public SwingAction() {
				putValue(NAME, "SwingAction");
				putValue(SHORT_DESCRIPTION, "Some short description");
			}


			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean rasgo=false;
				if (e.getSource()==btnAceptar) {
					if(comboBoxLema.getSelectedIndex()==0)
						JOptionPane.showMessageDialog(null,"No se ha seleccionado Lema."); 
					else{       
							lemaRasgo miLemaRasgo =new lemaRasgo();	
							rasgocontenido mirasgo=new rasgocontenido(textFieldRasgo.getText());
							if(mirasgo.añadirRasgo(_conn)==false)
								JOptionPane.showMessageDialog(null,"No se ha podido añadir el Rasgo.");
							
							
						
							if(miLemaRasgo.añadirLemaRasgo(_conn, comboBoxLema.getSelectedItem().toString(), textFieldRasgo.getText())){
								JOptionPane.showMessageDialog(null,"Asociacion Correcta");
								System.exit(0);
							}
							else
								JOptionPane.showMessageDialog(null,"La Asociacion no se ha podido realizar");
							
					         
								
						System.exit(0);
					}
				}
				else if (e.getSource()==btnCancelar) {
		            System.exit(0);
		        }
			}
		}
}

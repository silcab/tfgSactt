import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;

import com.mxrck.autocompleter.TextAutoCompleter;
//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import categoriasLemas.categoriasLemas;


import Area.area;
import Categoria.categoria;
import ConexionBBDD.conexion;
import IdPalabra.idPalabra;
import Lema.lema;

//import org.jdesktop.swing.autocomplete.*;

public class FrameAñadirLemaCategoria extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JComboBox<String> comboBoxCategoria;
	JButton btnAsociar, btnCancelar;
	public  SwingAction action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	private JLabel lblElegirArea;
	private JComboBox<String> comboBoxArea;
	private JTextField textFieldLema;
	private JList list_1;
	//ItemListener itemListener ;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
		
					FrameAñadirLemaCategoria frame = new FrameAñadirLemaCategoria(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public FrameAñadirLemaCategoria(Connection conn) {
	
		setTitle("Asociar Lema a categoria");
		_conn=conn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAsociarLema = new JLabel("Asociar Lema:");
		lblAsociarLema.setBounds(26, 103, 89, 14);
		contentPane.add(lblAsociarLema);
		
		JLabel lblNewLabel = new JLabel("Asociar categoria:");
		lblNewLabel.setBounds(26, 67, 142, 14);
		contentPane.add(lblNewLabel);
		
		comboBoxCategoria = new JComboBox<String>();
		comboBoxCategoria.setBounds(159, 64, 265, 20);
		contentPane.add(comboBoxCategoria);
		
		btnAsociar = new JButton("Asociar");
		btnAsociar.addActionListener(this.action);
		btnAsociar.setBounds(159, 210, 105, 23);
		contentPane.add(btnAsociar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(319, 210, 105, 23);
		contentPane.add(btnCancelar);
		
		lblElegirArea = new JLabel("Elegir Area:");
		lblElegirArea.setBounds(28, 22, 87, 14);
		contentPane.add(lblElegirArea);
		
		
		textFieldLema = new JTextField();
		textFieldLema.setBounds(159, 100, 265, 20);
		contentPane.add(textFieldLema);
		textFieldLema.setColumns(10);
		textFieldLema.setEnabled(false);
		
		comboBoxArea = new JComboBox();
		comboBoxArea.setBounds(159, 19, 265, 20);
		contentPane.add(comboBoxArea);
		comboBoxArea.addItem("  ");
		cargarComboArea();
		cargarTextFieldLemas();
		
		ItemListener itemListener = new ItemListener() {
			int contador=0;
			public void itemStateChanged(ItemEvent itemEvent) {
				contador++;
				//System.out.println("pasa"+contador);
				comboBoxCategoria.removeAllItems();
				System.out.println(comboBoxArea.getSelectedIndex() );
				if (comboBoxArea.getSelectedIndex() > 0){
					System.out.println(comboBoxArea.getSelectedItem().toString());
					categoria c = new categoria();
					ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
					
					cats = c.consultarCategoriasConArea(comboBoxArea.getSelectedItem().toString(), miconexion.getMiConexion());
					System.out.println(cats.size());
					if(cats.size()==0){
						comboBoxCategoria.setEnabled(false);
						cargarTextFieldLemas();
						  textFieldLema.setText(null);
					}
					else{
						 textFieldLema.setText(null);
						textFieldLema.setEnabled(true);
						comboBoxCategoria.setEnabled(true);
						comboBoxCategoria.removeAllItems();
						comboBoxCategoria.addItem("  ");
						for(int i=0; i<cats.size(); i++){
							comboBoxCategoria.addItem(cats.get(i).getPalabra());
							
						}
						//cargarTextFieldLemas();
					}
				}
				
				
			}
		};

		

		comboBoxArea.addItemListener(itemListener);
	
		
		

		
		
		JList list = new JList();
		list.setBounds(159, 149, 1, 1);
		contentPane.add(list);
		
		list_1 = new JList();
		list_1.setBounds(335, 149, -164, 1);
		contentPane.add(list_1);
		
		
		//comboBoxCategoria.setEnabled(false);
	}
	
	   public void cargarComboArea(){
   		 area miArea =new area();
       	 ArrayList<idPalabra>misAreas=new ArrayList<idPalabra>();
       	misAreas=area.devolverAreas(_conn);
       	 for(int i=0; i<misAreas.size(); i++){
       		comboBoxArea.addItem(misAreas.get(i).getPalabra());
       	 }	
       	 
	   }
	   
	   public void cargarTextFieldLemas(){
		   textFieldLema.setText(null);
			TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( textFieldLema );
			lema miLema =new lema();
			ArrayList<idPalabra> mislemas=new ArrayList<idPalabra>();
			mislemas=miLema.devolverLemas(_conn);
			 for(int i=0; i<mislemas.size(); i++){
				 textAutoAcompleter.addItem(mislemas.get(i).getPalabra());
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
	   
	  /* public void cargarComboCategoria(String nomArea){
       	 categoria micategoria =new categoria();
       	 ArrayList<idPalabra>misCategorias=new ArrayList<idPalabra>();
       	 misCategorias=micategoria.consultarCategoriasConArea(nomArea, _conn);
       	 for(int i=0; i<misCategorias.size(); i++){
       		comboBoxCategoria.addItem(misCategorias.get(i).getPalabra());
       	 }	
   	 }*/

	   
		   
		   
	   
	private class SwingAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		
	
		
		public void actionPerformed(ActionEvent e) {
	
			
		if (e.getSource()==btnAsociar) {
			
			/*if(comboBoxArea.getSelectedIndex()==0)
				JOptionPane.showMessageDialog(null,"No se ha seleccionado Area."); 
			else if(comboBoxCategoria.getSelectedIndex()==0)
				JOptionPane.showMessageDialog(null,"No se ha seleccionado una categoría."); */
			//else{ 
	
					if(textFieldLema.getText().equalsIgnoreCase(""))
						JOptionPane.showMessageDialog(null,"No hay ningún lema.");
					else{
						//AutoCompleteDecorator.decorate(list_1, textFieldLema);
					
						lema milema=new lema(textFieldLema.getText());
							milema.añadirLema(_conn);
							categoriasLemas miLemacategoria =new categoriasLemas();	
							if(miLemacategoria.añadirLemaCategoria(_conn, comboBoxCategoria.getSelectedItem().toString(),textFieldLema.getText() )){
								JOptionPane.showMessageDialog(null,"Asociacion Correcta");
								System.exit(0);
							}
							else
								JOptionPane.showMessageDialog(null,"La Asociacion no se ha podido realizar");
							
				         //}
					
					}
			
		}
		else if (e.getSource()==btnCancelar) {
            System.exit(0);
        }
	}
		
		
		
		
		}		
}
			
			

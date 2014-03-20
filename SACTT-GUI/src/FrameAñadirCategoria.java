//import java.awt.BorderLayout;
//import java.awt.Desktop.Action;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;


import Area.area;
import Categoria.categoria;
import ConexionBBDD.conexion;
import IdPalabra.idPalabra;


public class FrameAñadirCategoria extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCategoria;
	JComboBox<String> comboBoxAreas ;
	JButton btnAceptar, btnCancelar;
	public  SwingAction action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameAñadirCategoria frame = new FrameAñadirCategoria(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public FrameAñadirCategoria(Connection conn) {
		_conn=conn;
		setTitle("A\u00F1adir Categoria");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Insertar Categoria:");
		lblNewLabel.setBounds(21, 36, 134, 14);
		contentPane.add(lblNewLabel);
		
		textFieldCategoria = new JTextField();
		
		textFieldCategoria.setBounds(133, 33, 279, 20);
		textFieldCategoria.addActionListener(this.action);
		contentPane.add(textFieldCategoria);
		textFieldCategoria.setColumns(10);
		
		JLabel lblAsociarrea = new JLabel("Asociar \u00C1rea:");
		lblAsociarrea.setBounds(21, 78, 93, 14);
		contentPane.add(lblAsociarrea);
		
		comboBoxAreas = new JComboBox();
		comboBoxAreas.setBounds(133, 75, 279, 20);
		contentPane.add(comboBoxAreas);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);
		btnAceptar.setBounds(133, 129, 123, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(289, 129, 123, 23);
		contentPane.add(btnCancelar);
		cargarComboAreas();
	}
		
	 	 
    	 
    	 
    public void cargarComboAreas(){
    		// area miArea =new area();
        	 ArrayList<idPalabra>misAreas=new ArrayList<idPalabra>();
        	 misAreas=area.devolverAreas(_conn);
        	 for(int i=0; i<misAreas.size(); i++){
        		 comboBoxAreas.addItem(misAreas.get(i).getPalabra());
        	 }	
        	// comboBoxCategoria.setEnabled(false);
    	 }
	
	
	private class SwingAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			categoria micategoria;
			if(e.getSource()==textFieldCategoria){
				System.out.println("pasa");
				comboBoxAreas.setEnabled(true);
			}
			else if (e.getSource()==btnAceptar) {
				if(textFieldCategoria.getText().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido Categoría."); 
				else{
			        micategoria =new categoria(textFieldCategoria.getText());
			         
			         if(!textFieldCategoria.getText().equalsIgnoreCase("")){ 	       
							try {
								 micategoria =new categoria(textFieldCategoria.getText());
								if(micategoria.añadirCategoria(_conn,comboBoxAreas.getSelectedItem().toString()))
									JOptionPane.showMessageDialog(null,"Se ha añadido la categoria con su area!"); 
								else
									JOptionPane.showMessageDialog(null,"Ya existe una categoria con ese área"); 
							} 
							catch (SQLException e1) {	
							}
									
						dispose();
			         }
				}
	        }
			else if (e.getSource()==btnCancelar) {
	            dispose();
	        }
		}
	}
	
}

//import java.awt.BorderLayout;
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
import RasgoContenido.rasgocontenido;


public class FrameAñadirRasgo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldRasgo;
	JComboBox<String> comboBoxCategoria ;
	JButton btnAceptar, btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameAñadirRasgo frame = new FrameAñadirRasgo(_conn);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public FrameAñadirRasgo(Connection conn) {
		_conn=conn;
		setTitle("A\u00F1adir Rasgo de Contenido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Insertar Rasgo de Contenido:");
		lblNewLabel.setBounds(10, 36, 157, 14);
		contentPane.add(lblNewLabel);
		
		textFieldRasgo = new JTextField();
		
		textFieldRasgo.setBounds(162, 33, 250, 20);
		textFieldRasgo.addActionListener(this.action);
		contentPane.add(textFieldRasgo);
		textFieldRasgo.setColumns(10);
		
		
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);
		btnAceptar.setBounds(133, 129, 123, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(289, 129, 123, 23);
		contentPane.add(btnCancelar);
		
	}
		
	 	 

private class SwingAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			rasgocontenido miRasgo;
			if(e.getSource()==textFieldRasgo){
				//System.out.println("pasa");
				comboBoxCategoria.setEnabled(true);
			}
			else if (e.getSource()==btnAceptar) {
				if(textFieldRasgo.getText().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido Rasgo de Contenido."); 
				else{
			        miRasgo =new rasgocontenido(textFieldRasgo.getText());
			         
			         if(!textFieldRasgo.getText().equalsIgnoreCase("")){ 	       
							miRasgo =new rasgocontenido(textFieldRasgo.getText());
							if(miRasgo.añadirRasgo(_conn))
								JOptionPane.showMessageDialog(null,"Se ha añadido el Rasgo!"); 
							else
								JOptionPane.showMessageDialog(null,"Ya existe un Rasgo de Contenido");
									
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
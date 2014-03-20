
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ConexionBBDD.conexion;
import Lema.lema;

public class FrameAñadirLema extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldAñadir;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameAñadirLema frame = new FrameAñadirLema(_conn);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public FrameAñadirLema(Connection conn) {
		setTitle("A\u00F1adir Lema");
		_conn=conn;
		//setTitle("A\u00F1adir \u00C1rea");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 124);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("A\u00F1adir Lema:");
		lblNewLabel.setBounds(10, 14, 144, 17);
		contentPane.add(lblNewLabel);
		
		textFieldAñadir = new JTextField();
		textFieldAñadir.setBounds(164, 11, 260, 20);
		contentPane.add(textFieldAñadir);
		textFieldAñadir.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(164, 52, 131, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(305, 52, 119, 23);
		contentPane.add(btnCancelar);
	}
	
	private class SwingAction extends AbstractAction {
	
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btnAceptar) {
				if(textFieldAñadir.getText().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido lema"); 
				else{
			         lema milema =new lema(textFieldAñadir.getText());
						if(milema.añadirLema(_conn))
							JOptionPane.showMessageDialog(null,"Se ha añadido el lema correctamente!");
						else
							JOptionPane.showMessageDialog(null,"Ya existe el lema");
							
		          dispose();
				}
	        }
			else if (e.getSource()==btnCancelar) {
	            dispose();
	        }
		}
	}
}
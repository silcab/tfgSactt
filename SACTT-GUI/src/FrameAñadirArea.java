import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Action;

import Area.area;
import ConexionBBDD.conexion;

public class FrameAñadirArea extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldAñadir;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameAñadirArea frame = new FrameAñadirArea(_conn);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameAñadirArea(Connection conn) {
		setTitle("A\u00F1adir \u00E1rea");
		_conn=conn;
		//setTitle("A\u00F1adir \u00C1rea");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 124);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("A\u00F1adir \u00C1rea:");
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
					JOptionPane.showMessageDialog(null,"No se ha introducido area"); 
				else{
			         area miarea =new area(textFieldAñadir.getText());
			        
					try {
						if(miarea.añadirArea(_conn))
							JOptionPane.showMessageDialog(null,"Se ha añadido el area correctamente!");
						else
							JOptionPane.showMessageDialog(null,"Ya existe el área");
							
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
							
					dispose();
				}
	        }
			else if (e.getSource()==btnCancelar) {
	            dispose();
	        }
		}
	}
}
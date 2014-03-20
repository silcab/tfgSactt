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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Action;

import Area.area;
import ConexionBBDD.conexion;
import IdPalabra.idPalabra;
import Categoria.categoria;
import categoriasLemas.categoriasLemas;
import LemaRasgo.lemaRasgo;
import Lema.lema;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class FrameModificarLema extends JFrame {

	private JPanel contentPane;
	private JComboBox comboModificarLema;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	private JComboBox comboBox;
	private JLabel lblSeleccioneUnLema;
	private JTextField nuevoLema;
	private JLabel lblNewLabel;




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameModificarLema frame = new FrameModificarLema(_conn);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public FrameModificarLema(Connection conn) throws SQLException {
		setTitle("Modificar lema");
		_conn=conn;
		//setTitle("A\u00F1adir \u00C1rea");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 514, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(199, 177, 131, 23);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(368, 177, 119, 23);
		contentPane.add(btnCancelar);



		comboModificarLema = new JComboBox();
		comboModificarLema.setMaximumRowCount(5);
		comboModificarLema.setEditable(true);
		comboModificarLema.setBounds(199, 31, 288, 20);
		ArrayList<idPalabra> lemas = lema.consultarTodosLemas(_conn);
		comboModificarLema.addItem("");
		for(int i=0; i<lemas.size(); i++){
			comboModificarLema.addItem(lemas.get(i).getPalabra());
		}
		contentPane.add(comboModificarLema);
		AutoCompleteDecorator.decorate(comboModificarLema);

		lblSeleccioneUnLema = new JLabel("Seleccione un lema:");
		lblSeleccioneUnLema.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnLema.setBounds(10, 32, 141, 17);
		contentPane.add(lblSeleccioneUnLema);
		
		nuevoLema = new JTextField();
		nuevoLema.setBounds(199, 84, 288, 20);
		contentPane.add(nuevoLema);
		nuevoLema.setColumns(10);
		
		lblNewLabel = new JLabel("Introduzca el nuevo nombre:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 87, 179, 14);
		contentPane.add(lblNewLabel);

	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			boolean lemaModificado = false;
			int idLema=0;

			if (e.getSource()==btnAceptar) {

				if(comboModificarLema.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido lema","ERROR", 2);
				else{
					try {
						lema miLema =new lema(comboModificarLema.getSelectedItem().toString());
						idLema= miLema.consultarUnLema(_conn);

						if (!nuevoLema.getText().equalsIgnoreCase("")){
							lemaModificado = miLema.modificarLemaNombre(nuevoLema.getText(), _conn);	
							
							if (lemaModificado)
								JOptionPane.showMessageDialog(null, "El lema se ha modificado con éxito", "Modificar lema", 1);
							else
								JOptionPane.showMessageDialog(null, "El lema no se ha podido modificar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "El lema no se ha podido modificar", "ERROR", 0);

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.exit(0);
					dispose();
				}
			}
			else if (e.getSource()==btnCancelar) {
				//System.exit(0);
				dispose();
			}
		}
	}
}

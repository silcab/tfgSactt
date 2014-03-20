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


import ConexionBBDD.conexion;
import IdPalabra.idPalabra;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class FrameEliminarRasgo extends JFrame {

	private JPanel contentPane;
	private JComboBox comboEliminarRasgo;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	private JComboBox comboBox;
	private JLabel lblSeleccioneUnRasgo;




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameEliminarRasgo frame = new FrameEliminarRasgo(_conn);
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
	public FrameEliminarRasgo(Connection conn) throws SQLException {
		setTitle("Eliminar rasgo de contenido");
		_conn=conn;
		//setTitle("A\u00F1adir \u00C1rea");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 463, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(177, 177, 131, 23);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(318, 177, 119, 23);
		contentPane.add(btnCancelar);



		comboEliminarRasgo = new JComboBox();
		comboEliminarRasgo.setMaximumRowCount(5);
		comboEliminarRasgo.setEditable(true);
		comboEliminarRasgo.setBounds(221, 31, 216, 20);
		rasgocontenido rasgoAux = new rasgocontenido();
		ArrayList<idPalabra> rasgos = rasgoAux.consultarTodosRasgos(_conn);
		comboEliminarRasgo.addItem("");
		for(int i=0; i<rasgos.size(); i++){
			comboEliminarRasgo.addItem(rasgos.get(i).getPalabra());
		}
		contentPane.add(comboEliminarRasgo);
		AutoCompleteDecorator.decorate(comboEliminarRasgo);

		lblSeleccioneUnRasgo = new JLabel("Seleccione un rasgo de contenido:");
		lblSeleccioneUnRasgo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnRasgo.setBounds(10, 32, 201, 17);
		contentPane.add(lblSeleccioneUnRasgo);


	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			boolean lemaRasgoEliminada = false;
			boolean rasgoEliminado = false;
			int opcion=0;
			int idRasgo = 0;
			int idLema=0;

			if (e.getSource()==btnAceptar) {

				if(comboEliminarRasgo.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido rasgo de contenido","ERROR", 2);
				else{
					try {
						rasgocontenido miRasgo = new rasgocontenido(comboEliminarRasgo.getSelectedItem().toString());
						idRasgo = miRasgo.consultarUnRasgo(_conn);
						
						if (comboEliminarRasgo.getSelectedIndex() > 0)
							//Devuelve yes=0 no=1
							opcion=JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este rasgo de contenido?","AVISO" ,0);

						ArrayList<Integer> lemasDeRasgo;
						lemaRasgo lemaRasgoAux = new lemaRasgo();

						if (opcion == 0){

							lemasDeRasgo = lemaRasgoAux.devolverLemasDeRasgo(idRasgo, _conn);
							for(int k=0; k<lemasDeRasgo.size(); k++){
								lemaRasgoEliminada = lemaRasgoAux.eliminarLemaRasgo(idRasgo,lemasDeRasgo.get(k),_conn);
							}
							
							rasgoEliminado = miRasgo.eliminarRasgo(_conn);

							if (rasgoEliminado)
								JOptionPane.showMessageDialog(null, "El rasgo de contenido se ha eliminado con éxito", "Eliminar lema", 1);
							else
								JOptionPane.showMessageDialog(null, "El rasgo de contenido no se ha podido eliminar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "El rasgo de contenido no se ha podido eliminar", "ERROR", 0);

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
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


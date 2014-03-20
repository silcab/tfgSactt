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
import Lema.lema;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class FrameEliminarLemaRasgo extends JFrame {

	private JPanel contentPane;
	private JComboBox comboEliminarRasgo;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	private JComboBox comboBox;
	private JLabel lblSeleccioneUnRasgo;
	private JComboBox comboEliminarLema;
	private JLabel lblSeleccioneUnLema;




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameEliminarLemaRasgo frame = new FrameEliminarLemaRasgo(_conn);
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
	public FrameEliminarLemaRasgo(Connection conn) throws SQLException {
		setTitle("Eliminar relaci\u00F3n lema - rasgo de contenido");
		_conn=conn;
		//setTitle("A\u00F1adir \u00C1rea");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		comboEliminarRasgo.setEnabled(false);
		comboEliminarRasgo.setMaximumRowCount(5);
		comboEliminarRasgo.setEditable(true);
		comboEliminarRasgo.setBounds(236, 61, 201, 20);
		
		contentPane.add(comboEliminarRasgo);
		AutoCompleteDecorator.decorate(comboEliminarRasgo);

		lblSeleccioneUnRasgo = new JLabel("Seleccione un rasgo de contenido:");
		lblSeleccioneUnRasgo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnRasgo.setBounds(236, 22, 201, 17);
		contentPane.add(lblSeleccioneUnRasgo);
		
		comboEliminarLema = new JComboBox();
		comboEliminarLema.setMaximumRowCount(5);
		comboEliminarLema.setEditable(true);
		comboEliminarLema.setBounds(10, 61, 201, 20);
		ArrayList<idPalabra> lemas = lema.consultarTodosLemas(_conn);
		comboEliminarLema.addItem("");
		for(int i=0; i<lemas.size(); i++){
			comboEliminarLema.addItem(lemas.get(i).getPalabra());
		}
		contentPane.add(comboEliminarLema);
		AutoCompleteDecorator.decorate(comboEliminarLema);
		
		ItemListener itemListener = new ItemListener() {
			int idLema = 0;
			public void itemStateChanged(ItemEvent itemEvent) {
				if (comboEliminarLema.getSelectedIndex() > 0){
					comboEliminarRasgo.setEnabled(true);
					lema miLema = new lema(comboEliminarLema.getSelectedItem().toString());
					lemaRasgo lemaRasgoAux = new lemaRasgo();
					rasgocontenido rasgoAux = new rasgocontenido();
					try {
						idLema = miLema.consultarUnLema(_conn);
						ArrayList<Integer> rasgosDeLema = lemaRasgoAux.devolverRasgosDeLema(idLema, _conn);
						ArrayList<idPalabra> rasgos = new ArrayList<idPalabra>();
						
					//	String r="";
						for(int i=0; i<rasgosDeLema.size(); i++){
							idPalabra palabra = new idPalabra();
							palabra.setIDPalabra(rasgosDeLema.get(i), rasgoAux.consultarRasgoPorId(rasgosDeLema.get(i),_conn));
						//	r = rasgocontenido.consultarRasgoPorId(rasgosDeLema.get(i),_conn);
							//System.out.println(r+" -----");
						//	System.out.println("ID PALABRA:"+palabra.getId());
						//	System.out.println("NOMBRE PALABRA:"+palabra.getPalabra());
							rasgos.add(palabra);
						//	System.out.println("RASGOS DE i:"+i+" "+rasgos.get(i).getPalabra());
							
						}
						//System.out.println("RASGOS DE 0:"+0+" "+rasgos.get(0).getPalabra());

						comboEliminarRasgo.removeAllItems();
						comboEliminarRasgo.addItem("");
						comboEliminarRasgo.setEnabled(true);
						System.out.println("Size: "+rasgos.size());
						for(int j=0; j<rasgos.size(); j++){
							System.out.println(rasgos.get(j).getPalabra()+" --222---");
							comboEliminarRasgo.addItem(rasgos.get(j).getPalabra());
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					comboEliminarRasgo.setEnabled(false);
				}
			}
		};
		
		comboEliminarLema.addItemListener(itemListener);
		
		lblSeleccioneUnLema = new JLabel("Seleccione un lema:");
		lblSeleccioneUnLema.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnLema.setBounds(10, 24, 125, 14);
		contentPane.add(lblSeleccioneUnLema);


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

				if((comboEliminarRasgo.getSelectedItem().toString().equalsIgnoreCase(""))&&(comboEliminarLema.getSelectedItem().toString().equalsIgnoreCase("")))
					JOptionPane.showMessageDialog(null,"No se ha introducido rasgo de contenido y/o lema","ERROR", 2);
				else{
					try {
						rasgocontenido miRasgo = new rasgocontenido(comboEliminarRasgo.getSelectedItem().toString());
						idRasgo = miRasgo.consultarUnRasgo(_conn);
						
						lema miLema = new lema(comboEliminarLema.getSelectedItem().toString());
						idLema = miLema.consultarUnLema(_conn);
						
						if ((comboEliminarLema.getSelectedIndex() > 0 )&&(comboEliminarRasgo.getSelectedIndex() > 0))
							//Devuelve yes=0 no=1
							opcion=JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta relación lema - rasgo de contenido?","AVISO" ,0);

						lemaRasgo lemaRasgoAux = new lemaRasgo();
						if (opcion == 0){
							lemaRasgoEliminada = lemaRasgoAux.eliminarLemaRasgo(idRasgo,idLema,_conn);
							if (lemaRasgoEliminada)
								JOptionPane.showMessageDialog(null, "La relación se ha eliminado con éxito", "Eliminar lema", 1);
							else
								JOptionPane.showMessageDialog(null, "La relación no se ha podido eliminar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "La relación no se ha podido eliminar", "ERROR", 0);

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

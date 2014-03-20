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

public class FrameModificarCategoria extends JFrame {

	private JPanel contentPane;
	private JComboBox comboModificarArea;
	private JComboBox comboModificarCategoria;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	static conexion miconexion=new conexion();
	private JTextField nuevaCategoria;
	private JLabel lblIntroduzcaNuevoNombre;
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					miconexion.iniciarBBDD();
					FrameModificarCategoria frame = new FrameModificarCategoria(_conn);
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
	public FrameModificarCategoria(Connection conn) {
		setTitle("Modificar categor\u00EDa");
		_conn=conn;
		//setTitle("A\u00F1adir \u00C1rea");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 512, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seleccione un \u00E1rea:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 13, 141, 17);
		contentPane.add(lblNewLabel);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(198, 259, 131, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(367, 259, 119, 23);
		contentPane.add(btnCancelar);
		
		comboModificarArea = new JComboBox();
		comboModificarArea.setMaximumRowCount(5);
		comboModificarArea.setEditable(true);
		comboModificarArea.setToolTipText("");
		comboModificarArea.setBounds(10, 54, 179, 20);
		ArrayList<idPalabra> areas = area.devolverAreas(_conn);
		comboModificarArea.addItem("");
		for(int i=0; i<areas.size(); i++){
			comboModificarArea.addItem(areas.get(i).getPalabra());
		}
		contentPane.add(comboModificarArea);
		AutoCompleteDecorator.decorate(comboModificarArea);
		
		comboModificarCategoria = new JComboBox();
		comboModificarCategoria.setMaximumRowCount(5);
		comboModificarCategoria.setEnabled(false);
		comboModificarArea.setEditable(true);
		comboModificarCategoria.setBounds(307, 54, 179, 20);
		contentPane.add(comboModificarCategoria);
		AutoCompleteDecorator.decorate(comboModificarCategoria);
		
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if (comboModificarArea.getSelectedIndex() > 0){
					comboModificarCategoria.setEnabled(true);
					ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
					categoria catAux = new categoria();
					
						cats = catAux.consultarCategoriasConArea(comboModificarArea.getSelectedItem().toString(), _conn);

					comboModificarCategoria.removeAllItems();
					comboModificarCategoria.addItem("");
					comboModificarCategoria.setEnabled(true);
					for(int i=0; i<cats.size(); i++){
						comboModificarCategoria.addItem(cats.get(i).getPalabra());
					}
				}
				else{
					comboModificarCategoria.setEnabled(false);
				}
			}
		};
		
		comboModificarArea.addItemListener(itemListener);
		
		JLabel lblSeleccioneUnaCategora = new JLabel("Seleccione una categor\u00EDa:");
		lblSeleccioneUnaCategora.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnaCategora.setBounds(307, 13, 179, 17);
		contentPane.add(lblSeleccioneUnaCategora);
		
		nuevaCategoria = new JTextField();
		nuevaCategoria.setBounds(198, 137, 288, 20);
		contentPane.add(nuevaCategoria);
		nuevaCategoria.setColumns(10);
		
		lblIntroduzcaNuevoNombre = new JLabel("Introduzca nuevo nombre: ");
		lblIntroduzcaNuevoNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIntroduzcaNuevoNombre.setBounds(10, 139, 160, 14);
		contentPane.add(lblIntroduzcaNuevoNombre);
	}
	
	private class SwingAction extends AbstractAction {
	
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		public void actionPerformed(ActionEvent e) {
			boolean catModificada = false;
			int opcion=0;
			int idArea=0;
			int idCat=0;
			
			if (e.getSource()==btnAceptar) {
				
				if(comboModificarCategoria.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido categoria","ERROR", 2);
				else{
					try {
						area miarea =new area(comboModificarArea.getSelectedItem().toString());
						idArea= miarea.consultarUnArea(_conn);

						categoria miCat = new categoria(comboModificarCategoria.getSelectedItem().toString());
						idCat = miCat.consultarUnaCategoria(_conn);
						
						if (!nuevaCategoria.getText().equalsIgnoreCase("")){
							catModificada = miCat.modificarCategoriaNombre(nuevaCategoria.getText(), _conn, miarea);
							if (catModificada)
								JOptionPane.showMessageDialog(null, "La categoría se ha modificado con éxito", "Modificar categoría", 1);
							else
								JOptionPane.showMessageDialog(null, "La categoría no se ha podido modificar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "La categoría no se ha podido modificar", "ERROR", 0);
					
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

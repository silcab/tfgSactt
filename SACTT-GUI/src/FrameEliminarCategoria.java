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

public class FrameEliminarCategoria extends JFrame {

	private JPanel contentPane;
	private JComboBox comboEliminarArea;
	private JComboBox comboEliminarCategoria;
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
					FrameEliminarCategoria frame = new FrameEliminarCategoria(_conn);
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
	public FrameEliminarCategoria(Connection conn) {
		setTitle("Eliminar categor\u00EDa");
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
		
		comboEliminarArea = new JComboBox();
		comboEliminarArea.setMaximumRowCount(5);
		comboEliminarArea.setEditable(true);
		comboEliminarArea.setToolTipText("");
		comboEliminarArea.setBounds(10, 54, 179, 20);
		ArrayList<idPalabra> areas = area.devolverAreas(_conn);
		comboEliminarArea.addItem("");
		for(int i=0; i<areas.size(); i++){
			comboEliminarArea.addItem(areas.get(i).getPalabra());
		}
		contentPane.add(comboEliminarArea);
		AutoCompleteDecorator.decorate(comboEliminarArea);
		
		comboEliminarCategoria = new JComboBox();
		comboEliminarCategoria.setMaximumRowCount(5);
		comboEliminarCategoria.setEnabled(false);
		comboEliminarArea.setEditable(true);
		comboEliminarCategoria.setBounds(307, 54, 179, 20);
		contentPane.add(comboEliminarCategoria);
		AutoCompleteDecorator.decorate(comboEliminarCategoria);
		
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if (comboEliminarArea.getSelectedIndex() > 0){
					comboEliminarCategoria.setEnabled(true);
					ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
					categoria catAux = new categoria();
					
						cats = catAux.consultarCategoriasConArea(comboEliminarArea.getSelectedItem().toString(), _conn);

					comboEliminarCategoria.removeAllItems();
					comboEliminarCategoria.addItem("");
					comboEliminarCategoria.setEnabled(true);
					for(int i=0; i<cats.size(); i++){
						comboEliminarCategoria.addItem(cats.get(i).getPalabra());
					}
				}
				else{
					comboEliminarCategoria.setEnabled(false);
				}
			}
		};
		
		comboEliminarArea.addItemListener(itemListener);
		
		JLabel lblSeleccioneUnaCategora = new JLabel("Seleccione una categor\u00EDa:");
		lblSeleccioneUnaCategora.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnaCategora.setBounds(307, 13, 179, 17);
		contentPane.add(lblSeleccioneUnaCategora);
	}
	
	private class SwingAction extends AbstractAction {
	
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		public void actionPerformed(ActionEvent e) {
			boolean catLemaEliminada = false;
			boolean lemaRasgoEliminada = false;
			boolean lemaEliminado = false;
			boolean catEliminada = false;
			int opcion=0;
			int idArea=0;
			int idCat=0;
			
			if (e.getSource()==btnAceptar) {
				
				if(comboEliminarCategoria.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido categoria","ERROR", 2);
				else{
					try {
						area miarea =new area(comboEliminarArea.getSelectedItem().toString());
						idArea= miarea.consultarUnArea(_conn);
					//	JOptionPane.showMessageDialog(null, "El area tiene el id "+idArea, "Eliminar categoria", 1);

						if (comboEliminarCategoria.getSelectedIndex() > 0)
							//Devuelve yes=0 no=1
							opcion=JOptionPane.showConfirmDialog(null, "Si elimina esta categoría se eliminarán todos sus lemas asociados, ¿está seguro de eliminar esta categoría?","AVISO" ,0);
						
						categoria miCat = new categoria(comboEliminarCategoria.getSelectedItem().toString());
						idCat = miCat.consultarUnaCategoria(_conn);
					//	JOptionPane.showMessageDialog(null, "La categoria tiene el id "+idCat, "Eliminar categoria", 1);
						
						ArrayList<Integer> lemasDeCat;
						lema miLema = new lema();
						String lem;
						
						ArrayList<Integer> rasgosDeLema;
						lema lemaAux = new lema();
						lemaRasgo lemaRasgoAux = new lemaRasgo();
						
						if (opcion == 0){
							lemasDeCat = categoriasLemas.devolverLemasDeCategoria(idCat, _conn);
						//	JOptionPane.showMessageDialog(null, "Lema long "+lemasDeCat.size(), "INFORMACION LEMAS", 1);
							for(int j=0; j<lemasDeCat.size(); j++){
							//	JOptionPane.showMessageDialog(null, "Lema "+lemasDeCat.get(j)+" de Categoria "+miCat.consultarUnaCategoria(_conn), "INFORMACION CAT Y LEMAS", 1);
								lem = lemaAux.consultarUnLemaPorId(lemasDeCat.get(j), _conn);
							//	JOptionPane.showMessageDialog(null, "Lema nombre "+lem, "INFORMACION CAT Y LEMAS", 1);
								miLema.setLema(lem);
								catLemaEliminada = categoriasLemas.eliminarLemaCategoria(idCat,lemasDeCat.get(j),_conn);

								rasgosDeLema = lemaRasgoAux.devolverRasgosDeLema(lemasDeCat.get(j), _conn);
								//JOptionPane.showMessageDialog(null, "Rasgos long "+rasgosDeLema.size(), "INFORMACION RASGOS", 1);
								for(int k=0; k<rasgosDeLema.size(); k++){
									lemaRasgoEliminada = lemaRasgoAux.eliminarLemaRasgo(rasgosDeLema.get(k),lemasDeCat.get(j),_conn);
								}
								
								//if (j == lemasDeCat.size() -1)
								lemaEliminado = miLema.eliminarLema(_conn);	
							}
							catEliminada = miCat.eliminarCategoria(_conn, miarea);
							
							if (catEliminada)
								JOptionPane.showMessageDialog(null, "La categoría se ha eliminado con éxito", "Eliminar categoría", 1);
							else
								JOptionPane.showMessageDialog(null, "La categoría no se ha podido eliminar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "La categoría no se ha podido eliminar", "ERROR", 0);
					
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

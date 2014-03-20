import java.awt.EventQueue;
import java.awt.ItemSelectable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JButton;
import javax.swing.JTextArea;

import Area.area;
import Categoria.categoria;
import ConexionBBDD.conexion;
import IdPalabra.idPalabra;
import OtrasFunciones.consulta;
import RasgoContenido.rasgocontenido;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import java.awt.ScrollPane;

import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionListener;

import java.awt.Font;


public class consultaGUI{

	JFrame frmConsulta;
	static Connection _conn;
	String seleccionado;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					conexion miconexion=new conexion();
					miconexion.iniciarBBDD();
					consultaGUI window = new consultaGUI(miconexion.getMiConexion());
					window.frmConsulta.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public consultaGUI(Connection conn) throws InstantiationException, IllegalAccessException {
		_conn = conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void initialize() throws InstantiationException, IllegalAccessException {
		//final conexion miconexion=new conexion();
		//try {
			
		//} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
	//	}
		//miconexion.iniciarBBDD();
		
		frmConsulta = new JFrame();
		frmConsulta.setTitle("Consulta");
		frmConsulta.setBounds(100, 100, 583, 396);
		frmConsulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		final JComboBox comboCats = new JComboBox();
		comboCats.setBounds(350, 55, 94, 22);
		frmConsulta.getContentPane().setLayout(null);
		final JComboBox comboAreas = new JComboBox();
		comboAreas.setBounds(85, 55, 94, 22);
		frmConsulta.getContentPane().add(comboAreas);
		final String[] prueba = {""};
		final JList list = new JList(prueba);
		list.setBounds(1, 1, 244, 73);
		list.setEnabled(false);
		frmConsulta.getContentPane().add(list);
		
		area a = new area();
		ArrayList<idPalabra> areas = area.devolverAreas(_conn);
		comboAreas.addItem("   ");
		for(int i=0; i<areas.size(); i++){
			comboAreas.addItem(areas.get(i).getPalabra());
		}
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if (comboAreas.getSelectedIndex() > 0){
					comboCats.setEnabled(true);
					categoria c = new categoria();
					ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
					cats = c.consultarCategoriasConArea(comboAreas.getSelectedItem().toString(), _conn);
					comboCats.removeAllItems();
					comboCats.addItem("   ");
					comboCats.setEnabled(true);
					for(int i=0; i<cats.size(); i++){
						comboCats.addItem(cats.get(i).getPalabra());
					}
				}else{comboCats.setEnabled(false);}
			}
		 };
		    comboAreas.addItemListener(itemListener);

		
		
		comboCats.setEnabled(false);
		frmConsulta.getContentPane().add(comboCats);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(85, 156, 246, 75);
		frmConsulta.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(85, 263, 246, 75);
		frmConsulta.getContentPane().add(scrollPane2);
		
		final JList listResul = new JList();
		scrollPane2.setViewportView(listResul);
		listResul.setEnabled(false);
		
		listResul.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList listResul = (JList)evt.getSource();
		        if (evt.getClickCount() > 1) {   // Triple-click
		            int index = listResul.locationToIndex(evt.getPoint());
		            Object selec = listResul.getSelectedValue();
		        }
		    }
		});
		
		ItemListener itemListener2 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if ((comboCats.getSelectedIndex() > 0) && (comboCats.isEnabled()) && (comboAreas.isEnabled())){
					String[] prueba = {"uno", "dos"};
					ArrayList<String> misRasgos = new ArrayList<String>();
					list.setEnabled(true);
					rasgocontenido r = new rasgocontenido();
					categoria c = new categoria();
					int indCat;
					try {
						indCat = c.consultarUnaCategoriaArea(_conn, comboAreas.getSelectedItem().toString(), comboCats.getSelectedItem().toString());
			            misRasgos = r.consultaRasgosCat(_conn, indCat);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					list.removeAll();
					list.setListData(misRasgos.toArray());
				}else{comboCats.setEnabled(false);}
			}
		};
		comboCats.addItemListener(itemListener2); 
		
		final JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(380, 153, 97, 49);
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List listRasgos = list.getSelectedValuesList();
				ArrayList<String> rsgs = new ArrayList<String>();
				ArrayList<String> resul = new ArrayList<String>();
				for (int i=0; i<listRasgos.size(); i++){
					rsgs.add((String) listRasgos.get(i));
				}
				consulta c = new consulta(rsgs,comboAreas.getSelectedItem().toString(), comboCats.getSelectedItem().toString() );
				c.rasgosAIndices(_conn);
				resul = c.consultar(_conn);
				//textArea.setEnabled(true);
				//textArea.setText("");
				
				listResul.setEnabled(true);
				listResul.removeAll();
				listResul.setListData(resul.toArray());
				
				/*for (int i=0; i<resul.size(); i++){
					textArea.append(resul.get(i));
					textArea.append("\n");
				}*/
			}
		});
		btnConsultar.setEnabled(false);
		frmConsulta.getContentPane().add(btnConsultar);

		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				/*if (evt.getValueIsAdjusting())
					return;
				System.out.println("Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());*/
				
				int[] indices = list.getSelectedIndices();
				if ((indices.length >0) && (comboCats.isEnabled()) && (comboAreas.isEnabled())){
					btnConsultar.setEnabled(true);
				}else{btnConsultar.setEnabled(false);}
			}
		});
		
		
		JLabel lblSeleccioneUnrea = new JLabel("Seleccione un \u00E1rea");
		lblSeleccioneUnrea.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSeleccioneUnrea.setBounds(60, 30, 151, 16);
		frmConsulta.getContentPane().add(lblSeleccioneUnrea);
		
		JLabel lblNewLabel = new JLabel("Seleccione una categor\u00EDa");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(327, 26, 199, 20);
		frmConsulta.getContentPane().add(lblNewLabel);
		

		
		JLabel lblRasgos = new JLabel("Seleccione al menos un rasgo");
		lblRasgos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRasgos.setBounds(59, 108, 296, 22);
		frmConsulta.getContentPane().add(lblRasgos);
		
		JLabel lblMantengaPulsadoCtrl = new JLabel("Mantenga pulsado CTRL para selecci\u00F3n m\u00FAltiple");
		lblMantengaPulsadoCtrl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMantengaPulsadoCtrl.setBounds(60, 127, 370, 22);
		frmConsulta.getContentPane().add(lblMantengaPulsadoCtrl);
		
		JLabel labelResul = new JLabel("Resultado(s) de la consulta:");
		labelResul.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelResul.setBounds(60, 244, 222, 16);
		frmConsulta.getContentPane().add(labelResul);
		

	}
}
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.JMenuItem;

import Area.area;
import Categoria.categoria;
import ConexionBBDD.conexion;
import IdPalabra.idPalabra;
import OtrasFunciones.consulta;
import RasgoContenido.rasgocontenido;

import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;

public class editorConsultaIntegrada extends JFrame {

  private Action openAction = new OpenAction();

  private Action saveAction = new SaveAction();
  
  private Action LanzarConsultaGUI = new LanzarConsultaGUI(this);

  JTextComponent textComp;

  private Hashtable actionHash = new Hashtable();

  public static void main(String[] args) {
	editorConsultaIntegrada editor = new editorConsultaIntegrada();
    editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    editor.setVisible(true);
  }

  // Create an editor.
  public editorConsultaIntegrada() {
    super("Swing Editor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("SACTT Text Editor");
    textComp = createTextComponent();
    makeActionsPretty();

    Container content = getContentPane();
    getContentPane().setLayout(null);
    content.add(textComp);
    setJMenuBar(createMenuBar());
    setSize(624, 506);
  }

  // Create the JTextComponent subclass.
  protected JTextComponent createTextComponent() {
    JTextArea ta = new JTextArea();
    ta.setBounds(0, 0, 606, 435);
    ta.setLineWrap(true);
    return ta;
  }

  // Add icons and friendly names to actions we care about.
  protected void makeActionsPretty() {
    Action a;
    a = textComp.getActionMap().get(DefaultEditorKit.cutAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("cut.gif"));
    a.putValue(Action.NAME, "Cut");

    a = textComp.getActionMap().get(DefaultEditorKit.copyAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("copy.gif"));
    a.putValue(Action.NAME, "Copy");

    a = textComp.getActionMap().get(DefaultEditorKit.pasteAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("paste.gif"));
    a.putValue(Action.NAME, "Paste");

    a = textComp.getActionMap().get(DefaultEditorKit.selectAllAction);
    a.putValue(Action.NAME, "Select All");
  }

  // Create a simple JToolBar with some buttons.
  /*protected JToolBar createToolBar() {
  }*/

  // Create a JMenuBar with file & edit menus.
  protected JMenuBar createMenuBar() {
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("Archivo");
    JMenu edit = new JMenu("Editar");
    menubar.add(file);
    menubar.add(edit);

    JMenuItem menuItem = file.add(getOpenAction());
    menuItem.setText("Abrir");
    JMenuItem menuItem_1 = file.add(getSaveAction());
    menuItem_1.setText("Guardar");
    JMenuItem menuItem_2 = file.add(new ExitAction());
    menuItem_2.setText("Salir");
    JMenuItem mntmCortarAlPortapapeles = edit.add(textComp.getActionMap().get(DefaultEditorKit.cutAction));
    mntmCortarAlPortapapeles.setText("Cortar al portapapeles");
    JMenuItem menuItem_4 = edit.add(textComp.getActionMap().get(DefaultEditorKit.copyAction));
    menuItem_4.setText("Copiar al portapapeles");
    JMenuItem menuItem_3 = edit.add(textComp.getActionMap().get(DefaultEditorKit.pasteAction));
    menuItem_3.setText("Pegar");
    JMenuItem menuItem_5 = edit.add(textComp.getActionMap().get(DefaultEditorKit.selectAllAction));
    menuItem_5.setText("Seleccionar todo");
    
    
    JMenu mnSactt = new JMenu("SACTT");
    JMenuItem menuItemSACTT = mnSactt.add(getConsultaGUI());
    menuItemSACTT.setText("Consulta");
    menubar.add(mnSactt);
    
    
    
    
    return menubar;
  }
  protected Action getConsultaGUI() {
	    return LanzarConsultaGUI;
	  }
  

  // Subclass can override to use a different open action.
  protected Action getOpenAction() {
    return openAction;
  }

  // Subclass can override to use a different save action.
  protected Action getSaveAction() {
    return saveAction;
  }

  protected JTextComponent getTextComponent() {
    return textComp;
  }

  // ********** ACTION INNER CLASSES ********** //

  // A very simple exit action
  public class ExitAction extends AbstractAction {
    public ExitAction() {
      super("Exit");
    }

    public void actionPerformed(ActionEvent ev) {
      System.exit(0);
    }
  }

  // An action that opens an existing file
  class OpenAction extends AbstractAction {
    public OpenAction() {
      super("Open", new ImageIcon("icons/open.gif"));
    }

    // Query user for a filename and attempt to open and read the file into
    // the
    // text component.
    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showOpenDialog(editorConsultaIntegrada.this) != JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileReader reader = null;
      try {
        reader = new FileReader(file);
        textComp.read(reader, null);
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(editorConsultaIntegrada.this,
            "Archivo no encontrado", "ERROR", JOptionPane.ERROR_MESSAGE);
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException x) {
          }
        }
      }
    }
  }

  // An action that saves the document to a file
  class SaveAction extends AbstractAction {
    public SaveAction() {
      super("Save", new ImageIcon("icons/save.gif"));
    }

    // Query user for a filename and attempt to open and write the text
    // component's content to the file.
    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showSaveDialog(editorConsultaIntegrada.this) != JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileWriter writer = null;
      try {
        writer = new FileWriter(file);
        textComp.write(writer);
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(editorConsultaIntegrada.this,
            "No se guardó el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
      } finally {
        if (writer != null) {
          try {
            writer.close();
          } catch (IOException x) {
          }
        }
      }
    }
  }
  
  class LanzarConsultaGUI extends AbstractAction {

	editorConsultaIntegrada editor;
	public LanzarConsultaGUI(editorConsultaIntegrada edi){editor = edi;}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			conexion miconexion = new conexion();
			miconexion.iniciarBBDD();
			consultaGUIEditor consult = new consultaGUIEditor(miconexion.getMiConexion(), editor );
			consult.frmConsulta.setVisible(true);
			
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
	  
  }
  
public void appendString(String pal){
	  //JTextComponent tC = new JTextField("Initial Text");
	  Document doc = textComp.getDocument();

	  try {
		  Caret crt = textComp.getCaret();
		doc.insertString(/*doc.getLength()*/crt.getDot(), pal, null);
	} catch (BadLocationException e) {
		e.printStackTrace();
	};
  }
}





class consultaGUIEditor{

	JFrame frmConsulta;
	static Connection _conn;
	String seleccionado;
	editorConsultaIntegrada editor;


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
	public consultaGUIEditor(Connection conn, editorConsultaIntegrada edi) throws InstantiationException, IllegalAccessException {
		_conn = conn;
		initialize();
		editor = edi;
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
		       if (evt.getClickCount() > 1) {   // Double-click
		            int index = listResul.locationToIndex(evt.getPoint());
		            Object selec = listResul.getSelectedValue();
		            editor.appendString(selec.toString());
		            frmConsulta.dispose();
		        }
		    }
		});
		
		ItemListener itemListener2 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if ((comboCats.getSelectedIndex() > 0) && (comboCats.isEnabled()) && (comboAreas.isEnabled())){
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
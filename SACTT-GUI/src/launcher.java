import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import ConexionBBDD.conexion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JLabel;

import java.awt.Font;
import java.sql.SQLException;


public class launcher extends JFrame {

	private JPanel contentPane;
	static conexion miconexion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					launcher frame = new launcher();
					frame.setVisible(true);
					
					try {
						miconexion=new conexion();
						miconexion.iniciarBBDD();
					} catch (InstantiationException | IllegalAccessException e2) {
						e2.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public launcher() {
		setResizable(false);

		
		setTitle("SACTT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 542, 604);
		contentPane = new JPanel();
		contentPane.setForeground(Color.MAGENTA);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAArea = new JButton("\u00C1rea");
		btnAArea.setForeground(Color.BLUE);
		btnAArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameAñadirArea aarea = new FrameAñadirArea(miconexion.getMiConexion());
				aarea.setVisible(true);
			}
		});
		btnAArea.setBounds(57, 46, 153, 25);
		contentPane.add(btnAArea);
		
		JButton btnACat = new JButton("Categor\u00EDa");
		btnACat.setForeground(Color.BLUE);
		btnACat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirCategoria acategoria = new FrameAñadirCategoria(miconexion.getMiConexion());
				acategoria.setVisible(true);
			}
		});
		btnACat.setBounds(57, 84, 153, 25);
		contentPane.add(btnACat);
		
		JButton btnALema = new JButton("Lema");
		btnALema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirLema frame = new FrameAñadirLema(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnALema.setForeground(Color.BLUE);
		btnALema.setBounds(57, 122, 153, 25);
		contentPane.add(btnALema);
		
		JButton btnEArea = new JButton("\u00C1rea");
		btnEArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameEliminarArea frame = new FrameEliminarArea(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnEArea.setForeground(new Color(204, 51, 0));
		btnEArea.setBounds(57, 313, 153, 25);
		contentPane.add(btnEArea);
		
		JButton btnECat = new JButton("Categor\u00EDa");
		btnECat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarCategoria frame = new FrameEliminarCategoria(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnECat.setForeground(new Color(204, 51, 0));
		btnECat.setBounds(57, 351, 153, 25);
		contentPane.add(btnECat);
		
		JButton btnELema = new JButton("Lema");
		btnELema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarLema frame;
				try {
					frame = new FrameEliminarLema(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnELema.setForeground(new Color(204, 51, 0));
		btnELema.setBounds(57, 389, 153, 25);
		contentPane.add(btnELema);
		
		JButton btnARasgo = new JButton("Rasgo");
		btnARasgo.setForeground(Color.BLUE);
		btnARasgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirRasgo frame = new FrameAñadirRasgo(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnARasgo.setBounds(57, 160, 153, 25);
		contentPane.add(btnARasgo);
		
		JButton btnERasgo = new JButton("Rasgo");
		btnERasgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarRasgo frame;
				try {
					frame = new FrameEliminarRasgo(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnERasgo.setForeground(new Color(204, 51, 0));
		btnERasgo.setBounds(57, 427, 153, 25);
		contentPane.add(btnERasgo);
		
		JButton btnMRasgo = new JButton("Rasgo");
		btnMRasgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameModificarRasgo mrasgo;
				try {
					mrasgo = new FrameModificarRasgo(miconexion.getMiConexion());
					mrasgo.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnMRasgo.setForeground(new Color(139, 0, 139));
		btnMRasgo.setBounds(298, 160, 153, 25);
		contentPane.add(btnMRasgo);
		
		JButton btnMLema = new JButton("Lema");
		btnMLema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameModificarLema mlema;
				try {
					mlema = new FrameModificarLema(miconexion.getMiConexion());
					mlema.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnMLema.setForeground(new Color(139, 0, 139));
		btnMLema.setBounds(298, 122, 153, 25);
		contentPane.add(btnMLema);
		
		JButton btnMCat = new JButton(" Categor\u00EDa");
		btnMCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameModificarCategoria mcat = new FrameModificarCategoria(miconexion.getMiConexion());
				mcat.setVisible(true);
			}
		});
		btnMCat.setForeground(new Color(139, 0, 139));
		btnMCat.setBounds(298, 84, 153, 25);
		contentPane.add(btnMCat);
		
		JButton btnMArea = new JButton("\u00C1rea");
		btnMArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameModificarArea marea = new FrameModificarArea(miconexion.getMiConexion());
				marea.setVisible(true);
			}
		});
		btnMArea.setForeground(new Color(139, 0, 139));
		btnMArea.setBounds(298, 46, 153, 25);
		contentPane.add(btnMArea);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setForeground(new Color(0, 128, 128));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					consultaGUI window = new consultaGUI(miconexion.getMiConexion());
					window.frmConsulta.setVisible(true);
				} catch (InstantiationException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnConsultar.setBounds(298, 313, 153, 25);
		contentPane.add(btnConsultar);
		
		JButton btnBusquedaCiega = new JButton("B\u00FAsqueda ciega");
		btnBusquedaCiega.setForeground(new Color(0, 128, 128));
		btnBusquedaCiega.setBounds(298, 351, 153, 25);
		contentPane.add(btnBusquedaCiega);
		
		JButton btnALemaCat = new JButton("Lema a Categor\u00EDa");
		btnALemaCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirLemaCategoria frame = new FrameAñadirLemaCategoria(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnALemaCat.setForeground(Color.BLUE);
		btnALemaCat.setBounds(57, 202, 153, 25);
		contentPane.add(btnALemaCat);
		
		JLabel lblAadir = new JLabel("A\u00F1adir");
		lblAadir.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAadir.setForeground(Color.BLUE);
		lblAadir.setBounds(57, 17, 56, 16);
		contentPane.add(lblAadir);
		
		JLabel lblModificar = new JLabel("Modificar");
		lblModificar.setForeground(new Color(139, 0, 139));
		lblModificar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblModificar.setBounds(298, 17, 109, 16);
		contentPane.add(lblModificar);
		
		JLabel lblEliminar = new JLabel("Eliminar");
		lblEliminar.setForeground(new Color(204, 51, 0));
		lblEliminar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEliminar.setBounds(57, 284, 109, 16);
		contentPane.add(lblEliminar);
		
		JButton btnARasgoLema = new JButton("Rasgo a Lema");
		btnARasgoLema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirLemaRasgo frame = new FrameAñadirLemaRasgo(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnARasgoLema.setForeground(Color.BLUE);
		btnARasgoLema.setBounds(57, 240, 153, 25);
		contentPane.add(btnARasgoLema);
		
		JButton btnELemaCat = new JButton("Lema-Categor\u00EDa");
		btnELemaCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarCategoriaLema frame;
				try {
					frame = new FrameEliminarCategoriaLema(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnELemaCat.setForeground(new Color(204, 51, 0));
		btnELemaCat.setBounds(57, 465, 153, 25);
		contentPane.add(btnELemaCat);
		
		JButton btnELemaRasgo = new JButton("Lema-Rasgo");
		btnELemaRasgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarLemaRasgo frame;
				try {
					frame = new FrameEliminarLemaRasgo(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnELemaRasgo.setForeground(new Color(204, 51, 0));
		btnELemaRasgo.setBounds(57, 503, 153, 25);
		contentPane.add(btnELemaRasgo);
		
		JButton btnNewButton = new JButton("Crear/Borrar usuario");
		btnNewButton.setForeground(new Color(0, 128, 128));
		btnNewButton.setBounds(298, 389, 153, 25);
		contentPane.add(btnNewButton);
	}
}

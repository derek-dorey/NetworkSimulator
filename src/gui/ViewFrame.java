package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import core.routing.RoutingAlgorithm;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class ViewFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewFrame frame = new ViewFrame();
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
	public ViewFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 813, 578);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{1, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel graphPanel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 12;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		contentPane.add(graphPanel, gbc_panel_1);
		
		JButton btnUndo = new JButton("Undo");
		GridBagConstraints gbc_btnUndo = new GridBagConstraints();
		gbc_btnUndo.insets = new Insets(0, 0, 5, 5);
		gbc_btnUndo.gridx = 1;
		gbc_btnUndo.gridy = 1;
		contentPane.add(btnUndo, gbc_btnUndo);
		
		JButton btnLoad = new JButton("Load");
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoad.gridx = 2;
		gbc_btnLoad.gridy = 1;
		contentPane.add(btnLoad, gbc_btnLoad);
		
		JButton btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 3;
		gbc_btnSave.gridy = 1;
		contentPane.add(btnSave, gbc_btnSave);
		
		JButton btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 5, 5);
		gbc_btnCreate.gridx = 4;
		gbc_btnCreate.gridy = 1;
		contentPane.add(btnCreate, gbc_btnCreate);
		
		JButton btnDestroy = new JButton("Destroy");
		GridBagConstraints gbc_btnDestroy = new GridBagConstraints();
		gbc_btnDestroy.insets = new Insets(0, 0, 5, 5);
		gbc_btnDestroy.gridx = 5;
		gbc_btnDestroy.gridy = 1;
		contentPane.add(btnDestroy, gbc_btnDestroy);
		
		JButton btnConnect = new JButton("Connect");
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnect.gridx = 6;
		gbc_btnConnect.gridy = 1;
		contentPane.add(btnConnect, gbc_btnConnect);
		
		JButton btnDisconnect = new JButton("Disconnect");
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnDisconnect.gridx = 7;
		gbc_btnDisconnect.gridy = 1;
		contentPane.add(btnDisconnect, gbc_btnDisconnect);
		
		JButton btnRate = new JButton("Rate");
		GridBagConstraints gbc_btnRate = new GridBagConstraints();
		gbc_btnRate.insets = new Insets(0, 0, 5, 5);
		gbc_btnRate.gridx = 8;
		gbc_btnRate.gridy = 1;
		contentPane.add(btnRate, gbc_btnRate);
		
		JButton btnAlgorithm = new JButton("Algorithm");
		GridBagConstraints gbc_btnAlgorithm = new GridBagConstraints();
		gbc_btnAlgorithm.insets = new Insets(0, 0, 5, 5);
		gbc_btnAlgorithm.gridx = 9;
		gbc_btnAlgorithm.gridy = 1;
		contentPane.add(btnAlgorithm, gbc_btnAlgorithm);
		
		JButton btnStep = new JButton("Step");
		GridBagConstraints gbc_btnStep = new GridBagConstraints();
		gbc_btnStep.insets = new Insets(0, 0, 5, 5);
		gbc_btnStep.gridx = 10;
		gbc_btnStep.gridy = 1;
		contentPane.add(btnStep, gbc_btnStep);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
	}

}

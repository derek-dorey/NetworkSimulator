package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class NetworkGUI extends JFrame {
	private static final long serialVersionUID = -6411165349120727909L;
	
	private JPanel contentPane;
	private JButton btnStep;
	private JButton btnNew;
	private JButton btnDelete;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JButton btnSetRate;
	private NetworkView network;

	/**
	 * Create the frame.
	 */
	public NetworkGUI(NetworkView network) {
		this.network = network;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 8;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(network, gbc_panel);
		
		btnStep = new JButton("Step");
		GridBagConstraints gbc_btnStep = new GridBagConstraints();
		gbc_btnStep.insets = new Insets(0, 0, 0, 5);
		gbc_btnStep.gridx = 1;
		gbc_btnStep.gridy = 1;
		contentPane.add(btnStep, gbc_btnStep);
		
		btnNew = new JButton("New");
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.insets = new Insets(0, 0, 0, 5);
		gbc_btnNew.gridx = 2;
		gbc_btnNew.gridy = 1;
		contentPane.add(btnNew, gbc_btnNew);
		
		btnDelete = new JButton("Delete");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 3;
		gbc_btnDelete.gridy = 1;
		contentPane.add(btnDelete, gbc_btnDelete);
		
		btnConnect = new JButton("Connect");
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnect.gridx = 4;
		gbc_btnConnect.gridy = 1;
		contentPane.add(btnConnect, gbc_btnConnect);
		
		btnDisconnect = new JButton("Disconnect");
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnDisconnect.gridx = 5;
		gbc_btnDisconnect.gridy = 1;
		contentPane.add(btnDisconnect, gbc_btnDisconnect);
		
		btnSetRate = new JButton("Set Rate");
		GridBagConstraints gbc_btnSetRate = new GridBagConstraints();
		gbc_btnSetRate.insets = new Insets(0, 0, 0, 5);
		gbc_btnSetRate.gridx = 6;
		gbc_btnSetRate.gridy = 1;
		contentPane.add(btnSetRate, gbc_btnSetRate);
	}

	public JButton getBtnStep() {
		return btnStep;
	}
	public JButton getBtnNew() {
		return btnNew;
	}
	public JButton getBtnDelete() {
		return btnDelete;
	}
	public JButton getBtnConnect() {
		return btnConnect;
	}
	public JButton getBtnDisconnect() {
		return btnDisconnect;
	}
	public JButton getBtnSetRate() {
		return btnSetRate;
	}
	public NetworkView getNetworkView() {
		return network;
	}
}

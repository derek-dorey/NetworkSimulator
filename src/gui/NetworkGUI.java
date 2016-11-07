package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = -6411165349120727909L;
	
	private SpringLayout windowLayout;
	private JFrame buttonFrame;
	private JPanel popUpPane;
	private JTextArea textArea;
	private JTextField inputField;
	private JButton getInput;
	
	private JPanel contentPane;
	private JButton btnStep;
	private JButton btnNew;
	private JButton btnDelete;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JButton btnSetRate;
	private NetworkView network;
	private final GUIController controller;

	/**
	 * Create the frame.
	 */
	public NetworkGUI(NetworkView network, GUIController controller) {
		
		this.network = network;
		this.controller = controller;
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
		btnStep.addActionListener(this);
		GridBagConstraints gbc_btnStep = new GridBagConstraints();
		gbc_btnStep.insets = new Insets(0, 0, 0, 5);
		gbc_btnStep.gridx = 1;
		gbc_btnStep.gridy = 1;
		contentPane.add(btnStep, gbc_btnStep);
		
		btnNew = new JButton("New");
		btnNew.addActionListener(this);
		
		
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.insets = new Insets(0, 0, 0, 5);
		gbc_btnNew.gridx = 2;
		gbc_btnNew.gridy = 1;
		contentPane.add(btnNew, gbc_btnNew);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this);
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 3;
		gbc_btnDelete.gridy = 1;
		contentPane.add(btnDelete, gbc_btnDelete);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(this);
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnect.gridx = 4;
		gbc_btnConnect.gridy = 1;
		contentPane.add(btnConnect, gbc_btnConnect);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(this);
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnDisconnect.gridx = 5;
		gbc_btnDisconnect.gridy = 1;
		contentPane.add(btnDisconnect, gbc_btnDisconnect);
		
		btnSetRate = new JButton("Set Rate");
		btnSetRate.addActionListener(this);
		GridBagConstraints gbc_btnSetRate = new GridBagConstraints();
		gbc_btnSetRate.insets = new Insets(0, 0, 0, 5);
		gbc_btnSetRate.gridx = 6;
		gbc_btnSetRate.gridy = 1;
		contentPane.add(btnSetRate, gbc_btnSetRate);
	}
	
	public void createFrame(String displayText, String buttonText, ActionEvent e) {
		
		buttonFrame = new JFrame();
		buttonFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		buttonFrame.setSize(200, 100);
		buttonFrame.setLocation(650, 400);
		
		windowLayout = new SpringLayout();
		
		popUpPane = new JPanel();
		popUpPane.setLayout(windowLayout);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(displayText);
		popUpPane.add(textArea);
	
		inputField = new JTextField(50);
		inputField.setEditable(true);
		popUpPane.add(inputField);
		
		getInput = new JButton(buttonText);
		getInput.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(e.getSource().equals(getBtnNew())) {
				getController().create(inputField.getText());
				inputField.setText(null);						//reset the inputField to reflect the added node
			}
			
			else if(e.getSource().equals(getBtnDelete())) {
				getController().delete(inputField.getText());
				inputField.setText(null);
			}
			
			else if(e.getSource().equals(getBtnConnect())) {
				String[] nodes = inputField.getText().split("");
				getController().connect(nodes);
				inputField.setText(null);
			}
			
			else if(e.getSource().equals(getBtnDisconnect())) {
				String[] nodes = inputField.getText().split("");
				getController().disconnect(nodes);
				inputField.setText(null);
			}
			
			else if(e.getSource().equals(getBtnSetRate())) {
				getController().setRate(Integer.parseInt(inputField.getText()));
				inputField.setText(null);
			}
			
			}
		});
		
		popUpPane.add(getInput);

		windowLayout.putConstraint(SpringLayout.WEST, inputField, 10, SpringLayout.EAST, textArea);
		windowLayout.putConstraint(SpringLayout.SOUTH, getInput, 50, SpringLayout.NORTH, inputField);
		
		buttonFrame.add(popUpPane);
		buttonFrame.setVisible(true);
	}
	
	public void createDeleteFrame() {
		
		buttonFrame = new JFrame();
		buttonFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		buttonFrame.setSize(200, 100);
		buttonFrame.setLocation(650, 400);
	
		windowLayout = new SpringLayout();
	
		popUpPane = new JPanel();
		popUpPane.setLayout(windowLayout);
	
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText("Node Name: ");
		
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
	
	public GUIController getController() {
		return controller;
	}
	
	public NetworkView getView() {
		return network;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(getBtnNew())) {
			createFrame("Node Name: ", "Create", e);
		}
		
		else if (e.getSource().equals(getBtnDelete())) {
			createFrame("Delete Node: ", "Delete", e);
		}
		
		else if (e.getSource().equals(getBtnConnect())) {
			createFrame("Connect Nodes: ", "Connect", e);
		}
		
		else if (e.getSource().equals(getBtnDisconnect())) {
			createFrame("Disconnect Nodes: ", "Disconnect", e);
		}
		
		else if (e.getSource().equals(getBtnSetRate())) {
			createFrame("New Rate: ", "Set", e);
		}
		
		else if(e.getSource().equals(getBtnStep())) {
			getController().step();
		}
		
	}
}

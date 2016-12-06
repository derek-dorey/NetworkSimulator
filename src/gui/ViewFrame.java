package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;

public class ViewFrame extends JFrame {

	private static final long serialVersionUID = 3901616787004121494L;
	private JPanel contentPane;
	private JButton btnUndo;
	private JButton btnLoad;
	private JButton btnSave;
	private JButton btnCreate;
	private JButton btnDestroy;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JButton btnRate;
	private JButton btnAlgorithm;
	private JButton btnStep;
	private JButton btnMetrics;


	/**
	 * Create the frame.
	 */
	public ViewFrame(GraphHandler gh) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 923, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{1, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 12;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		contentPane.add(gh.getComponent(), gbc_panel_1);
		
		btnUndo = new JButton("Undo");
		GridBagConstraints gbc_btnUndo = new GridBagConstraints();
		gbc_btnUndo.insets = new Insets(0, 0, 0, 5);
		gbc_btnUndo.gridx = 1;
		gbc_btnUndo.gridy = 1;
		contentPane.add(btnUndo, gbc_btnUndo);
		
		btnLoad = new JButton("Load");
		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.insets = new Insets(0, 0, 0, 5);
		gbc_btnLoad.gridx = 2;
		gbc_btnLoad.gridy = 1;
		contentPane.add(btnLoad, gbc_btnLoad);
		
		btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 3;
		gbc_btnSave.gridy = 1;
		contentPane.add(btnSave, gbc_btnSave);
		
		btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 4;
		gbc_btnCreate.gridy = 1;
		contentPane.add(btnCreate, gbc_btnCreate);
		
		btnDestroy = new JButton("Destroy");
		GridBagConstraints gbc_btnDestroy = new GridBagConstraints();
		gbc_btnDestroy.insets = new Insets(0, 0, 0, 5);
		gbc_btnDestroy.gridx = 5;
		gbc_btnDestroy.gridy = 1;
		contentPane.add(btnDestroy, gbc_btnDestroy);
		
		btnConnect = new JButton("Connect");
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnect.gridx = 6;
		gbc_btnConnect.gridy = 1;
		contentPane.add(btnConnect, gbc_btnConnect);
		
		btnDisconnect = new JButton("Disconnect");
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnDisconnect.gridx = 7;
		gbc_btnDisconnect.gridy = 1;
		contentPane.add(btnDisconnect, gbc_btnDisconnect);
		
		btnRate = new JButton("Rate");
		GridBagConstraints gbc_btnRate = new GridBagConstraints();
		gbc_btnRate.insets = new Insets(0, 0, 0, 5);
		gbc_btnRate.gridx = 8;
		gbc_btnRate.gridy = 1;
		contentPane.add(btnRate, gbc_btnRate);
		
		btnAlgorithm = new JButton("Algorithm");
		GridBagConstraints gbc_btnAlgorithm = new GridBagConstraints();
		gbc_btnAlgorithm.insets = new Insets(0, 0, 0, 5);
		gbc_btnAlgorithm.gridx = 9;
		gbc_btnAlgorithm.gridy = 1;
		contentPane.add(btnAlgorithm, gbc_btnAlgorithm);
		
		btnMetrics = new JButton("Metrics");
		GridBagConstraints gbc_btnMetrics = new GridBagConstraints();
		gbc_btnMetrics.insets = new Insets(0, 0, 0, 5);
		gbc_btnMetrics.gridx = 10;
		gbc_btnMetrics.gridy = 1;
		contentPane.add(btnMetrics, gbc_btnMetrics);
		
		btnStep = new JButton("Step");
		GridBagConstraints gbc_btnStep = new GridBagConstraints();
		gbc_btnStep.insets = new Insets(0, 0, 0, 5);
		gbc_btnStep.gridx = 11;
		gbc_btnStep.gridy = 1;
		contentPane.add(btnStep, gbc_btnStep);
		setVisible(true);
		
	}
	public JButton getBtnUndo() {
		return btnUndo;
	}
	public JButton getBtnLoad() {
		return btnLoad;
	}
	public JButton getBtnSave() {
		return btnSave;
	}
	public JButton getBtnCreate() {
		return btnCreate;
	}
	public JButton getBtnDestroy() {
		return btnDestroy;
	}
	public JButton getBtnConnect() {
		return btnConnect;
	}
	public JButton getBtnDisconnect() {
		return btnDisconnect;
	}
	public JButton getBtnRate() {
		return btnRate;
	}
	public JButton getBtnAlgorithm() {
		return btnAlgorithm;
	}
	public JButton getBtnStep() {
		return btnStep;
	}
	public JButton getBtnMetrics() {
		return btnMetrics;
	}
}

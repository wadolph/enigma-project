package main.java.GUINew;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Encapsulates the functionality to reset
 * the machine state in one of three different ways.
 * Also includes the functionality for configuring
 * the text output.
 * 
 * ResetPanel.java
 * @author Jessica Ikley
 * @author Team Enigma
 * Dec 5, 2013
 */
@SuppressWarnings("serial")
public class ResetPanel extends JPanel {
	private static final String[] outSpaceChoices = {
		"No Spaces",
		"4 Spaces", 
		"5 Spaces",
		"Original Spaces"
	};
	private static final String[] machineTypeChoices = {
		"No Restrictions",
		"Enigma I",
		"Enigma M3 Army",
		"Enigma M4 Naval", 
		"Enigma M4 R1",
		"Enigma M4 R2"
	};
	private JButton defaultConfigButton;
	private JButton resetIndicatorsButton;
	private JButton clearTextButton;
	private JComboBox<String> outSpaceDropdown;
	private JComboBox<String> machineTypeDropdown;
	private EnigmaSingleton machine = EnigmaSingleton.INSTANCE;
	private char[] defaultRotorPositions = { '!', 'A', 'A', 'A' };
	private char[] defefaultRingSettings = { '!', 'A', 'A', 'A' };
	private int[] defaultRotors = { -1, 0, 1, 2 };
	private String defaultPlugboard = null;
	private int defaultReflector = 0;
	private ImageIcon programLogo;
	private JLabel logoImage;
	
	/**
	 * Default and only constructor. Initializes all components
	 * and lays them out using GroupLayout.
	 * 
	 */
	public ResetPanel() {
		String filePrefix = "/main/resources";
		//Changed to support jar loading.
		try{
		programLogo = (new javax.swing.ImageIcon // Logo
				(getClass().getResource(filePrefix + "/images/programlogo.jpg")));
		logoImage = new JLabel(programLogo);
		} catch(NullPointerException e){
			filePrefix = "";
			programLogo = (new javax.swing.ImageIcon // Logo
					(getClass().getResource(filePrefix + "/images/programlogo.jpg")));
			logoImage = new JLabel(programLogo);
		}
		GroupLayout mainLayout = new GroupLayout(this);
		setLayout(mainLayout);
		setBackground(Color.black);
		
		JLabel outSpaceLabel = new JLabel("Output Space Options");
		outSpaceLabel.setForeground(Color.white);
		JLabel optionsLabel = new JLabel("Reset Options");
		optionsLabel.setForeground(Color.white);
		JLabel machineTypeLabel = new JLabel("Machine Version");
		machineTypeLabel.setForeground(Color.white);
		
		outSpaceDropdown = new JComboBox<String>(outSpaceChoices);
		outSpaceDropdown.setSelectedIndex(0);
		outSpaceDropdown.addActionListener(new SpaceDropdownListener());
		defaultConfigButton = new JButton("Default Configuration");
        defaultConfigButton.addActionListener(new DefaultConfigListener());
		resetIndicatorsButton = new JButton("Reset Indicators");
		resetIndicatorsButton.addActionListener(new ResetIndicatorsListener());
		clearTextButton = new JButton("Clear Text");
		clearTextButton.addActionListener(new ClearTextListener());
		machineTypeDropdown = new JComboBox<String>(machineTypeChoices);
		machineTypeDropdown.setSelectedIndex(0);
		machineTypeDropdown.addActionListener(new MachineTypeListener());
		
		mainLayout.setAutoCreateGaps(true);
		mainLayout.setHorizontalGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(outSpaceLabel)
				.addComponent(outSpaceDropdown)
				.addComponent(optionsLabel)
				.addComponent(defaultConfigButton)
				.addComponent(resetIndicatorsButton)							
				.addComponent(clearTextButton)
				.addComponent(machineTypeLabel)
				.addComponent(machineTypeDropdown)
				.addComponent(logoImage));
		mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
				.addComponent(outSpaceLabel)
				.addComponent(outSpaceDropdown)
				.addComponent(optionsLabel)
				.addComponent(defaultConfigButton)
				.addComponent(resetIndicatorsButton)
				.addComponent(clearTextButton)
				.addComponent(machineTypeLabel)
				.addComponent(machineTypeDropdown)
				.addComponent(logoImage));
		mainLayout.linkSize(SwingConstants.HORIZONTAL, machineTypeDropdown, 
				defaultConfigButton,resetIndicatorsButton, clearTextButton, 
				outSpaceDropdown);
		mainLayout.linkSize(SwingConstants.VERTICAL, machineTypeDropdown, 
				defaultConfigButton,resetIndicatorsButton, clearTextButton, 
				outSpaceDropdown);
	} // end constructor
	
	/**
	 * Private ActionListener class that handles events for
	 * the Default Configuration button. Causes the machine 
	 * to reset to default configuration and propagate
	 * said changes to other GUI components.
	 * @author Jessica Ikley
	 * @author Team Enigma
	 */
	private class DefaultConfigListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("**** DEFAULT CONFIG PRESSED ****");
			machine.setUpdateType(EnigmaSingleton.FULLRESET);
			machine.setState(defaultRotors, defaultReflector,
					defefaultRingSettings, defaultRotorPositions,
					defaultPlugboard);
			machine.notifyObservers();
		}
	} // end DefaultConfigListener class
	
	/**
	 * Private ActionListener that handles events for the 
	 * Reset Indicators button. Causes the machine to reset
	 * the rotor positions back to the initial ones for that
	 * EnigmaMachine.
	 * @author Jessica Ikley
	 * @author Team Enigma
	 *
	 */
	private class ResetIndicatorsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("***** RESET INDICATORS PRESSED *****");
			machine.indicatorReset();
		}
	} // end ResetIndicatorsListener
	
	/**
	 * Private ActionListener that handles events for the Clear Text
	 * button. Causes the GUI to reset the text components without
	 * any machine components changing.
	 * @author Jessica Ikley
	 * @author Team Enigma
	 *
	 */
	private class ClearTextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("***** CLEAR TEXT PRESSED *****");
			machine.setUpdateType(EnigmaSingleton.CLEARTEXT);
			machine.notifyObservers();
		}
	}
	
	/**
	 * Private ActionListener that handles events for the dropdown
	 * that controls the space configuration for the text components.
	 * Currently only affects future text, leaving the past text in 
	 * either text box unchanged.
	 * @author Jessica Ikley
	 * @author Team Enigma
	 *
	 */
	private class SpaceDropdownListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Setting space option: " 
					+ outSpaceDropdown.getSelectedIndex());
			machine.setSpacesOption(outSpaceDropdown.getSelectedIndex());
		}
	}
	/**
	 * Private ActionListener that handles events for the dropdown
	 * that controls the MachineType selection.
	 * Should only affect reflector and rotors options.
	 * @author Rosana Montanez
	 * @author Team Enigma
	 */
	private class MachineTypeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("**** MACHINE TYPE CHANGED ****");
			machine.setMachineType(machineTypeDropdown.getSelectedIndex());
		}
}

} // end ResetPanel class

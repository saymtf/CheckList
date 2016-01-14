package com.saymtf.frames;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.saymtf.file.Writer;
import com.saymtf.list.CheckList;
import com.saymtf.serialize.Deserialize;
import com.saymtf.serialize.Serialize;

public class Page implements Serializable {

	/** SerialVersionUID  */
	private static final long serialVersionUID = 1029588655046437768L;
	private Deserialize deser;
	private Serialize ser;
	private JFrame frame;
	private JPanel editPanel;
	private JPanel frontPanel;
	private JPanel cardPanel;
	private List<JPanel> panels;

	/**
	 * Constructor
	 * Initialize
	 */
	public Page() {
	// Initialize
		frame = new JFrame(); // init frame
		panels = new ArrayList<JPanel>();
		editPanel = new JPanel(); // init editPanel
		frontPanel = new JPanel();

	// Other Inits
		deser = new Deserialize();
		ser = new Serialize();
		
	// Card Panel
		cardPanel = new JPanel(); 
		cardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		cardPanel.setLayout(new CardLayout());

		mainPage(); // Start the MAIN GUI

	// The Rest
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //frame.setSize(new Dimension(1024, 1024));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * The First page that you see whne the program opens
	 * 
	 * Uses:
	 * 	Shows Edit Button (Admin Use Only PASS:POOP)
	 * 	Description + Comments (Only Edit Comment(s))
	 * 	Sign Name + Finish
	 */
	private void mainPage() {
	// Deserialize & get panels
		deser.deserialize();
		panels = deser.getPanelList();
		
	// Start Main GUI
		JPanel mainPanel = new JPanel(new BorderLayout()); // main panel

	// Settings Button
		JButton editButton = new JButton(new ImageIcon("/Users/mitchellfenton/Documents/workspace/RollicksCheckList/images/setting.png"));

	// When Button is clicked
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPasswordField passwordField = new JPasswordField(); // password field
				JOptionPane.showMessageDialog(null, passwordField, "Password", JOptionPane.WARNING_MESSAGE); // show dialog
				if(new String(passwordField.getPassword()).equals("poop")) {
					CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
					cardLayout.show(cardPanel, "EditPanel");
					frontPanel.remove(mainPanel);
					changeFrame();
				}
			}
		});

	// Description Scroll Panel
		JPanel scrollPanel = new JPanel(new GridLayout(0,1,5,5));
		scrollPanel.setAutoscrolls(true);

	// Show Labels
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 256, 0));
		JLabel descriptionLabel = new JLabel("Descriptions");
		descriptionLabel.setFont(new Font("Serif", Font.BOLD, 32));
		JLabel commentLabel = new JLabel("Comments");
		commentLabel.setFont(new Font("Serif", Font.BOLD, 32));
		labelPanel.add(descriptionLabel);
		labelPanel.add(commentLabel);

		scrollPanel.add(labelPanel);// Add labelPanel to the scrollPanel

	// Set the Scroll Pane with Scroll Panel Component
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(1000,650));

		displayPanels(scrollPanel); //Display The Panels
		
	// Sign and Finish
		JPanel finishPanel = new JPanel(); // PAnel
		JLabel employeeLabel = new JLabel("Employee Name(s):");
		JTextField employeeNamesField = new JTextField(); // Name Text Field
		employeeNamesField.setPreferredSize(new Dimension(256, 28));

	// Finish Button
		JButton finishedButton = new JButton("Finished");

	// Finish Button Clicked..
		finishedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(employeeNamesField.getText().equals("")) { JOptionPane.showMessageDialog(null, "Please Sign the Employee Field.."); }
				else { Page.this.finalize(employeeNamesField); }
			}
		});

	// Add to final panel + Main Panel
		finishPanel.add(employeeLabel);
		finishPanel.add(employeeNamesField);
		finishPanel.add(finishedButton);
		mainPanel.add(editButton, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(finishPanel, BorderLayout.SOUTH);
		
	// Add to frontPanel + CardPanel
		frontPanel.add(mainPanel);
		cardPanel.add(frontPanel, "frontPanel");
		cardPanel.add(editPanel, "EditPanel");
		frame.add(cardPanel);
	}
	
	private void finalize(JTextField employeeNamesField) {
		Calendar today = Calendar.getInstance(); // Get The Date
		
	// The String that will be added into the dialog.
		String string = "Today's Information is: " + today.getTime().toString() + 
				"\n Employee(s): " + employeeNamesField.getText() + 
				"\n Are you sure everything is finished?"; 
		
	// Show Dialog
		int val = JOptionPane.showConfirmDialog(null, string);
		
	// User Pressed Yes
		if(val == 0) {
		// Save whole checklist information, date, time, employees, what was checked..
			try { Writer.writeToFile(today, employeeNamesField, panels); } 
			catch (FileNotFoundException e) { System.out.println("File was not found."); } 
			catch (UnsupportedEncodingException e) { System.out.println("The Encoding was used in a different format :("); }
			

			//Send to steve


			//Restart everything
			final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			File currentJar;
			try {
				currentJar = new File(CheckList.class.getProtectionDomain().getCodeSource().getLocation().toURI());
				/* is it a jar file? */
				if(!currentJar.getName().endsWith(".jar"))
					return;

				/* Build command: java -jar application.jar */
				final ArrayList<String> command = new ArrayList<String>();
				command.add(javaBin);
				command.add("-jar");
				command.add(currentJar.getPath());

				final ProcessBuilder builder = new ProcessBuilder(command);
				builder.start();
				System.exit(0);
			} catch (URISyntaxException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * When Settings Button is clicked you come here
	 * 
	 * You Can:
	 * 		Add New Description Panel
	 * 		Edit Each Descriptions
	 * 		Remove A Description
	 * 		Done & Save
	 * 		Return to Main Page
	 */
	private void changeFrame() {
		descriptionEditable(); // description is now editable
		showButton(); //Show the Remove Button
	
	// Main Panel for Settings
		JPanel mainPanel = new JPanel(new BorderLayout()); 

	//Add Button Panel
		JPanel addButtonPanel = new JPanel();
		JButton addButton = new JButton("+");
		addButtonPanel.add(addButton); // add button to panel

	//Description Scroll Panel
		JPanel scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		scrollPanel.setAutoscrolls(true);
		
	//Set the Scroll Pane for the Scroll Panel Component
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(928,656));

		displayPanels(scrollPanel); // Display All the Current JPanel Texts

	//When Add(+) Button Gets Clicked..
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
			// Panel That Will Be Added..
				JPanel textPanels = new JPanel(); // The Panel will be added to list 
			
			// Description Text Area
				JTextArea textArea = new JTextArea("");
				textArea.setPreferredSize(new Dimension(420,64));
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);

			// Comment Text Area
				JTextArea sign = new JTextArea("");
				sign.setPreferredSize(new Dimension(300,64));  
				sign.setLineWrap(true);
				sign.setWrapStyleWord(true);

			// Remove The Panel Button
				JButton removeButton = new JButton("X");

			// Remove Panel Just Created.
				removeButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(int i = 0; i < panels.size(); i++) {
							if(e.getSource() == panels.get(i).getComponent(2)) {
								panels.get(i).removeAll();
								panels.remove(i);
								displayPanels(scrollPanel);
								scrollPane.revalidate();
								scrollPane.repaint();
								break;
							}
						}
					}
				});

			// Add to Panel
				textPanels.add(textArea);
				textPanels.add(sign);
				textPanels.add(removeButton);

			// Add Panel to ArrayList
				panels.add(textPanels);
				
			//Display the panels in the list
				scrollPane.revalidate();
				scrollPane.repaint();
				displayPanels(scrollPanel);
			}
		});


	//	Remove Panel That has been Serialized
		for(int i = 0; i < panels.size(); i++) {
			((JButton)panels.get(i).getComponent(2)).addActionListener(new Listener(i, scrollPanel, scrollPane));
		}

	//Done Panel
		JPanel doneEdittingButtonPanel = new JPanel();
		JButton doneEdittingButton = new JButton("Done");

	//When Button is Clicked
		doneEdittingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			// Hide the remove(X) button & Make Description Uneditable
				removeButton();
				descriptionUnEditable();
				
			// Save Changes
				ser.serialize(panels);

			// Go Back To Main Layout
				CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
				cardLayout.show(cardPanel, "MainPanel");
				mainPage();
				editPanel.remove(mainPanel);
			}
		});

		doneEdittingButtonPanel.add(doneEdittingButton); // add button to panel

	// Add Components to Main Panel
		mainPanel.add(addButtonPanel,  BorderLayout.NORTH);
		mainPanel.add(scrollPane,  BorderLayout.CENTER);
		mainPanel.add(doneEdittingButtonPanel,  BorderLayout.SOUTH);

	// Add To Edit Panel
		editPanel.add(mainPanel);
	}
	
	/**
	 * Allows the Admin to edit the Description.
	 */
	private void descriptionEditable() {
		for(JPanel panel: panels) { ((JTextArea)panel.getComponent(0)).setEditable(true); }
	}

	/**
	 * Remove the ability to edit the description.
	 */
	private void descriptionUnEditable() {
		for(JPanel panel: panels) { ((JTextArea)panel.getComponent(0)).setEditable(false); }
	}

	/**
	 * Allows the Admin to remove a panel.
	 */
	private void showButton() {
		for(JPanel panel: panels) { panel.getComponent(2).setVisible(true); }
	}

	/**
	 *  Remove the ability to remove the panel.
	 */
	private void removeButton() {
		for(JPanel panel: panels) { panel.getComponent(2).setVisible(false); }
	}
	
	/**
	 * @param textAreaPanels is the needed Panel to add the list of JPanels
	 * Adds the JPanels in the list onto a outside Panel
	 */
	private void displayPanels(JPanel textAreaPanels) {
		for(JPanel panel: panels) { textAreaPanels.add(panel); }
	}
	
	/**
	 * @author mitchellfenton
	 *Action Listener When the remove button(of the serialized) gets pressed
	 */
	private class Listener implements ActionListener {
		int i = -1;
		JPanel scrollPanel;
		JScrollPane scrollPane;
		
		public Listener(int i, JPanel panel, JScrollPane pane) {
			this.i = i;
			scrollPane = pane;
			scrollPanel = panel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			panels.get(i).removeAll();
			panels.remove(i);
			displayPanels(scrollPanel);
			scrollPane.revalidate();
			scrollPane.repaint();
		}
	}
}

package com.saymtf.list;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
public class CheckList {

	public static void main(String[] args) {
		CheckList cL = new CheckList(); // Program
	}



	private JFrame frame;
	private JPanel editPanel;
	private JPanel frontPanel;
	private JPanel cardPanel;
	private List<JPanel> panels;
	// (Need Password)
	//Add button --> New jtable 
	//Remove Button --> Remove jtable

	/**
	 * Constructor
	 * 
	 * Initialize
	 */
	public CheckList() {
		// Initialize
		frame = new JFrame(); // init frame
		panels = new ArrayList<JPanel>();
		editPanel = new JPanel(); // init editPanel
		frontPanel = new JPanel();

		//Other Inits

		// Card Panel
		cardPanel = new JPanel(); 
		cardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		cardPanel.setLayout(new CardLayout());

		mainPage();

		//These are needed
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //frame.setSize(new Dimension(1024, 1024));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.pack();
	}

	/**
	 * The First page that you see whne the program opens
	 * 
	 * Shows Edit Button
	 * Scroll Panel
	 * The Tasks
	 * 
	 */
	private void mainPage() {

		JPanel mainPanel = new JPanel(new BorderLayout()); // main panel

		// Settings Button
		JButton editButton = new JButton(new ImageIcon("/Users/mitchellfenton/Documents/workspace/RollicksCheckList/images/setting.png"));

		// When Button is clicked
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//JPasswordField passwordField = new JPasswordField(); // password field
				//JOptionPane.showMessageDialog(null, passwordField, "Password", JOptionPane.WARNING_MESSAGE); // show dialog
				//if(new String(passwordField.getPassword()).equals("poop")) {
				CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
				cardLayout.show(cardPanel, "EditPanel");
				frontPanel.remove(mainPanel);
				changeFrame();
				//}

			}

		});

		//Description Scroll Panel
		JPanel scrollPanel = new JPanel(new GridLayout(0,1,5,5));
		scrollPanel.setAutoscrolls(true);

		//Show Labels
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 256, 0));
		JLabel descriptionLabel = new JLabel("Descriptions");
		JLabel commentLabel = new JLabel("Comments");
		labelPanel.add(descriptionLabel);
		labelPanel.add(commentLabel);

		scrollPanel.add(labelPanel);

		//Set the Scroll Pane with Scroll Panel Component
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(1000,650));


		//deserialize(scrollPanel);
		displayPanels(scrollPanel);


		//Sign and Finish
		JPanel finishPanel = new JPanel(); // PAnel
		JLabel employeeLabel = new JLabel("Employee Name(s):");
		JTextField employeeNamesField = new JTextField(); // Name Text Field
		employeeNamesField.setPreferredSize(new Dimension(256, 28));


		/******FIX THE UPDATED TIME (PUT IN thread or create a pop up within that function get the current time before accepting)****/
		// Get the Date

		//Finish Button
		JButton finishedButton = new JButton("Finished");

		// Finish Button Clicked..
		finishedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(employeeNamesField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Sign the Employee Field..");
				}else {
					Calendar today = Calendar.getInstance();
					int val = JOptionPane.showConfirmDialog(null, 
							"Today's Information is: " + today.getTime().toString() + 
							"\n Employee(s): " + employeeNamesField.getText() + 
							"\n Are you sure everything is finished?"); // show dialog
					//Yes
					if(val == 0) {
						System.out.println("SENT");
						// Save whole checklist information, date, time, employees, what was checked..
						try {
							PrintWriter writer = new PrintWriter(today.getTime().toString(), "UTF-8");
							writer.write("Date: " + today.getTime().toString() + "\n\n"); // get the date
							writer.write("Employee(s): " + employeeNamesField.getText() + "\n\n"); // get the employees working/signed

							//Write all the descriptions and Comments to the file
							for(JPanel panel: panels) {
								writer.write(((JTextArea)panel.getComponent(0)).getText() + "\t\t" + 
										((JTextArea)panel.getComponent(1)).getText() + "\n\n");
							}

							writer.close();
						} catch (FileNotFoundException | UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}


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
			}
		});

		//SEND TO STEVE THROUGH EMAIL

		// Add to final panel
		finishPanel.add(employeeLabel);
		finishPanel.add(employeeNamesField);
		finishPanel.add(finishedButton);

		mainPanel.add(editButton, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(finishPanel, BorderLayout.SOUTH);
		// Add to frontPanel
		frontPanel.add(mainPanel);


		cardPanel.add(frontPanel, "frontPanel");
		cardPanel.add(editPanel, "EditPanel");
		frame.add(cardPanel);
	}


	/**
	 * When Settings Button is clicked you come here
	 * 
	 * You Can:
	 * Edit The Descriptions
	 * Remove Descriptions
	 * Finish & Save
	 * 
	 */
	private void changeFrame() {
		descriptionEditable(); // description is now editable

		JPanel mainPanel = new JPanel(new BorderLayout()); // Main Panel for Settings

		JPanel addButtonPanel = new JPanel();
		// Add New Description Button
		JButton addButton = new JButton("+");


		addButtonPanel.add(addButton); // add button to panel


		//Description Scroll Panel
		JPanel scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

		//Set the Scroll Pane with Scroll Panel Component
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setAutoscrolls(true);
		scrollPane.setPreferredSize(new Dimension(928,656));

		//Show the Remove Button
		showButton();

		// Display All the Current JPanel Texts
		displayPanels(scrollPanel);


		/*

		The Remove Button WILL NOT work if serialized, since the button will not be apart of the actionlistener any more..

		Fix:
			Have a listener for the panel object array.. (maybe in thread) to check if something was clicked.

		 */

		//When Add(+) Button Gets Clicked..
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Panel That Will Be Added..
				JPanel textPanels = new JPanel(); // create panel to add to list 

				JTextArea textArea = new JTextArea("");
				textArea.setPreferredSize(new Dimension(420,64));
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);

				JTextArea sign = new JTextArea("");
				sign.setPreferredSize(new Dimension(300,64));  
				sign.setLineWrap(true);
				sign.setWrapStyleWord(true);

				JButton removeButton = new JButton("X");


				// If Button Clicked (REMOVE)
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

				textPanels.add(textArea);
				textPanels.add(sign);
				textPanels.add(removeButton);

				// Add Panel to List
				panels.add(textPanels);


				//Display the panels in the list
				displayPanels(scrollPanel);
				scrollPane.revalidate();
				scrollPane.repaint();
			}
		});


		//		// TEST TO REMOVE PANEL
		//		for(JPanel checkClick: panels) {
		//			
		//			System.out.println("Clicked");
		//			((JButton)checkClick.getComponent(2)).addActionListener(new ActionListener() {
		//
		//				@Override
		//				public void actionPerformed(ActionEvent e) {
		//					checkClick.removeAll();
		//					checkClick.remove(checkClick);
		//					displayPanels(scrollPanel);
		//					scrollPane.revalidate();
		//					scrollPane.repaint();
		//				}
		//			});
		//			
		//		}
		//		

		JPanel doneEdittingButtonPanel = new JPanel();

		// Finish Editing Button
		JButton doneEdittingButton = new JButton("Done");


		//When Button is Clicked
		doneEdittingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// iterate through and make description setEditable False!

				// Hide the remove(X) button & Make Description Uneditable
				removeButton();
				descriptionUnEditable();

				// Save What was inputted into description
				//serialize();

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



	private void descriptionEditable() {
		for(JPanel panel: panels) {
			((JTextArea)panel.getComponent(0)).setEditable(true);
		}
	}


	private void descriptionUnEditable() {
		for(JPanel panel: panels) {
			((JTextArea)panel.getComponent(0)).setEditable(false);
		}
	}

	private void showButton() {
		for(JPanel panel: panels) {
			panel.getComponent(2).setVisible(true);
		}
	}

	private void removeButton() {
		for(JPanel panel: panels) {
			panel.getComponent(2).setVisible(false);
		}
	}

	private void displayPanels(JPanel textAreaPanels) {
		//serialize();
		//deserialize();
		for(JPanel panel: panels) {
			textAreaPanels.add(panel);
		}
	}

	/*
	@SuppressWarnings("unchecked")
	private void deserialize(JPanel panel) {
		try
		{
			FileInputStream fileIn = new FileInputStream("/Users/mitchellfenton/Documents/workspace/RollicksCheckList/employeeList.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			panels = (ArrayList<JPanel>) in.readObject();
			in.close();
			fileIn.close();
			displayPanels(panel);
		}catch(IOException i)
		{
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c)
		{
			System.out.println("JPanel class not found");
			c.printStackTrace();
			return;
		}
	}

	private void serialize() {
		try {
			FileOutputStream fileOut = new FileOutputStream("/Users/mitchellfenton/Documents/workspace/RollicksCheckList/employeeList.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(panels);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in /tmp/employee.ser");
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}
*/
}

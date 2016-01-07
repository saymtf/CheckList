package com.saymtf.list;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
public class CheckList{
	public static void main(String[] args) {
		new CheckList();
	}


	private JFrame frame;
	private JPanel editPanel;
	private JPanel cardPanel;
	private List<JPanel> panels;
	// (Need Password)
	//Add button --> New jtable 
	//Remove Button --> Remove jtable

	public CheckList() {
		// Initialize
		frame = new JFrame(); // init frame
		panels = new ArrayList<JPanel>();
		editPanel = new JPanel(); // init editPanel

		// Card Panel
		cardPanel = new JPanel(); 
		cardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		cardPanel.setLayout(new CardLayout());

		mainPage();

		//These are needed
		frame.setSize(new Dimension(1024, 1024));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.pack();
	}

	private void mainPage() {

		JPanel mainPanel = new JPanel(); // main panel

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
					changeFrame();
				//}
					
			}

		});

		//Description Scroll Panel
		JPanel scrollPanel = new JPanel(new GridLayout(0,1,5,5));

		//Set the Scroll Pane with Scroll Panel Component
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setAutoscrolls(true);
		scrollPane.setPreferredSize(new Dimension(800,500));
		
		//deserialize(scrollPanel);
		
		mainPanel.revalidate();
		mainPanel.repaint();



		mainPanel.add(editButton);
		mainPanel.add(scrollPane);
		cardPanel.add(mainPanel, "MainPanel");
		cardPanel.add(editPanel, "EditPanel");
		frame.add(cardPanel);
	}

	
	/**
	 * Edit The Descriptions
	 * 
	 */
	private void changeFrame() {
		JPanel mainPanel = new JPanel(); // Main Panel for Settings
		mainPanel.setLayout(new GridLayout(0,1)); // make it a grid layout

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
		scrollPane.setPreferredSize(new Dimension(800,200));

		//Show the Remove Button
		showButton();
		
		// Display All the Current JPanel Texts
		displayPanels(scrollPanel);

		//When Add Button Gets Clicked..
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel textPanels = new JPanel(); // create panel to add to list 

				JTextArea textArea = new JTextArea("");
				textArea.setPreferredSize(new Dimension(628,64));
				
				JTextArea sign = new JTextArea("");
				sign.setPreferredSize(new Dimension(64,64));  

				JButton removeButton = new JButton("X");

				// If Button Clicked (REMOVE)
				removeButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						for(int i = 0; i < panels.size(); i++) {
							if(panels.get(i).hashCode() == textPanels.hashCode()) {
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
				scrollPane.validate();

			}
		});

		JPanel doneEdittingButtonPanel = new JPanel();

		// Finish Editing Button
		JButton doneEdittingButton = new JButton("Done");


		//When Button is Clicked
		doneEdittingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// iterate through and make description setEditable False!
				
				// Remove the X button
				removeButton();
				
				// Save What was inputted into description
				//serialize();

				// Go Back To Main Layout
				CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
				cardLayout.show(cardPanel, "MainPanel");
				editPanel.remove(mainPanel);
			}
		});

		doneEdittingButtonPanel.add(doneEdittingButton); // add button to panel

		// Add Components to Main Panel
		mainPanel.add(addButtonPanel);
		mainPanel.add(scrollPane);
		mainPanel.add(doneEdittingButtonPanel);

		// Add To Edit Panel
		editPanel.add(mainPanel);
	}
	
	private void descriptionEditable() {
		for(JPanel panel: panels) {
			panel.getComponent(2).setVisible(true);
		}
	}
	
	
	private void descriptionUnEditable() {
		for(JPanel panel: panels) {
			panel.getComponent(0).setEnabled(false);
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

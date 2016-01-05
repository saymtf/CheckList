package com.saymtf.list;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
		frame = new JFrame(); // init frame
		panels = new ArrayList<JPanel>();
		editPanel = new JPanel(); // init editPanel
		cardPanel = new JPanel(); // Card Panel
		cardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		cardPanel.setLayout(new CardLayout());

		JPanel mainPanel = new JPanel(); // main panel

		// Settings Button
		JButton editButton = new JButton(new ImageIcon("/Users/mitchellfenton/Documents/workspace/RollicksCheckList/images/setting.png"));

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

		displayPanels(mainPanel);

		mainPanel.validate();
		mainPanel.add(editButton);
		cardPanel.add(mainPanel, "MainPanel");
		cardPanel.add(editPanel, "EditPanel");

		frame.add(cardPanel);
		//These are needed
		frame.setSize(new Dimension(1024, 1024));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.pack();
	}

	private void changeFrame() {
		JPanel mainPanel = new JPanel(); // Main Panel for Settings
		mainPanel.setLayout(new GridLayout(0,1,5,5)); // make it a grid layout

		// Add New Description Button
		JButton addButton = new JButton("+");
		addButton.setPreferredSize(new Dimension(128,16));

		//Description Scroll Panel
		JPanel scrollPanel = new JPanel(new GridLayout(0,1,5,5));

		//Set the Scroll Pane with Scroll Panel Component
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setAutoscrolls(true);
		scrollPane.setPreferredSize(new Dimension(800,300));

		// Display All the Current JPanel Texts
		displayPanels(scrollPanel);

		//Add to Main Panel


		//When Add Button Gets Clicked..
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(); // create panel to add to list 
				JTextArea textArea = new JTextArea("");
				textArea.setPreferredSize(new Dimension(256,48));
				JTextArea sign = new JTextArea("");
				sign.setPreferredSize(new Dimension(72,48));  
				panel.add(textArea);
				panel.add(sign);

				// Add Panel to List
				panels.add(panel);


				//Display the panels in the list
				displayPanels(scrollPanel);
				scrollPane.validate();

			}
		});

		// Finish Editing Button
		JButton doneEdittingButton = new JButton("Done");
		doneEdittingButton.setPreferredSize(new Dimension(128,16));
		
		//When Button is Clicked
		doneEdittingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// iterate through and make setEditable False!


				// Go Back To Main Layout
				CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
				cardLayout.show(cardPanel, "MainPanel");
				editPanel.remove(mainPanel);
			}
		});

		
		// Add Components to Main Panel
		mainPanel.add(addButton);
		mainPanel.add(scrollPane);
		mainPanel.add(doneEdittingButton);

		// Add To Edit Panel
		editPanel.add(mainPanel);
	}

	private void displayPanels(JPanel textAreaPanels) {
		for(JPanel panel: panels) {
			textAreaPanels.add(panel);
		}

	}

}

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
				JPasswordField passwordField = new JPasswordField(); // password field
				//JOptionPane.showMessageDialog(null, passwordField, "Password", JOptionPane.WARNING_MESSAGE); // show dialog
				//if(passwordField.getPassword().equals("poop")) {
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
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0,1,5,5));
		JButton addButton = new JButton("+");
		addButton.setPreferredSize(new Dimension(128,72));
		
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		JPanel scrollPanel = new JPanel();
		
		scrollBar.add(scrollPanel);
		// Display All the JPanel Texts
		displayPanels(mainPanel);
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(); // create panel to add to list
				panel.setPreferredSize(new Dimension(1024,512));
				panel.setBackground(new Color(100,100,100));
				JTextArea textArea = new JTextArea("");
				JTextField sign = new JTextField("");
				textArea.setEditable(true);
				sign.setEditable(true);
				panel.add(textArea);
				panel.add(sign);
				
				// Add Panels to list
				panels.add(panel);
				
				//Display the panels in the list
				displayPanels(scrollPanel);
				
				scrollPanel.validate();
			}
		});
		
		
		JButton doneEdittingButton = new JButton("Done");
		doneEdittingButton.setPreferredSize(new Dimension(128,72));
		
		doneEdittingButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// iterate through and make setEditable False!
				
				
				// Go Back
				CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
				cardLayout.show(cardPanel, "MainPanel");
				editPanel.remove(mainPanel);
			}
		});
		
		mainPanel.add(addButton);
		mainPanel.add(scrollBar);
		mainPanel.add(doneEdittingButton);
		mainPanel.setPreferredSize(new Dimension(512,512));
		editPanel.add(mainPanel);
	}
	
	private void displayPanels(JPanel textAreaPanels) {
		for(JPanel panel: panels) {
			textAreaPanels.add(panel);
		}
		
	}
	
}

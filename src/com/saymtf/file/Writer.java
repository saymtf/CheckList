package com.saymtf.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Writer {
	
	/**
	 * @param today The Date will be saved as the file name + on text
	 * @param employeeNamesField The Names who signed the doc
	 * @param panels any comments that were left as is
	 * 
	 * Will write to the file
	 * 
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException
	 */
	public static void writeToFile(Calendar today, JTextField employeeNamesField, List<JPanel> panels) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(today.getTime().toString(), "UTF-8");
		writer.write("Date: " + today.getTime().toString() + "\n\n"); // get the date
		writer.write("Employee(s): " + employeeNamesField.getText() + "\n\n"); // get the employees working/signed

		//Write all the descriptions and Comments to the file
		for(JPanel panel: panels) {
			writer.write(((JTextArea)panel.getComponent(0)).getText() + ":\t\t" + 
				((JTextArea)panel.getComponent(1)).getText() + ".\n\n");
		}

		writer.close();
	}
}

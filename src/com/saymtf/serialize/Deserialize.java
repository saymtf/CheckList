package com.saymtf.serialize;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Deserialize {
	List<JPanel> panels;
	
	public Deserialize() {
		panels = new ArrayList<JPanel>();
	}
	
	@SuppressWarnings("unchecked")
	public void deserialize() {
		try
		{
			FileInputStream fileIn = new FileInputStream("/Users/mitchellfenton/Documents/employee.ser"/*"/Users/mitchellfenton/Documents/workspace/RollicksCheckList/employeeList.ser"*/);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			panels = (ArrayList<JPanel>) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			System.out.println("The serialize is prob.. empty");
			return;
		}catch(ClassNotFoundException c)
		{
			System.out.println("JPanel class not found");
		return;
		}
	}
	
	public List<JPanel> getPanelList() {
		return panels;
	}
}

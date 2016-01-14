package com.saymtf.serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JPanel;

public class Serialize {

	public void serialize(List<JPanel> panels) {
		try {
			FileOutputStream fileOut = new FileOutputStream("/Users/mitchellfenton/Documents/employee.ser"/*"/Users/mitchellfenton/Documents/workspace/RollicksCheckList/employeeList.ser"*/);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(panels);
			out.close();
			fileOut.close();			
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

}

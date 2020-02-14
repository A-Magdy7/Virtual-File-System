import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Driver extends Unit
{	
	@Override
	public void show(String s) {
		System.out.println(s + "Driver : " + name);
		System.out.println();
	}

	@Override
	public void WriteToFile(Writer writer) throws IOException
	{
		writer.write(UnitType + " " + getUnits().size() + " " + name + " ");
	}
	
	@Override
	public void ReadFromFile(Scanner in) throws IOException
	{		
		name =in.next();
	}
	
	@Override
	public int getLocation() {
		// TODO Auto-generated method stub
		return -1;
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return -1;
	}

	public Driver() {
		UnitType = "Driver";
	}
}

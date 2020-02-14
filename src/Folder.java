import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Scanner;

public class Folder extends Unit
{
	private Date CreationDate;
	private Date LastModificationDate;

	@Override
	public void show(String s) {
		System.out.println(s + "Folder : " + name);
		System.out.println(s + "CreationDate : " + CreationDate.toString());
		System.out.println(s + "LastModificationDate : " + LastModificationDate.toString());
		System.out.println();
	}
	
	@Override
	public void WriteToFile(Writer writer) throws IOException
	{
		writer.write(UnitType + " " + getUnits().size() + " " + name + " " + CreationDate.getTime() 
						+ " " + LastModificationDate.getTime() + " ");
	}
	
	@Override
	public void ReadFromFile(Scanner in) throws IOException
	{
		long date;
		
		name =in.next();
		date = in.nextLong();
		CreationDate = new Date(date);
		date = in.nextLong();
		LastModificationDate = new Date(date);
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

	public Folder() {
		UnitType = "Folder";
	}
		
	public Date getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(Date creationDate) {
		CreationDate = creationDate;
	}

	public Date getLastModificationDate() {
		return LastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		LastModificationDate = lastModificationDate;
	}
}

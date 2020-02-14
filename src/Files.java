import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Scanner;

public class Files extends Unit
{
	private String type;
	private int location;
	private int size;
	private Date CreationDate;
	private Date LastModificationDate;

	@Override
	public void show(String s) {
		System.out.println(s + "File : " + name);
		System.out.println(s + "Type : " + type);
		System.out.println(s + "Size : " + size);
		System.out.println(s + "CreationDate : " + CreationDate.toString());
		System.out.println(s + "LastModificationDate : " + LastModificationDate.toString());
		System.out.println();
	}
	
	@Override
	public void WriteToFile(Writer writer) throws IOException
	{
		writer.write(UnitType + " " + name + " " + type + " " + location + " " + size + " " + CreationDate.getTime()
						+ " " + LastModificationDate.getTime() + " ");
	}
	
	@Override
	public void ReadFromFile(Scanner in) throws IOException
	{
		long date;
		
		name =in.next();
		type = in.next();
		location = in.nextInt();
		size = in.nextInt();
		date = in.nextLong();
		CreationDate = new Date(date);
		date = in.nextLong();
		LastModificationDate = new Date(date);
	}
	
	@Override
	public void addUnit(Unit unit) {
		System.out.println("This is cannot have Units");
	}
	
	@Override
	public boolean deleteUnit(String name, String type) {
		System.out.println("This is cannot have Units");
		return false;
	}
	
	public Files() {
		UnitType = "File";
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	@Override
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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

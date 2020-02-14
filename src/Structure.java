import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class Structure
{
	private Unit head;
	
	public Unit getHead()
	{
		return head;
	}

	public void setHead(Unit head)
	{
		this.head = head;
	}

	public boolean add (ArrayList<String> path, int ind, Unit cur, Unit NewUnit)
	{
		ArrayList<Unit> children = cur.getUnits();

		if(path.size()==ind)
		{
			for(Unit x : children)
				if(x.getUnitType().equals(NewUnit.getUnitType()) && x.getName().equals(NewUnit.getName()))
					return false;
			
			cur.addUnit(NewUnit);
			
			return true;
		}
		
		for(Unit x : children)
			if(x.getUnitType().equals("Folder") && x.getName().equals(path.get(ind)))
				return add(path, ind+1, x, NewUnit);
		
		return false;
	}
	
	public boolean delete (ArrayList<String> path, int ind, Unit cur, String name, String type)
	{
		if(path.size()==ind)
			return cur.deleteUnit(name, type);
		
		ArrayList<Unit> children = cur.getUnits();

		for(Unit x : children)
			if(x.getUnitType().equals("Folder") && x.getName().equals(path.get(ind)))
				return delete(path, ind+1, x, name, type);
		
		return false;
	}
	
	public int Get (ArrayList<String> path, int ind, Unit cur, String name, int t)
	{
		ArrayList<Unit> children = cur.getUnits();

		if(path.size()==ind)
		{
			for(Unit x : children)
				if(x.getUnitType().equals("File") && x.getName().equals(name))
					return t==0 ? x.getLocation() : x.getSize();
			
			return -1;
		}
		
		for(Unit x : children)
			if(x.getUnitType().equals("Folder") && x.getName().equals(path.get(ind)))
				return Get(path, ind+1, x, name, t);
		
		return -1;
	}
		
	public void DisplayDiskStructure(Unit cur, int cnt)
	{
		String s = "";
		
		for(int i=0 ; i<cnt ; i++)
			s += "	";
		
		cur.show(s);
		
		if(cur.getUnitType().equals("File"))
			return ;
		
		ArrayList<Unit> children = cur.getUnits();
		
		for(Unit x : children)
			DisplayDiskStructure(x,cnt+1);
	}
	
	public void Save(Unit cur, Writer writer) throws IOException
	{
		cur.WriteToFile(writer);
		ArrayList<Unit> children = cur.getUnits();

		for(Unit x : children)
			Save(x, writer);
	}
	
	public void Load(Unit cur, int sz, Scanner in) throws IOException
	{
		String type;
		int Nsz;
		
		while (sz-- > 0)
		{
			type = in.next();
			
			if(type.equals("File"))
			{
				Files file = new Files();
				file.ReadFromFile(in);
				
				cur.addUnit(file);
			}
			
			else if(type.equals("Folder"))
			{
				Folder folder = new Folder();
				Nsz = in.nextInt();
				folder.ReadFromFile(in);
				
				cur.addUnit(folder);
				Load(folder, Nsz, in);
			}
			
			else 
			{
				Nsz = in.nextInt();
				type = in.next();
				Load(cur, Nsz, in);
			}
		}
	} 
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Disk
{
	private Structure struct = new Structure();
	private String Fname;
	private Memory mem;
	
	public Disk()
	{
		Fname = "Disk1.txt";
		Driver drv = new Driver();
		drv.setName("VFSD");
		struct.setHead(drv);
	}
	
	public void setAlg(int alg)
	{		
		if (alg == 1)
			mem = new ContiguousMemory();
		
		else if (alg == 2)
			mem = new LinkedMemory();

		else if (alg == 3)
			mem = new IndexedMemory();		
	}

	public boolean execute (String command)
	{
		if(command.equals("DisplayDiskStatus"))
		{
			mem.DisplayDiskStatus();
			return true;
		}
		
		else if (command.equals("DisplayDiskStructure"))
		{
			struct.DisplayDiskStructure(struct.getHead(), 0);
			return true;
		}
		
		String s = "";
		
		for(int i=0 ; i<command.length() && command.charAt(i)!=' ' ; i++)
			s += command.charAt(i);
		
		command = command.substring(s.length()+1);
		
		switch (s)
		{
		case "CFile":
			return AddFile(command);
			
		case "CFolder":
			return AddFolder(command);

		case "DeleteFile":
			return DeleteFile(command);

		case "DeleteFolder":
			return DeleteFolder(command);
			
		default:
			return false;
		}
	}
	
	public boolean AddFile (String command)
	{
		String name="", type="", size="";
		int loc, sz;
		ArrayList<String> path;
		
		for(int i=0 ; i<command.length() && command.charAt(i)!=' ' ; i++)
			name += command.charAt(i);
		command = command.substring(name.length()+1);
		
		for(int i=name.length()-1 ; name.charAt(i)!='.' ; i--)
			type = name.charAt(i) + type;
		
		for(int i=0 ; i<command.length() && command.charAt(i)!=' ' ; i++)
			size += command.charAt(i);
		command = command.substring(size.length()+1);
		
		path = GetPath(command);
		
		Files file = new Files();
		
		sz = Integer.parseInt(size);
		loc = mem.add(sz);
		
		file.setName(name);
		file.setLocation(loc);
		file.setSize(sz);
		file.setType(type);
		file.setCreationDate(new Date());
		file.setLastModificationDate(new Date());
		
		if(loc!=-1)
		{
			if(struct.add(path, 0, struct.getHead(), file))
				return true;
			
			mem.remove(loc, sz);
			return false;
		}
		
		return false;
	}
	
	public boolean AddFolder (String command)
	{
		String name="";
		ArrayList<String> path;
		
		for(int i=0 ; i<command.length() && command.charAt(i)!=' ' ; i++)
			name += command.charAt(i);
		command = command.substring(name.length()+1);
		
		path = GetPath(command);
		
		Folder folder = new Folder();
		
		folder.setName(name);
		folder.setCreationDate(new Date());
		folder.setLastModificationDate(new Date());
		
		return struct.add(path, 0, struct.getHead(), folder);
	}
	
	public boolean DeleteFile (String command)
	{
		int loc, sz;
		
		String name="";
		ArrayList<String> path;
		
		for(int i=command.length()-1 ; command.charAt(i)!='\\' ; i--)
			name = command.charAt(i) + name;
		
		command = command.substring(0,command.length()-name.length()-1);
		
		path = GetPath(command);
		
		loc = struct.Get(path, 0, struct.getHead(), name, 0);
		sz = struct.Get(path, 0, struct.getHead(), name, 1);
		
		if(!struct.delete(path, 0, struct.getHead(), name, "File"))
			return false;
		
		mem.remove(loc, sz);
			
		return true;
	}
	
	public boolean DeleteFolder (String command)
	{
		String name="";
		ArrayList<String> path;
		
		for(int i=command.length()-1 ; command.charAt(i)!='\\' ; i--)
			name = command.charAt(i) + name;
		
		command = command.substring(0,command.length()-name.length()-1);
		
		path = GetPath(command);
		
		return struct.delete(path, 0, struct.getHead(), name, "Folder");
	}
	
	public ArrayList<String> GetPath (String path)
	{
		ArrayList<String> p = new ArrayList<>();
		String s="";
				
		path = path.equals("VFSD:") ||  path.equals("VFSD:\\")? "" : path.substring(6);
				
		for(int i=0 ; i<path.length() ; i++)
		{
			if(path.charAt(i)=='\\')
			{
				p.add(s);
				s="";
			}
			else
				s += path.charAt(i);
		}
		
		if(!s.equals(""))
			p.add(s);
				
		return p;
	}
	
	public void Save() throws IOException
	{
		Writer writer = new FileWriter(new File(Fname));		
		struct.Save(struct.getHead(), writer);
		mem.Save(writer);
		writer.close();
	}
	
	public void Load() throws IOException
	{
		Scanner in = new Scanner(new File(Fname));
		struct.Load(struct.getHead(), 1, in);
		int alg = in.nextInt();
		setAlg(alg);		
		mem.Load(in);
		in.close();
	} 

}

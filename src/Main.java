import java.io.IOException;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Disk disk = new Disk();
		Scanner in = new Scanner(System.in);
		String command = "";
		int alg;
				
		System.out.println("1-Contiguous Allocation\n2-Linked Allocation\n3-Indexed Allocation");
		alg = in.nextInt();
		disk.setAlg(alg);
		
		disk.Load();
		
		in.nextLine();
		
		while (true)
		{
			command = in.nextLine();
			
			if(command.equals("Exit"))
				break;
			
			if(disk.execute(command))
				System.out.println("Executed");
			
			else
				System.out.println("Invalid Command");
		}
		
		disk.Save();
		
		in.close();
	}

}

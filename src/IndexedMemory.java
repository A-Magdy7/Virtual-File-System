import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class OSlot
{
	int start;
	
	ArrayList<Integer> slots = new ArrayList<>();
}

public class IndexedMemory implements Memory
{
	int TSize, Size;

	ArrayList<Integer> Eslots = new ArrayList<>();
	ArrayList<OSlot> Oslots = new ArrayList<>();
	
	public IndexedMemory()
	{
		TSize = Size = 1000000;

		for(int i=0 ; i<TSize ; i++)
			Eslots.add(i);
	}
	
	@Override
	public int add(int size) 
	{
		if(size > Size)
			return -1;
		
		OSlot Oslot = new OSlot();
		
		Oslot.start = Eslots.get(0);
		
		for(int i=0 ; i<size ; i++)
		{
			Oslot.slots.add(Eslots.get(0));
			Eslots.remove(0);
		}
		
		Oslots.add(Oslot);
		Size -= size;
			
		return Oslot.start;
	}
	
	@Override
	public void remove(int loc, int sz) 
	{
		for(int i=0 ; i<Oslots.size() ; i++)
		{
			if(Oslots.get(i).start == loc)
			{
				for(int j : Oslots.get(i).slots)
					Eslots.add(j);
				
				Oslots.remove(i);
				break;
			}
		}
		Size += sz;
		
		Collections.sort(Eslots);
	}

	@Override
	public void Save(Writer writer) throws IOException 
	{
		writer.write(3 + " " + Eslots.size() + " " + Size + " ");
		
		for(int i : Eslots)
			writer.write(i + " ");
		
		writer.write(Oslots.size() + " ");
		
		for(OSlot i : Oslots)
		{
			writer.write(i.start + " " + i.slots.size() + " ");
			
			for(int j : i.slots)
				writer.write(j + " ");
		}
	}

	@Override
	public void Load(Scanner in) throws IOException
	{
		Eslots = new ArrayList<>();
		Oslots = new ArrayList<>();
		
		int sz = in.nextInt(), x, y;
		OSlot slot;
		
		Size = in.nextInt();
		
		while (sz-- > 0)
		{
			x = in.nextInt();
			
			Eslots.add(x);
		}
		
		sz = in.nextInt();
		
		while (sz-- > 0)
		{
			slot = new OSlot();
			
			slot.start = in.nextInt();
			
			x = in.nextInt();
			
			while (x-- > 0)
			{
				y = in.nextInt();
				
				slot.slots.add(y);
			}
			
			Oslots.add(slot);
		}
	}

	@Override
	public void DisplayDiskStatus()
	{
		int ESize = TSize-Size, s, sz=1;
		
		System.out.println("Empty Space : " + ESize);
		System.out.println("Occupied Space : " + Size);
		System.out.println();
		
		s=Eslots.get(0);
		
		for(int i=1 ; i<Eslots.size() ; i++)
		{
			if(Eslots.get(i)-1 != Eslots.get(i-1))
			{
				System.out.println("Start : " + s + "\nSize : " + sz + "\n");
				
				s = Eslots.get(i);
				sz = 1;
			}
			
			else
				sz++;
		}	
		
		System.out.println("Start : " + s + "\nSize : " + sz + "\n");
	}	
}

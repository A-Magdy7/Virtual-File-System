import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Slot
{
	int Start, Size;
}

public class ContiguousMemory implements Memory
{
	int TSize, Size;
	ArrayList<Slot> slots = new ArrayList<>();
	
	public ContiguousMemory() 
	{
		Slot slot = new Slot();
		
		slot.Start = 0;
		TSize=slot.Size = 1000000;
		Size = 0;
		
		slots.add(slot);
	}
	
	@Override
	public int add(int size) 
	{
		int loc =-1;
				
		for(int i=0 ; i<slots.size() ; i++)
			if(slots.get(i).Size >= size)
			{
				Size += size;
				loc = slots.get(i).Start;
				slots.get(i).Size -= size;
				slots.get(i).Start += size;
				
				if(slots.get(i).Size == 0)
					slots.remove(i);
			}
		
		return loc;
	}
	
	@Override
	public void remove(int loc, int sz) 
	{
		Slot slot = new Slot();
		
		slot.Start = loc;
		slot.Size = sz;

		slots.add(slot);
		Sort();
		
		for(int i=0 ; i<slots.size()-1 ; i++)
		{
			loc = slots.get(i).Start;
			sz = slots.get(i).Size;
			
			if(loc+sz == slots.get(i+1).Start)
			{
				slots.get(i).Size += slots.get(i+1).Size;
				slots.remove(i+1);
				i--;
			}
		}
		Size -= sz;
	}
	
	@Override
	public void Save(Writer writer) throws IOException
	{
		writer.write(1 + " " + slots.size() + " ");
		
		for(int i=0 ; i<slots.size() ; i++)
			writer.write(slots.get(i).Start + " " + slots.get(i).Size + " ");
	}
	
	@Override
	public void Load(Scanner in) throws IOException
	{
		int sz = in.nextInt();
		Slot slot;
		
		slots = new ArrayList<>();
		
		while (sz-- != 0)
		{
			slot = new Slot();
			
			slot.Start = in.nextInt();
			slot.Size = in.nextInt();
			
			Size += slot.Size;
			
			slots.add(slot);
		}
	}

	@Override
	public void DisplayDiskStatus() 
	{
		int ESize = TSize-Size;
		
		System.out.println("Empty Space : " + ESize);
		System.out.println("Occupied Space : " + Size);
		System.out.println();

		for(int i=0 ; i<slots.size() ; i++)
			System.out.println("Start : " + slots.get(i).Start + "\nSize : " + slots.get(i).Size + "\n");
	}
	
	private void Sort()
	{
		Collections.sort(slots, new Comparator<Slot>() 
		{
			@Override
			public int compare(Slot o1, Slot o2) 
			{
				return o1.Start>o2.Start ? 1 : (o1.Start<o2.Start ? -1 : 0);
			}
		});
	}
}

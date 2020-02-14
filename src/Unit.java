import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Unit 
{
	private ArrayList<Unit> units;
	protected String UnitType;
	protected String name;
	
	public abstract void show(String s);
	public abstract void WriteToFile(Writer writer) throws IOException;
	public abstract void ReadFromFile(Scanner in) throws IOException;
	public abstract int getSize();
	public abstract int getLocation();

	
	public Unit(){
		units = new ArrayList<>();
	}
	
	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void addUnit(Unit unit) {
		units.add(unit);
	}
	
	public boolean deleteUnit(String name, String type) {
		int ind = -1;
		
		for(int i=0 ; i<units.size() && ind==-1 ; i++)
			if(units.get(i).UnitType.equals(type) && units.get(i).name.equals(name))
				ind = i;
		
		if(ind == -1)
			return false;
		
		units.remove(ind);
		
		return true;
	}

	public String getUnitType() {
		return UnitType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

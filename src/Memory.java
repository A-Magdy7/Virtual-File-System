import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public interface Memory
{
	public abstract int add (int size);
	
	public abstract void remove (int loc, int sz);
	
	public void Save (Writer writer) throws IOException;
	
	public void Load(Scanner in) throws IOException;
	
	public void DisplayDiskStatus ();
}

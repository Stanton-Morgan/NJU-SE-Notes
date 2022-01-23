import java.io.*;
import static java.lang.System.out;
public class CSVFile {

	public static void main(String[] args) {
		String filePath = CSVFile.class.getClassLoader().getResource("data.txt").getPath();
		printCSVFile(filePath);
	}
	
	public static void printCSVFile(String filePath) {
		out.println("Last    Fisrt    Salary");
		try {
			File file = new File(filePath);
			BufferedReader f = null;
			String tempString = null;
			f = new BufferedReader(new FileReader(file));
			while ((tempString = f.readLine()) != null) {
				String[] str = tempString.split(",");
				out.println(String.join("    ",str));
			}
			f.close();
		} catch (IOException e) {
			out.println("no file");
		}

	}
}

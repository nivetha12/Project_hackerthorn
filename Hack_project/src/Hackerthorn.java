import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import org.json.simple.JSONObject;
import java.sql.*;
import java.lang.*;
import java.text.DecimalFormat;
public class Hackerthorn {
	static BufferedReader br = null;	
	static int itr = 0;
	static String some;
	
	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();
		DecimalFormat df=new DecimalFormat("#0.00");
		double sum=0;
		double avg=0;
		double max=0;
		int lineno = 0;
		String line;
		Connection myConn = null;
		Statement myStmt = null;

		try {
			br = new BufferedReader(new FileReader("C:\\Users\\RDX\\Desktop\\memory.txt"));
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "project" , "project");
			System.out.println("Database connected");
			myStmt = myConn.createStatement();
			PreparedStatement pStmt = myConn.prepareStatement("INSERT into sample (transactionname, average, maximum) values(?,?,?)");
			while ((line = br.readLine()) != null) {
				if(lineno%2!=0)
				{String linetrim =line.trim();
					String[] memory = linetrim.split(":|\\ ");
					for(String l:memory)
						System.out.println(l);
					System.out.println("length"+memory.length);
					System.out.println("reqval"+memory[4]);
					
					Double reqmem = Double.parseDouble(memory[4].toString());
					double mbmem= reqmem/(double)1024;
					if (max<mbmem)
					{
						max = mbmem;
					}
					sum+=mbmem;
					StringBuilder sb = new StringBuilder();
					sb.append(itr + "s " + reqmem);
					itr++;
					some = itr + "s";
					obj.put(some, df.format(mbmem));

					System.out.println(sb.toString());
				}
				avg=(double)sum/(double)itr;
					
					lineno++;
				}
			obj1.put("CpuAvg:",df.format(avg));
			obj1.put("CpuMax:",df.format(max));
			obj1.put("values:",obj);
			obj1.put("usecasename:","sample");

			System.out.println(obj1);
			pStmt.setString(1,"sampletransaction");
			pStmt.setString(2,df.format(avg));
			pStmt.setDouble(3,max);
			pStmt.execute();
			FileWriter file = new FileWriter("C:\\Users\\RDX\\Desktop\\output_sample.json");
			file.write(obj1.toJSONString());
			file.flush();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	
	
	



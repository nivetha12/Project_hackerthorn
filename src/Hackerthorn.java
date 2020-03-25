import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import org.json.simple.JSONObject;
import java.sql.*;
import java.text.DecimalFormat;
public class Hackerthorn {
	static BufferedReader br = null;	
	static int itr = 0;
	static String some;
	
	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		DecimalFormat df=new DecimalFormat("#0.00");
		double sum=0;
		double avg=0;
		double max=0;
		String val="";
		String line;
		int i=0;
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			br = new BufferedReader(new FileReader("C:\\Users\\RDX\\Desktop\\input.txt"));
			
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "project" , "project");
//			System.out.println("Database connected");
			myStmt = myConn.createStatement();
			PreparedStatement pStmt = myConn.prepareStatement("INSERT into analysis (transactionname, average, maximum) values(?,?,?)");
			while ((line = br.readLine()) != null) {
				StringTokenizer stringTokenizer = new StringTokenizer(line, " ");

				while (stringTokenizer.hasMoreElements()) {

					int x = 0;
					while (x < 8) {
						stringTokenizer.nextElement().toString();
						x++;
					}

//					required line
					Double reqCPU = Double.parseDouble(stringTokenizer.nextElement().toString());
					if(max<reqCPU)
						max=reqCPU;
					sum+=reqCPU;
					while (x < 11) {
						stringTokenizer.nextElement().toString();
						x++;
					}

					StringBuilder sb = new StringBuilder();
					sb.append(itr + "s " + reqCPU);
					itr++;
					some = itr + "s";
					obj.put(some, reqCPU);

//					System.out.println(sb.toString());
				}

			}
			avg=(double)sum/(double)itr;
			

			obj.put("CpuAvg:",df.format(avg));
			obj.put("CpuMax:",max);
//			System.out.println(obj);
			System.out.println("max:"+max);
			System.out.println("avg:"+avg);
			pStmt.setString(1,"Transaction 1");
			pStmt.setString(2,df.format(avg));
			pStmt.setDouble(3,max);
			pStmt.execute();
			FileWriter file = new FileWriter("C:\\Users\\RDX\\Desktop\\output.json");
			file.write(obj.toJSONString());
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

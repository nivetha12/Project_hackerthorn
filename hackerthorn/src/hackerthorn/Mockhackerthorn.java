package hackerthorn;
import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import org.json.simple.JSONObject;


public class Mockhackerthorn {
	static BufferedReader br = null;
	static int itr = 0;
	static String some;

	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();
		DecimalFormat df=new DecimalFormat("#0.00");
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		double sum=0;
		double avg=0;
		double max=0;

		try {
			String line;
			br = new BufferedReader(new FileReader("C:\\Users\\RDX\\Desktop\\input.txt"));
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "project" , "project");
			System.out.println("Database connected");
			myStmt = myConn.createStatement();
			PreparedStatement pStmt = myConn.prepareStatement("INSERT into cpu (transactionname, average, maximum) values(?,?,?)");
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
					while (x < 11) {
						stringTokenizer.nextElement().toString();
						x++;
					}
					if (max<reqCPU)
					{
						max = reqCPU;
					}
					sum+=reqCPU;
					StringBuilder sb = new StringBuilder();
					sb.append(itr + "s " + reqCPU);
					itr++;
					some = itr + "s";
					obj.put(some, reqCPU);

					System.out.println(sb.toString());
				}

			}
			avg=(double)sum/(double)itr;
			obj1.put("CpuAvg:",df.format(avg));
			obj1.put("CpuMax:",df.format(max));
			obj1.put("values:",obj);
			obj1.put("usecasename:","sampleTransaction");
			System.out.println(obj1);
			pStmt.setString(1,"sampletransaction");
			pStmt.setString(2,df.format(avg));
			pStmt.setDouble(3,max);
			pStmt.execute();
			FileWriter file = new FileWriter("C:\\Users\\RDX\\Desktop\\output.json");
			file.write(obj1.toJSONString());
			file.flush();
			myRs = myStmt.executeQuery("select * from cpu");
			myRs.absolute(3);  
			String name= myRs.getString(1);
			String average = myRs.getString(2);
			String maximum= Integer.toString(myRs.getInt(3));
			String table = "<html><head><title> CPU Values</title></head><body><table border= 1><tr><th>TRANSACTION</th><th>AVERAGE</th><th>MAXIMUM</th></tr><tr><td>"+name+"</td><td>"+average+"</td><td>"+maximum+"</td></tr></table></body></html>";
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\RDX\\Desktop\\cpuhtml.html"));
			bw.write(table);
			bw.close();
			myConn.close();
			
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


package cms_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.net.aso.s;

public class cmsdb {
	//전역 변수들 선언
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost";
	static String user = "scott";
	static String password = "tiger";
	
	static Connection conn = null;

	static int cl_id = 0;
	static String cl_name = null;
	static String cl_gender = null;
	static String cl_email = null;
    static int cl_birthyear = 0;
    
	public static void main(String[] args) {
		conn = MakeConnection.getConnection();
		System.out.println("conn: " + conn);
 
		Scanner sc = new Scanner(System.in);
		System.out.println("이름을 입력하세요:");
		cl_name = sc.next();
		System.out.println("성별을 입력하세요(M/F):");
		cl_gender = sc.next();
		System.out.println("이메일을 입력하세요:");
		cl_email = sc.next();
		System.out.println("생년을 입력하세요:");
		cl_birthyear = Integer.parseInt(sc.next().replaceAll("[^0-9]", ""));
		
		insertEx(conn, cl_name, cl_gender, cl_email, cl_birthyear);
		
		//cmd로 실행 시 매개변수(args[])로 받아온 고객 정보로 insert 시행
		/*
		insertEx(conn, args[0], args[1], args[2], Integer.parseInt(args[3].replaceAll("[^0-9]", "")));
		*/
		
		String sql = "select * from client_info ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.print(rs.getInt(1));
				System.out.print(", ");
				System.out.print(rs.getString(2));
				System.out.print(", ");
				System.out.print(rs.getString(3));
				System.out.print(", ");
				System.out.print(rs.getString(4));
				System.out.print(", ");
				System.out.print(rs.getInt(5));
				System.out.print(", ");
				System.out.print(rs.getString(6));
				System.out.print(", ");
				System.out.print(rs.getString(7));
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("완료.");
	}
	
	//insertEx 메서드 정의
	public static void insertEx(Connection conn, String cl_name, String cl_gender, String cl_email, int cl_birthyear) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into client_info(cl_id, cl_name, cl_gender, cl_email, cl_birthyear) ");
		sb.append("values(cl_seq.nextval, ?, ?, ?, ?)");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, cl_name);
			pstmt.setString(2, cl_gender);
			pstmt.setString(3, cl_email);
			pstmt.setInt(4, cl_birthyear);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

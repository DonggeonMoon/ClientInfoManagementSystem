package cms_sql;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;
 
public class Client_Info {
	
		static Scanner scan = new Scanner(System.in);
		static int nNum = 0;
		static int rowcount = 0;
		
		public static void main(String[] args) throws Exception {
			
			while(true) {
				
				String str = Client_Info_view();
				
				switch(str) {
					case "I" : //신규 고객 저장
							InsertEx();
							System.out.println("고객이 저장되었습니다.");
							break;
							
					case "S" : //고객 검색
							SearchEx();
							break;
							
					case "P" : //이전 고객 검색
							if (nNum==0 || nNum-1 <= 0) {
								System.out.println("찾으시는 이전 고객은 없습니다.");
								}
							else {
								 prev_SearchEx("P");
							}
							break;
							
					case "N": //다음 고객 검색
							prev_SearchEx("N");
							break;
							
					case "D" :
							if (rowcount > 1 ) {
								System.out.println("다수의 고객이 선택되었습니다. 한 고객만 선택해주세요.");
								} else {
									if (nNum == 0||nNum <=0) {
										System.out.println(" 삭제하려는 고객이 없습니다.");
										}else {
											DeleteEx();
										}
								}
							break;
					case "U" : 
						   if (rowcount > 1 )System.out.println(" 다수의 고객이 선택되었습니다. 한 고객만 선택해주세요.");
						   else {
							   if (nNum == 0 || nNum <=0 ) {
								   System.out.println("수정하고자 하는 고객이 없습니다.");
								   }							 
							   else {
								   UpdateEx(); System.out.println("고객 정보를 수정하였습니다.");
								   } 
							   }
						   break;
					case "Q" :	
						 System.out.println("프로그램을 종료합니다.");
						 System.exit(0);
							break;
							
					default : 
							break;
				}
			}
		}
		
		public static String Client_Info_view() {//Client_Info의 메뉴 생성
			System.out.println("****************************************");
			System.out.println(" 	고객정보 UI 화면입니다. ");
			System.out.println(" 	고객정보 입력 : I   " );
			System.out.println(" 	고객정보 조회 : S   " );
			System.out.println(" 	이전고객 조회 : P   " );
			System.out.println(" 	이후고객 조회 : N   " );
			System.out.println(" 	고객정보 수정 : U   " );
			System.out.println(" 	고객정보 삭제 : D   " );
			System.out.println(" 	프로그램 종료 : Q   " );
		  	System.out.println("******************************************");
		  	
		  	String str = scan.next().toUpperCase().trim(); // 해당값을 무조건 대문자로 바꾸어준다.
		  	boolean chk = false;
		    chk = menuCheck(str);
		    
	       if (chk == false) {
				System.out.println("작업 메뉴에 없는 호출정보입니다. 다시 입력해주세요.");				
				Client_Info_view(); //재귀함수
	       }
	       return str;
		}
		
		public static boolean menuCheck(String str) {//입력받은 메뉴 실행
			  	//null check / 메뉴 범위에 있는 값들인지/ check 하여 true 혹은 false을 반환한다.
			  	if (str == null||str == ""|| str.length() >2|| str.length()<= 0) {
			  		return false;
			  	}
			  	String arr[] = {"I", "S", "D", "U", "Q", "P", "N"};
			  	
			    if (str.length() == 1) {
			    	
				  	for (int i = 0; i < arr.length; i++) {
						if (str.equals(arr[i])) {
							return true;
							}						 
				  	}
				  	return false;
			    }
				return false;
		}
		
		private static void InsertEx() {
			//고객의 정보를 수정합니다.
			 
			Connection conn = MakeConnection.getConnection();//MakeConnection 클래스에서 생성한 getConnection() 메서드를 이용해 접속.
			
			String tName ="";
			String tGender ="";
			String tBirthyear ="";
			String tEmail= "";
			
			System.out.println(" 고객 정보를 기입해주세요");
			System.out.print(" 고객 이름  : ");
			tName = scan.nextLine();	//해당값을 받아주어야 한다.
			tName=  scan.nextLine();
			
			while (tName.isEmpty()) {
				System.out.println(" 잘못된 정보입니다. 다시 입력해주세요. ");
				System.out.print(" 고객이름 : ");
				tName = scan.nextLine();  			 
			}
			tName= tName.toLowerCase();
			
		    System.out.print(" 성별 (M/F) : ");					 
			tGender = scan.nextLine();
				      
				     //성별체크하자
		     while  (!check_Gender(tGender)  || tGender.isEmpty()){
		    	System.out.println(" 잘못된 정보입니다. 다시 입력해주세요. ");
	    		System.out.print(" 성별 (M/F) : ");
	    	    tGender = scan.nextLine();
		     }
			 tGender= tGender.toUpperCase().trim();					
		
			 
			 System.out.print(" 생년월일 : ");		
			  tBirthyear = scan.nextLine(); 
					  
			// System.out.println( check_Birthyeakr(tBirthyear));
			while (! check_Birthyeakr(tBirthyear) || tBirthyear.isEmpty()) {
				System.out.println(" 잘못된 정보입니다. 다시 입력해주세요. ");
				System.out.print(" 생년월일 : ");
				tBirthyear = scan.nextLine();
			
			}
			
									
			System.out.print(" 이메일: ");
			tEmail =scan.nextLine(); 
				  
			while (tEmail.isEmpty()) {
				System.out.println(" 잘못된 정보입니다. 다시 입력해주세요. ");
				System.out.print(" 이메일: ");
				tEmail =scan.nextLine();  
			} tEmail  = tEmail.toLowerCase();
					
					 
					
			PreparedStatement pstmt = null;
			StringBuffer sb = new StringBuffer();
			
			sb.append(" insert into client_info ");
			sb.append("  ( cl_id, cl_name , cl_gender, cl_email, cl_birthyear  )" );
			sb.append(" values (cl_seq.nextval, ? , ?, ? , ?  )"); 
								 
				
			try {
				pstmt = conn.prepareStatement(sb.toString());
			 
				pstmt.setString(1, tName.trim());
				pstmt.setString(2, tGender.trim());
				pstmt.setString(3, tEmail.trim());
				pstmt.setString(4, tBirthyear.trim());				 		
				pstmt.executeUpdate(); 
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					pstmt.close();
					conn.close();
					 
				}catch(SQLException e) {
					e.printStackTrace();
				}
			} 
			
			
		}
		
		private static boolean check_Birthyeakr(String tBirthyear) {
			
			if (tBirthyear.trim().length()==8){ 			return true; }
			return false;
		}
		
		
		//Delete메소드
		private static void DeleteEx() {
			
			Connection conn = MakeConnection.getConnection();
			StringBuffer sb = new StringBuffer();
			
			if (nNum == 0 || nNum <=0 ) {System.out.println(" 삭제하고자 하는  고객이 없습니다.");}
			
			else {
				
				prev_SearchEx("S");
				
				int answer = JOptionPane.showConfirmDialog(null, "해당고객을 정말로 삭제 하겠습니까?", "confirm",JOptionPane.YES_NO_OPTION );
				
				if(answer == JOptionPane.YES_OPTION){
					//사용자가 yes를 눌렀을 떄
				
					sb.append("delete client_info ");
					sb.append("where cl_id = ? ");
					PreparedStatement pstmt = null;
					//System.out.println(" 해당고객을 삭제 하였습니다.");
					
					try {
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setInt(1, nNum);
						pstmt.executeUpdate();
					}catch(SQLException e) {
						e.printStackTrace();
					}finally {
						try {
							pstmt.close();
							conn.close();
						}catch(SQLException e) { 	e.printStackTrace(); 	}
					   }
					
					
				} else{
					//사용자가 Yes 외 값 입력시
					System.out.println("해당고객을 삭제 하지 않겠습니다.");
				}
			}
		}
		
		private static void UpdateEx() {
			//고객의 정보를 수정합니다.
			 
			Connection conn = MakeConnection.getConnection();
			
			if (nNum == 0 || nNum <=0 ) {System.out.println("수정하고자 하는 고객이 없습니다.");}
			else { 
					prev_SearchEx("S");
					String tName ="";
					System.out.print(" 수정할 이름 : ");
					tName = scan.nextLine();	//해당값을 받아주어야 한다.
					tName=  scan.nextLine();
					
					while (tName.isEmpty()) {
						System.out.println("값이 없습니다.");
						System.out.print(" 수정할 이름 : ");
						tName = scan.nextLine();  
					 
					}
					
					
					 System.out.print(" 수정할 성별 (M/F):");					 
					 String tGender = "";
				     tGender = scan.nextLine();				  
					
					 while  (!check_Gender(tGender)  || tGender.isEmpty()){
					    	System.out.println(" 잘못된 정보입니다. 다시 입력해주세요. ");
				    		System.out.print(" 수정할 성별 (M/F) : ");
				    	    tGender = scan.nextLine();
					     }
					 tGender= tGender.toUpperCase().trim();					
					
						 
				  System.out.print(" 수정할 생년월일 : ");		
				   String tBirthyear = scan.nextLine(); 
								  
					 
					while (! check_Birthyeakr(tBirthyear) || tBirthyear.isEmpty()) {
						System.out.println(" 잘못된 정보입니다. 다시 입력해주세요. ");
						System.out.print(" 수정할 생년월일 : ");
						tBirthyear = scan.nextLine();
					
					}
						
					System.out.print(" 수정할 이메일 :");	
					String tEmail ="";
					  tEmail =scan.nextLine(); 
					  
					while (tEmail.isEmpty()) {
						System.out.println("값이 없습니다.");
						System.out.print(" 수정할 이메일 :");
						tEmail =scan.nextLine(); 
					
					}
					
					 
					
					PreparedStatement pstmt = null;
					StringBuffer sb = new StringBuffer();
					
					sb.append(" update client_info ");
					sb.append(" set cl_name = ? , cl_gender = ?, cl_email = ? , cl_birthyear =?,  cl_alter_date  = sysdate ");
					sb.append(" where cl_id = ? ");
								 
				
			try {
				pstmt = conn.prepareStatement(sb.toString());
			 
				pstmt.setString(1, tName.trim());
				pstmt.setString(2, tGender.trim());
				pstmt.setString(3, tEmail.trim());
				pstmt.setString(4, tBirthyear.trim());
				pstmt.setInt(5, nNum); 				
				pstmt.executeUpdate(); 
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					pstmt.close();
					conn.close();
					 
				}catch(SQLException e) {
					e.printStackTrace();
				}
			} 
			
		}
	}
		
		private static boolean check_Gender(String tGender) {
			tGender = tGender.toUpperCase().trim();
			if (tGender.length() == 1 ) {
				if ((tGender.equals("M")) || (tGender.equals("F")) ){
					return true;
				}
				return false;
			}
			return false;
			
		}
		
		private static void prev_SearchEx( String gubun) {
			// 이전 고객의 값을 가지고 온다
			Connection conn = MakeConnection.getConnection();
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			String sql = "";
			
		 	int cl_id=0;
			String cl_name ="";
			String cl_gender="";
			String cl_email ="";
			String cl_birthyear ="";
			String cl_Date ="";
			String cl_alter_date ="";
			
			if (nNum == 0 ) { //검색할 고객이 있는지 여부 CHECK
				System.out.println(" 고객이 없습니다. ");
			}
		 
			else
			{
				if (gubun.equals("P")) { nNum = nNum -1 ;} //이전
					
				if (gubun.equals("N")) { nNum = nNum +1 ;} //이후
				
				if (gubun.equals("S")) { nNum = nNum; }    //현재
				
			}
			
			 
				//이후 고객을 찾는다
					sql = " select cl_id, cl_name, cl_gender, cl_email,cl_birthyear, cl_date, cl_alter_date " + 
							"from client_info  "+
							"where cl_id = " + nNum  ;
					//System.out.println(sql);
					try {
						
						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();  // 
						
						//executeQuery(), ResultSet , Select
						if ( rs.isBeforeFirst() == false) {System.out.println(" 찾으시는 고객정보가 없습니다.");   }
						//rs.isBeforeFirst(); 
						while(rs.next()) {
							System.out.println(" 고객정보를 보여줍니다. ");
							
							cl_id = rs.getInt("cl_id");
							nNum = cl_id  ;
							
							cl_name = rs.getString("cl_name");
							cl_gender = rs.getString("cl_gender");
							cl_email = rs.getString("cl_email");
							cl_birthyear = rs.getString("cl_birthyear");
							cl_Date = rs.getString("cl_Date"); 
							cl_Date = cl_Date.substring(0,10);
														
							if  ( rs.getString("cl_alter_date") == null) {								
								  cl_alter_date  ="";
						    }else {
							  cl_alter_date =  rs.getString("cl_alter_date"); 
							  cl_alter_date = cl_alter_date.substring(0,10);
						    }
							 
								
							System.out.print("  고객명 :" + cl_name);
						 	System.out.print(", 성 별   :" + cl_gender);							
							System.out.print(", 이메일 :" + cl_email);
							System.out.print(", 생년월일 :" + cl_birthyear);
							System.out.print(", 등록일자 :" + cl_Date);
							System.out.print(", 고객정보 수정일자 :" + cl_alter_date);						
							System.out.println();
						}
						
					}catch(SQLException e) {
						e.printStackTrace();
					}finally {
						try{
							rs.close();
							pstmt.close();
							conn.close();
						}catch(SQLException e) {
							e.printStackTrace();
						} 
					
					}
		 
	}//해당 클래스


		////////////////////Condition SEARCH  START /////////////////////////////////////////
		private static void SearchEx() {
			String sql ="";
		 		
			 
			Connection conn = MakeConnection.getConnection();
			ResultSet rs = null;
			PreparedStatement pstmt = null;			
			rowcount=0;		
		
			//조회항목 선택 여부 chk 그래서 다시 호출 할 수 있도록 구현하자
			boolean tFalse =true;
			String input_value ="";
			while(tFalse){
				
				System.out.println(" 조회항목을 선택하세요. 이름 :1, 생년월일:2, 남/여 :3,  전체고객 :4 ");
				 
				input_value = scan.next(); 
				  
			 
				if (input_value==null || input_value.isEmpty()) {
				 	tFalse =true;
				} else {
					tFalse = Item_Check(input_value);
					if (tFalse == true) System.out.println("조회항목이 아닙니다. 다시 선택해 주세요"); 
				
				}		 
								 
			}			
			
			 	sql = "select cl_id, cl_name, cl_gender, cl_email ,  cl_birthyear , cl_date, "
						+ "	cl_alter_Date  from client_info  " ;
			 	
			 boolean tFalse2 = true;
			 String tData = "";
			 while(tFalse2) {
				 	if (input_value.equals("1")) {System.out.print(" 검색을 원하는 이름을 입력해주세요.: ");}
				 	else if (input_value.equals("2")) {System.out.print(" 검색을 원하는 생년월일을  입력해주세요.(ex:19800520):");}
				 	else if (input_value.equals("3")) {System.out.print(" 검색을 원하는 성별을 입력해주세요.(ex:M,F) :");}
				 	else if (input_value.equals("4")) {System.out.print(" 전체를 검색합니다.:");}
				 	else {System.out.println(" 정확한 입력을 요구합니다. "); tFalse2 = true;  }
				 	
				 	if (!input_value.equals("4")) {
						 	tData = scan.next();
						 	tData = tData.trim(); 
						 
						   if (tData.isEmpty()|| (tData.toString()==""))  { tData = scan.next() ; tFalse2 = true; }
						   else { tFalse2 = false;} 
				 	}
				 	
				 	tFalse2 = false;
				 	 
			 }
			 
			 
			
		  	if (input_value.equals("1") ) { //이름으로 검색
		  	   sql =  sql + " where  cl_name like '" + tData.toLowerCase() +"%'" ;
					  		
		  	}else if (input_value.equals("2")) {//생년월일로 검색
		  		 	 sql =  sql + " where NVL(cl_birthyear, 'A') like '" + tData.toLowerCase() +"%'" ; //이메일은 소문자로 검색
			
		  	}else if (input_value.equals("3")) {//남여로 검색		  	 
		  		sql =  sql + " where NVL (cl_gender, 'A') = '" + tData.toUpperCase() + "'" ;//이메일은 대문자로 검색
		  		
		  		
			}else if (input_value.equals("4")) {
				sql = sql ;
			}
		  	sql = sql+ " order by cl_id ";
		  		
		   	int cl_id=0;
			String cl_name ="";
			String cl_gender="";
			String cl_email ="";
			String cl_birthyear ="";
			String cl_Date ="";
			String cl_alter_Date ="";
			
			try {
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				//executeQuery(), ResultSet , Select
				if ( rs.isBeforeFirst() == false) {System.out.println(" 찾으시는 고객정보가 없습니다.");   }
				//rs.isBeforeFirst(); 
				while(rs.next()) {
								
					cl_id = rs.getInt("cl_id");
					nNum = cl_id  ;
					rowcount  = rowcount+1;
					cl_name = rs.getString("cl_name");
					cl_gender = rs.getString("cl_gender");
					cl_email = rs.getString("cl_email");
					cl_birthyear = rs.getString("cl_birthyear");
					cl_Date = rs.getString("cl_Date"); 
					cl_Date = cl_Date.substring(0,10);
					 
					if  ( rs.getString("cl_alter_date") == null) {						  
					      cl_alter_Date  ="";
				    }else {					   
					  cl_alter_Date =  rs.getString("cl_alter_date"); 
					  cl_alter_Date = cl_alter_Date.substring(0,10);
				    }
										 
					System.out.print(" 고객명 :" + cl_name);
				 	System.out.print(", 성 별 :" + cl_gender);							
					System.out.print(", 이메일 :" + cl_email);
					System.out.print(", 생년월일 :" + cl_birthyear);
					System.out.print(", 등록일자 :" + cl_Date);
					System.out.print(", 고객정보 수정일자 :" + cl_alter_Date);
				
					System.out.println();
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try{
					rs.close();
					pstmt.close();
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}

		 
	}

////////////////////Condition SEARCH  END  /////////////////////////////////////////

////////////////////Condition SEARCH  ITEM START  /////////////////////////////////////////
		private static boolean Item_Check(String  input_value ) {
			String str1  = input_value;
			if ((input_value.equals("1") ) || (input_value.equals("2") ) || (input_value.equals("3") ) || (input_value.equals("4") ) ){
				return false;
			}
			 
			return true;
		}
}

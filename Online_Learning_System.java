import java.util.Scanner;
import java.sql.*;
import java.sql.Timestamp;
public class Online_Learning_System {
	static Connection con;
	static Connection con2;
	static Connection con1;
        static String driver = "com.mysql.cj.jdbc.Driver";
        static String url = "jdbc:mysql://localhost:3306/ONLINE_LEARNING_SYSTEM?useUnicode=true&characterEncoding=utf8";
	static int tag = 1;
	static int class_id = 1;

	public static void main(String[] args) throws Exception {
		String user="root";
		String password="Katherine";
		Class.forName(driver);
		con = DriverManager.getConnection(url,user,password);
		System.out.println(con);
		if(!con.isClosed()){
			System.out.println("Succeeded connecting to the database");
		}
		Statement statement = con.createStatement();
		String sql = "select* from School";
		ResultSet rs = statement.executeQuery(sql);
		System.out.println("校名"+"\t"+"校编号");
		int id = 0;
		String name = null;
		while(rs.next()){
			id = rs.getInt("School_id");
			name = rs.getString("School_name");
			System.out.println(id+"\t"+name);
		}
		rs.close();
		con.close();


		//数据库连接试验成功，进入M1用户，为学校进行系别初始化
		String user1 = "M1";
		String password1 = "M1password";
		Class.forName(driver);
		con = DriverManager.getConnection(url,user1,password1);
		if(!con.isClosed()){
			System.out.println("Welcome!"+user1);
		}
		Statement s1 = con.createStatement();
		String sql1 = "insert into College values('信息学院',1,1)";
		String sql2 = "insert into College values('经济学院',2,1)";
		String sql3 = "insert into College values('物理学院',3,2)";
		String sql4 = "insert into College values('建筑学院',4,2)";
		//s1.execute(sql1);
		//s1.execute(sql2);
		//s1.execute(sql3);
		//s1.execute(sql4);
		//M1初始化教师信息
		String sql5 = "insert into Teacher values('T1',1,1,1)";
		String sql6 = "insert into Teacher values('T2',2,1,2)";
		//s1.execute(sql5);
		//s1.execute(sql6);
		//M1初始化学生信息
		//String sql7 = "insert into Student values(1,'S1',1,1)";
		//String sql8 = "insert into Student values(2,'S2',1,1)";
		//String sql9 = "insert into Student values(3,'S3',1,2)";
		//s1.execute(sql7);
		//s1.execute(sql8);
		//s1.execute(sql9);
		con.close();

		//选择用户
		while(true){
		System.out.print("Manager, teacher or student? M/T/S :");
		Scanner input = new Scanner(System.in);
		String character = input.next();
		if(character.equals("M")){
			System.out.print("Name:");
			String n = input.next();
			System.out.print("Password:");
			String p = input.next();
			con1 = DriverManager.getConnection(url,n,p);
			con2 = DriverManager.getConnection(url,n,p);
			if(!con1.isClosed()){
				Manager_Area(n);
				//con1.close();
			}
			else{
				System.out.println("error");
			}
		}
		else if(character.equals("T")){
            System.out.print("Name:");
            String n = input.next();
            System.out.print("Password:");
            String p = input.next();
            con1 = DriverManager.getConnection(url,n,p);
                 if(!con1.isClosed()){
                	 Teacher_Area(n);
                     //con1.close();
                 }
                 else{
                      System.out.println("error");
                 }
		}
		else if(character.equals("S")){
                        System.out.print("Name:");
                        String n = input.next();
                        System.out.print("Password:");
                        String p = input.next();
                        con1 = DriverManager.getConnection(url,n,p);
                        if(!con1.isClosed()){
                        	Student_Area(n);
                        	//con1.close();
                        }
                        else{
                        	System.out.println("error");
                        }
		}
		else{
			break;
		}
		}
		
		//成绩统计分析
		//
	}	
		
	public static void Manager_Area(String n) throws Exception{
		Scanner input1 = new Scanner(System.in);
		Statement s2 = con1.createStatement();
		Statement s3 = con2.createStatement();
		while(true) {
		System.out.println("Welcome, "+n+"!\nEnter 1 for changing schedule;\nEnter 2 for checking classes situation\nEnter 3 for checking new class\nEnter 0 for quit");
		int enter = input1.nextInt();
		if(enter==1){
			//调整课表信息
			System.out.println("Enter the class information (数据库系统 Monday 8 0 10 0):");
			String set_class = input1.next();
			String set_date = input1.next();
			String set_stime_h = input1.next();
			String set_stime_m = input1.next();
			String set_etime_h = input1.next();
			String set_etime_m = input1.next();
			String sqlM_1 = "update Course set Course_date = '"+set_date+"' , Course_stime_h = '"+set_stime_h+"', Course_stime_m = '"+set_stime_m+"', Course_etime_h = '"+set_etime_h+"', Course_etime_m = '"+set_etime_m+"' where Course_name = '数据库系统'";
			s2.execute(sqlM_1);
			System.out.println("Succeeded changing schedule!");
		}

		else if(enter==2){
			//检查课堂情况
			System.out.println("Which class do you want to check? (数据库系统):");
			String check_class = input1.next();
			String sqlM_2 = "select * from Sign_in,Course where Sign_in.Course_id = Course.Course_id and Course.Course_name = '"+check_class+"'";
			System.out.println("课程名"+"\t"+"教师ID"+"\t"+"学生ID"+"\t"+"签到时间");
			ResultSet rs2 = s2.executeQuery(sqlM_2);
			while(rs2.next()) {
				String name1 = rs2.getString("Course_name");
				int teacher1 = rs2.getInt("T_id");
				int student1 = rs2.getInt("S_id");
				Timestamp ts = rs2.getTimestamp("sign_in_time");
				System.out.println(name1+"\t"+teacher1+"\t"+student1+"\t"+ts);
			}
			rs2.close();
		}
		else if(enter==3) {
			//审核新课程
			String sqlM_3 = "select * from Apply_course";
			ResultSet rs2 = s2.executeQuery(sqlM_3);
			System.out.println("课程名"+"\t"+"任课教师ID");
			while(rs2.next()) {
				String name1 = rs2.getString("Course_name");
				int id1 = rs2.getInt("T_id");
				String get_college_id = "select College_id from Teacher where T_id = "+id1;
				String get_school_id = "select School_id from Teacher where T_id ="+id1;
				int c_id=0;
				int sc_id=0;
				ResultSet rsc = s3.executeQuery(get_college_id);
				while(rsc.next()) {
					c_id = rsc.getInt("College_id");
				}
				ResultSet rssc = s3.executeQuery(get_school_id);
				while(rssc.next()) {
					sc_id = rssc.getInt("School_id");
				}
				System.out.println(name1+"\t"+id1);
				System.out.println("Allow?Y/N:");
				String allow = input1.next();
				if(allow.equals("Y")) {
					System.out.println("add the course(Monday,8,0,10,0):");
					String date1 = input1.next();
					int s_t_h = input1.nextInt();
					int s_t_m = input1.nextInt();
					int e_t_h = input1.nextInt();
					int e_t_m = input1.nextInt();
					String sqlM_4 = "insert into Course values('"+name1+"',"+class_id+",'"+date1+"',"+s_t_h+","+s_t_m+","+e_t_h+","+e_t_m+","+id1+","+sc_id+","+c_id+")";
					s3.execute(sqlM_4);
					//String sqlM_5 = "delete from Apply_course where Course_name = '"+name1+"'";
					//s2.execute(sqlM_5);
					class_id = class_id+1;
				}
				else {}
				rsc.close();
				rssc.close();
			}
			//rs2.close();
			}

		else{
			break;
		}
		}
	}
	

	public static void Teacher_Area(String n) throws Exception{
		Scanner input1 = new Scanner(System.in);
		int enter = 0;
		Statement s2 = con1.createStatement();
		String get_teacher_id = "select T_id from Teacher where T_name = '"+n+"'";
		ResultSet rs1 = s2.executeQuery(get_teacher_id);
		int teacher_id_i=0;
		while(rs1.next()) {
			teacher_id_i = rs1.getInt("T_id");
		}
		String teacher_id = Integer.toString(teacher_id_i);
		rs1.close();
		while(true){

		System.out.println("Welcome, "+n+"!\nEnter 1 for applying for a new class;\nEnter 2 for checking schedule;\nEnter 3 for submitting learning materials;\nEnter 4 for having exam;\nEnter 5 for checking exams;\nEnter 6 for having sign up;\nEnter 7 for checking sign up;\nEnter 0 for quit");
		enter = input1.nextInt();
		if(enter==1){
			//申请添加课程
			System.out.println("Enter the course you want to add(数据库系统):");
			String set_class = input1.next();
			String sqlT_0="insert into Apply_course values('"+set_class+"',"+teacher_id+")";
			s2.execute(sqlT_0);
			System.out.println("Succeeded submitting the class.");
		}
		else if(enter==2){
			//查看课表
			String sqlT_1 = "select Course_name,Course_id,Course_date,Course_stime_h,Course_stime_m,Course_etime_h,Course_etime_m,T_id from Course where T_id = "+teacher_id;
			ResultSet rs2 = s2.executeQuery(sqlT_1);
			System.out.println("课程名"+"\t\t"+"课程ID"+"\t"+"星期"+"\t"+"时间"+"\t\t"+"任课教师");
			while(rs2.next()) {
				String name2 = rs2.getString("Course_name");
				int id2 = rs2.getInt("Course_id");
				String date2 = rs2.getString("Course_date");
				int c_s_h = rs2.getInt("Course_stime_h");
				int c_s_m = rs2.getInt("Course_stime_m");
				int c_e_h = rs2.getInt("Course_etime_h");
				int c_e_m = rs2.getInt("Course_etime_m");
				int t_id2 = rs2.getInt("T_id");
				System.out.println(name2+"\t"+id2+"\t"+date2+"\t"+c_s_h+":"+c_s_m+"-"+c_e_h+":"+c_e_m+"\t"+t_id2);
			}
			rs2.close();
		}
		else if(enter==3){
			//上传文件
			System.out.println("Enter the Material name:");
			String Material_n = input1.next();
			System.out.println("Enter the course:");
			String course_n = input1.next();
			String get_course_id = "select Course_id from Course where Course_name = '"+course_n+"'";
			ResultSet rs2 = s2.executeQuery(get_course_id);
			int course_id_i = 0;
			while(rs2.next()) {
				course_id_i = rs2.getInt("Course_id");
			}
			//String course_id = Integer.toString(course_id_i);
			String sqlT_2 = "insert into Material values('"+Material_n+"',"+course_id_i+","+teacher_id+")";
			s2.execute(sqlT_2);
			rs2.close();
		}
		else if(enter==4){
			//发起考试
			System.out.print("Exam day:");
			String set_Month = input1.next();
			String set_Day = input1.next();

			System.out.print("Start time:");
			String set_stime_h = input1.next();
			String set_stime_m = input1.next();

			System.out.print("End time:");
			String set_etime_h = input1.next();
			String set_etime_m = input1.next();

			System.out.print("Course name:");
			String course_n = input1.next(); 
			String get_course_id = "select Course_id from Course where Course_name = '"+course_n+"'";
			ResultSet rs2 = s2.executeQuery(get_course_id);
			int course_id_i = 0;
			while(rs2.next()) {
				course_id_i=rs2.getInt("Course_id");
			}
			String course_id = Integer.toString(course_id_i);

			System.out.print("Give your question:");
			String question = input1.nextLine();
			String sqlT_3 = "insert into Exam values("+tag+",'"+set_Month+"','"+set_Day+"',"+set_stime_h+","+set_stime_m+","+set_etime_h+","+set_etime_m+","+teacher_id+","+course_id+",'"+question+"')";
			tag=tag+1;
			s2.execute(sqlT_3);
			rs2.close();
		}
		else if(enter==5){
			//评阅试卷
			System.out.print("Course name:");
            String course_n = input1.next();
			String get_course_id = "select Course_id from Course where Course_name = '"+course_n+"'";
			ResultSet rs2 = s2.executeQuery(get_course_id);
			int course_id_i = 0;
			while(rs2.next()) {
				course_id_i=rs2.getInt("Course_id");
			}
			String course_id = Integer.toString(course_id_i);
			
            String get_exam_id = "select Exam_id from Exam where Course_id = "+course_id;
            ResultSet rs3 = s2.executeQuery(get_exam_id);
			int exam_id_i = 0;
			while(rs3.next()) {
				exam_id_i=rs3.getInt("Exam_id");
			}
			String exam_id = Integer.toString(exam_id_i);
			String sqlT_4 = "select * from Exam_detail where Exam_id = "+exam_id;
			ResultSet rs4 = s2.executeQuery(sqlT_4);
			System.out.println("考试号"+"\t"+"学生ID"+"\t"+"答案");
			while(rs4.next()) {
				int exam_id4 = rs4.getInt("Exam_id");
				int stu_id4 = rs4.getInt("S_id");
				String answer4 = rs4.getString("Answer");
				System.out.println(exam_id4+"\t"+stu_id4+"\t"+answer4);
				System.out.print("Score(0-100):");
				int score4 = input1.nextInt();
				String sqlT_5 = "update Exam_detail set Score = "+score4+"where S_id=stu_id4";
				s2.execute(sqlT_5);
			}
			rs2.close();
			rs3.close();
			rs4.close();
			}
		else if(enter==6){
			//发起签到
			System.out.println("Enter the course_id:");
			String course_id = input1.next();
		}
		else if(enter==7){
			//查看签到
			System.out.println("Which class do you want to check?(数据库系统)");
			String check_class = input1.next();
			String sqlT_6 = "select Course_name,S_id,sign_in_time from Sign_in,Course where Course.Course_id = Sign_in.Course_id and Course.Course_name = '"+check_class+"'";
			ResultSet rs2 = s2.executeQuery(sqlT_6);
			System.out.println("课程名"+"\t"+"学生ID"+"\t"+"签到时间");
			while(rs2.next()) {
				String name7 = rs2.getString("Course_name");
				int S_id7 = rs2.getInt("S_id");
				java.sql.Timestamp newtime =   rs2.getTimestamp("sign_in_time");
				System.out.println(name7+"\t"+S_id7+"\t"+newtime);
			}
			rs2.close();
		}
		else{
			break;
		}
		}
		con1.close();
	}

	public static void Student_Area(String n) throws Exception{
		Statement s3 = con1.createStatement();
		String get_set_id = "select S_id from Student where S_name = '"+n+"'";
		ResultSet rs1 = s3.executeQuery(get_set_id);
		int stu_id_i=0;
		while(rs1.next()) {
			stu_id_i = rs1.getInt("S_id");
		}
		//int set_id = Integer.toString(stu_id_i);
		rs1.close();
		Scanner input2 = new Scanner(System.in);
        int enter = 0;
        while(true){
			System.out.println("Welcome, "+n+"!\nEnter 1 for checking class and selecting class;\nEnter 2 for viewing schedule;\nEnter 3 for viewing class materials;\nEnter 4 for taking exams;\nEnter 5 for signing in;\nEnter 0 for quit");
			enter = input2.nextInt();
			if(enter==1){
				//查看新课，选课
				String sqlS_1 = "select * from Course";
				ResultSet rs2 = s3.executeQuery(sqlS_1);
				System.out.println("课程名"+"\t\t"+"课程ID"+"\t"+"星期"+"\t"+"时间"+"\t"+"任课教师");
				while(rs2.next()) {
					String name2 = rs2.getString("Course_name");
					int id2 = rs2.getInt("Course_id");
					String date2 = rs2.getString("Course_date");
					int c_s_h = rs2.getInt("Course_stime_h");
					int c_s_m = rs2.getInt("Course_stime_m");
					int c_e_h = rs2.getInt("Course_etime_h");
					int c_e_m = rs2.getInt("Course_etime_m");
					int t_id2 = rs2.getInt("T_id");
					System.out.println(name2+"\t"+id2+"\t"+date2+"\t"+c_s_h+":"+c_s_m+"-"+c_e_h+":"+c_e_m+"\t"+t_id2);
				}
				System.out.println("Enter the course id you want to select:");
				int set_course_id = input2.nextInt();
				String sqlS_2 = "insert into Select_course values("+set_course_id+","+stu_id_i+")";
				s3.execute(sqlS_2);
				rs2.close();
			}
			else if(enter==2){
				//查看课表
				String sqlS_3 = "select Course_name,T_name,Course_date,Course_stime_h,Course_stime_m,Course_etime_h,Course_etime_m from Select_course,Course,Teacher where Select_course.S_id = "+stu_id_i+" and Course.Course_id = Select_course.Course_id and Teacher.T_id = Course.T_id";
				ResultSet rs2 = s3.executeQuery(sqlS_3);
				System.out.println("课程名"+"\t"+"星期"+"\t"+"星期"+"时间"+"任课教师");
				while(rs2.next()) {
					String name2 = rs2.getString("Course_name");
					String date2 = rs2.getString("Course_date");
					int c_s_h = rs2.getInt("Course_stime_h");
					int c_s_m = rs2.getInt("Course_stime_m");
					int c_e_h = rs2.getInt("Course_etime_h");
					int c_e_m = rs2.getInt("Course_etime_m");
					String t_id2 = rs2.getString("T_name");
					System.out.println(name2+"\t"+date2+"\t"+c_s_h+":"+c_s_m+"-"+c_e_h+":"+c_e_m+"\t"+t_id2);
				}
				rs2.close();
			}
			else if(enter==3){
				//查看材料
				String sqlS_4 = "select Material_name,Course_name from Material,Select_course,Course where Select_course.Course_id = Material.Course_id and Course.Course_id = Select_course.Course_id and Select_course.S_id = "+stu_id_i;
				ResultSet rs2 = s3.executeQuery(sqlS_4);
				System.out.println("材料名"+"\t"+"课程名");
				while(rs2.next()) {
					String m_name2 = rs2.getString("Material_name");
					String c_name2 = rs2.getString("Course_name");
					System.out.println(m_name2+"\t"+c_name2);
				}
				rs2.close();
			}
			else if(enter==4){
				//考试
				System.out.println("Which course to take the exam:");
				String name2 = input2.next();
				String sqlS_5 = "select Question from Exam,Course where Exam.Course_id=Course.Course_id and Course_name = '"+name2+"'";
				ResultSet rs2 = s3.executeQuery(sqlS_5);
				while(rs2.next()) {
					String q = rs2.getString("Question");
					System.out.println(q);
				}
				String get_id = "select Exam_id from Exam,Course where Exam.Course_id=Course.Course_id and Course_name = '"+name2+"'";
				ResultSet rs3 = s3.executeQuery(get_id);
				int exam_id2 = 0;
				while(rs3.next()) {
					exam_id2 = rs3.getInt("Exam_id");
				}
				System.out.println("Get_answer:");
				String answer2 = input2.next();
				String sqlS_6 = "insert into Exam_detail values("+exam_id2+","+stu_id_i+",'"+answer2+"',0)";
				s3.execute(sqlS_6);
				rs2.close();
				rs3.close();
			}
			else if(enter==5) {
				//签到
				System.out.println("Which course to sign in:");
				String name2 = input2.next();
				String sqlS_7 = "select Course_id from Course where Course_name = '"+name2+"'";
				ResultSet rs2 = s3.executeQuery(sqlS_7);
				int course_id=0;
				while(rs2.next()) {
					course_id=rs2.getInt("Course_id");
				}
				String sqlS_8 = "select T_id from Course where Course_name = '"+name2+"'";
				ResultSet rs3 = s3.executeQuery(sqlS_8);
				int teacher_id=0;
				while(rs3.next()) {
					teacher_id=rs3.getInt("T_id");
				}
				//Timestamp time_now = new Timestamp(System.currentTimeMillis());
				String sqlS_9 = "insert into Sign_in(Course_id,T_id,S_id) values("+course_id+","+teacher_id+","+stu_id_i+")";
				s3.execute(sqlS_9);
				rs2.close();
				rs3.close();
			}
			else {
				break;
			}
        }

	}
	
			

			
}



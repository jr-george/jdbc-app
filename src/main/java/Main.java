import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/enrollments-jdbc-simple-query";
        String user="root";
        String password="";
        try
        {
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement();
            System.out.println("Connection successful");
            ArrayList<String> queries = new ArrayList<String>();

            queries.add("select major, count(*) as qty from Students GROUP BY major");
            queries.add("select s.Name, c.CourseName from enrollments e INNER JOIN students s ON e.StudentID = s.StudentID " +
                        "INNER JOIN courses c on e.CourseID = c.CourseID");
            queries.add("select * from students where Major=NULL");
            String title="";
            for(int i=0; i<queries.size(); i++){
                ResultSet rs = statement.executeQuery(queries.get(i));
                if (i==0) {
                    title="Count Students by Major";
                }else if(i==1){
                    title="Retrieve Students by Name and their Courses";
                }else if(i==2){
                    title="Retrieve Students Without a Declared Major";
                }
                System.out.println("---------------------------------------------");
                System.out.println(title);
                System.out.println("---------------------------------------------");
                while(rs.next()){
                    switch (i){
                        case 0:
                            System.out.println("Major: " + rs.getString("Major"));
                            System.out.println("Number of Students: " + rs.getString("qty"));
                            break;
                        case 1:
                            System.out.println("Student: "+ rs.getString("Name"));
                            System.out.println("Course: "+ rs.getString("CourseName"));
                            break;
                        case 2:
                            System.out.println("Student: "+ rs.getString("Name"));
                            break;
                    }
                }
                System.out.println("---------------------------------------------");
            }

        }

        catch (SQLException e){
            e.printStackTrace();
        }
    }
}

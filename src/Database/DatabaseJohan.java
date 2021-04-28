package Database;
import java.sql.*;

public class DatabaseJohan {

    private Connection conn;
    private Statement st;
    private ResultSet rs;

    public DatabaseJohan(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11408591", "sql11408591", "gmzmA8XRwd");
            st = conn.createStatement();
        }catch(Exception e){}
    }

    public void getData(){
        try{
            String sql = "select * from LaserGame";
            rs = st.executeQuery(sql);
            System.out.println("Data from online database");

            while(rs.next()){

                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int score = rs.getInt("Score");
                System.out.println("ID: " + id + "\nName: " + name + "\nScore: " + score);

            }
        }catch(Exception ex){}
    }
}

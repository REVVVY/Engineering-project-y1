package Database;

import javax.swing.*;
import java.sql.*;

public class DataConn {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DataConn(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11408587","sql11408587","QuQnNEMWdj");
            st = con.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error is found: " + e);
        }
    }

    public void getData(){
        try{
            String sql = "select * from Highscore";
            rs = st.executeQuery(sql);
            System.out.println("Data från Isacs databas");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                System.out.println("ID: " + id + "\nName: " + name + "\nScore: " + score);
            }

        }catch(Exception ex){
            System.out.println("Error is found: "+ex);
        }
    }

    public void setData(){
        try{
            String name = JOptionPane.showInputDialog("Ange ditt namn");
            int score = Integer.parseInt(JOptionPane.showInputDialog("Ange din score"));
            String sql = "INSERT INTO Highscore VALUES (?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, 0);
            pstmt.setString(2, name);
            pstmt.setInt(3, score);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Uppdaterat databasen");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

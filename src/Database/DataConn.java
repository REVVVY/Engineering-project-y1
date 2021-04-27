package Database;

import Client.*;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DataConn {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DataConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11408587", "sql11408587", "QuQnNEMWdj");
            st = con.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error is found: " + e);
        }
    }

    public void getData() {
        try {

            String sql = "select * from Highscore";
            rs = st.executeQuery(sql);
            System.out.println("Data från Isacs databas");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                System.out.println("ID: " + id + "\nName: " + name + "\nScore: " + score);
            }

        } catch (Exception ex) {
            System.out.println("Error is found: " + ex);
        }
    }

    public ArrayList<Player> getHighscore(ArrayList<Player> highscore) {

        try {
            String sql = "select * from Highscore";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                String name = rs.getString("name");
                int score = rs.getInt("score");
                Player player = new Player(name, score);
                highscore.add(player);
            }

        } catch (Exception e) {
        }
        return highscore;
    }

    public ArrayList<Game> getGamelist(ArrayList<Game> gamelist) {
        Game game;

        try {
            String sql = "select * from Games";
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int winner = rs.getInt("winner");

                String name1 = rs.getString("player1");
                int score1 = rs.getInt("score1");
                Player player1 = new Player(name1, score1);

                String name2 = rs.getString("player2");

                if(name2 != null){
                    int score2 = rs.getInt("score2");
                    Player player2 = new Player(name2, score2);
                    game = new Game(player1, player2);

                    if (winner == 1) {
                        game.setWinner(player1);
                    } else if (winner == 2) {
                        game.setWinner(player2);
                    }
                    else{
                        game.setWinner(null);
                    }

                }else{
                    game = new Game(player1);
                    game.setWinner(player1);
                }
                gamelist.add(game);
            }
        }catch(Exception ex) {}

        return gamelist;
    }

    public void setData() {
        try {
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

package Database;

import Client.Model.*;

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

    public void setDataInDatabase(Game currentGame) {
        try {
            String sqlHighscore = "INSERT INTO Highscore VALUES (?, ?, ?)";

            String name1 = currentGame.getPlayer1().getName();
            int score1 = currentGame.getPlayer1().getScore();

            if(currentGame.getPlayer2() != null){
                String name2 = currentGame.getPlayer2().getName();
                int score2 = currentGame.getPlayer2().getScore();

                PreparedStatement player1Statement = con.prepareStatement(sqlHighscore);
                player1Statement.setInt(1, 0);
                player1Statement.setString(2, name1);
                player1Statement.setInt(3, score1);
                player1Statement.executeUpdate();
                player1Statement.close();

                PreparedStatement player2Statement = con.prepareStatement(sqlHighscore);
                player2Statement.setInt(1, 0);
                player2Statement.setString(2, name2);
                player2Statement.setInt(3, score2);
                player2Statement.executeUpdate();
                player2Statement.close();
            } else{
                PreparedStatement player1Statement = con.prepareStatement(sqlHighscore);
                player1Statement.setInt(1, 0);
                player1Statement.setString(2, name1);
                player1Statement.setInt(3, score1);
                player1Statement.executeUpdate();
                player1Statement.close();
            }
            System.out.println("Highscore updated in database");

            String sqlGame = "INSERT INTO Games VALUES (?, ?, ?, ?, ?, ?)";

            Player winningPlayer = currentGame.getWinner();
            int winner = 0;
            if(winningPlayer == currentGame.getPlayer1()){
                winner = 1;
            } else if(winningPlayer == currentGame.getPlayer2()){
                winner = 2;
            } else if(winningPlayer == null){
                winner = 3;
            }

            if(currentGame.getPlayer2() != null) {
                String name2 = currentGame.getPlayer2().getName();
                int score2 = currentGame.getPlayer2().getScore();

                PreparedStatement game2PlayerStatement = con.prepareStatement(sqlGame);
                game2PlayerStatement.setInt(1, 0);
                game2PlayerStatement.setString(2, name1);
                game2PlayerStatement.setInt(3, score1);
                game2PlayerStatement.setString(4, name2);
                game2PlayerStatement.setInt(5, score2);
                game2PlayerStatement.setInt(6, winner);
                game2PlayerStatement.executeUpdate();
                game2PlayerStatement.close();

            } else {
                PreparedStatement game1PlayerStatement = con.prepareStatement(sqlGame);
                game1PlayerStatement.setInt(1, 0);
                game1PlayerStatement.setString(2, name1);
                game1PlayerStatement.setInt(3, score1);
                game1PlayerStatement.setString(4, "");
                game1PlayerStatement.setInt(5, 0);
                game1PlayerStatement.setInt(6, winner);
                game1PlayerStatement.executeUpdate();
                game1PlayerStatement.close();
            }
            System.out.println("Games updated in database");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

package model.dao;

import model.bean.video;

import java.sql.*;
import java.util.ArrayList;

public class VideoDAO {
    public  void AddVideo(video vid,String username)  throws ClassNotFoundException, SQLException {
        Connection con = connectdb.getConnection();
        Statement stm =con.createStatement();
        String sql = "INSERT INTO video (name, isdone, `desc`, filelocation, filesize,username) VALUES (?, ?, ?, ?, ?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vid.getName());
            ps.setBoolean(2, vid.getIsDone());
            ps.setString(3, vid.getDesc());
            ps.setString(4, vid.getFileLocation());
            ps.setLong(5, vid.getFilesize());
            ps.setString(6, username);
            ps.executeUpdate();
        }
    }
    public ArrayList<video> GetAllVideo(String username) throws ClassNotFoundException, SQLException {
        Connection con = connectdb.getConnection();
        Statement stm =con.createStatement();
        String Query="Select * from video WHERE username = '"+username+"'";
        ArrayList<video> list= new ArrayList<video>();
        ResultSet rs= stm.executeQuery(Query);
        while(rs.next()) {
            String name=rs.getString("name");
            String fileLocation=rs.getString("fileLocation");
            long filesize=rs.getLong("filesize");
            String description=rs.getString("desc");
            Boolean isDone=rs.getBoolean("isdone");
            video vid =new video(name,isDone,fileLocation,description,filesize);
            list.add(vid);
        }
        return list;
    }
    public void UpdateVideo(video vid ,String username) throws ClassNotFoundException, SQLException {
        Connection con = connectdb.getConnection();
        Statement stm =con.createStatement();
        String sql = "UPDATE video SET filelocation = ?, isdone = ?, `desc` = ? WHERE name = ? AND username = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vid.getFileLocation());
            ps.setInt(2, 1);
            ps.setString(3, "The file compression is completed!");
            ps.setString(4, vid.getName());
            ps.setString(5, username);

            ps.executeUpdate();
        }
    }
}

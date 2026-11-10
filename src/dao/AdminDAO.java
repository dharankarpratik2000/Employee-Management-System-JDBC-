package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConnection;

public class AdminDAO 
{
	public boolean login(String username, String password) {

        String sql = "SELECT * FROM admin "
                   + "WHERE username = ? AND password = ?";

        try {

            Connection cn = DBConnection.getConnection();

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
}

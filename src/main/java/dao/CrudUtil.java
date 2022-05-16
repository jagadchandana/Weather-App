package dao;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql, Object...parms) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0; i < parms.length; i++) {
            stm.setObject((i+1), parms[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T) stm.executeQuery();
        }else {
            return ((T) (Boolean) (stm.executeUpdate()>0));
        }
    }
}

package net.hunau.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBLink {
    final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    public Connection openConnection(String url, String user, String password) {
        Connection conn = null;
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            conn = null;
        } catch (SQLException e) {
            conn = null;
        }
        System.out.println(conn);
        return conn;
    }

    public List<String> query(Connection conn, String sql) {
        if (conn == null) {
            return null;
        }
        Statement statement = null;
        ResultSet result = null;
        List<String> list = new ArrayList<>();
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            System.out.println(result);
            if (result != null && result.first()) {
                int idColumnIndex = result.findColumn("id");
                int nameColumnIndex = result.findColumn("name");
                int sexyColumnIndex = result.findColumn("sexy");
                int pwdColumnIndex = result.findColumn("pwd");
                int isUsedColumnIndex = result.findColumn("isused");
                while (!result.isAfterLast()) {
                    String str = "";
                    str += "ID:" + result.getString(idColumnIndex);
                    str +="密码:" + result.getString(pwdColumnIndex);
                    str +="姓名:" + result.getString(nameColumnIndex);
                    str +="性别:" + result.getString(sexyColumnIndex);
                    str +="是否有效:" + (result.getString(isUsedColumnIndex).toString().equals("1") ? "是" : "否");
                    //System.out.println(str);
                    list.add(str);
                    result.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        for (String i :list) {
            System.out.println(i);
        }
        return list;
    }

    public int execSQL(Connection conn, String sql) {
        int execResult = -1;
        if (conn == null) {
            return execResult;
        }
        Statement statement = null;
        try {
            statement = conn.createStatement();
            if (statement != null) {
                execResult = statement.executeUpdate(sql);   //execute(sql);
            }
        } catch (SQLException e) {
            execResult = -1;
        }
        return execResult;
    }
}

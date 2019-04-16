package com.hui.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
    private static ComboPooledDataSource ds=new ComboPooledDataSource();

    public static Connection getConn() throws SQLException {
        Connection conn= ds.getConnection();
        return conn;
    }

    public static DataSource getDataSources(){
        return ds;
    }

    //释放资源
    public static void closeResource(Connection conn, Statement st, ResultSet rs){
        closeConn(conn);
        closeSt(st);
        closeRs(rs);
    }

    private static void closeRs(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs=null;
        }

    }

    private static void closeSt(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            st=null;
        }

    }

    public  static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn=null;
        }
    }




}

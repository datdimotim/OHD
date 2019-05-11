package dbViewer;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import static dbViewer.SQLDriver.dbAddr;
import static dbViewer.SQLDriver.*;

public class SQLDriver {
    public static final String dbAddr="192.168.1.71:3306";
    public static final String dbName="testdb";
    public static final String username="user";
    public static final String password="password";

    public SQLDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("mysql driver not found", e);
        }
    }

    @SneakyThrows
    public List<List<String>> query(String query){
        Properties properties = new Properties();
        properties.setProperty("user",username);
        properties.setProperty("password",password);
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","utf8");

        try (Connection c = DriverManager.getConnection("jdbc:mysql://"+dbAddr +"/"+ dbName,properties);
             Statement st=c.createStatement(); ResultSet rs= st.executeQuery(query)){
            List<List<String>> lls=new ArrayList<>();
            {
                List<String> ls = new ArrayList<>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    ls.add(rs.getMetaData().getColumnName(i));
                }
                lls.add(ls);
            }

            while (rs.next()) {
                List<String> ls = new ArrayList<>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    String val=rs.getString(rs.getMetaData().getColumnName(i));
                    ls.add(val!=null?val:" ");
                }
                lls.add(ls);
            }
            return lls;
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<List<String>>(){{add(new ArrayList<>());}};
        }
    }



    @SneakyThrows
    public int update(String query){
        try (Connection c = DriverManager.getConnection("jdbc:mysql://"+dbAddr +"/"+ dbName+"?"+"characterEncoding=utf8", username, password);
             Statement st=c.createStatement()){
            return st.executeUpdate(query);
        }
    }
}



class T{
    @SneakyThrows
    public static void main(String[] args) {
        Connection c = DriverManager.getConnection("jdbc:mysql://"+dbAddr +"/"+ "doc"+"?"+"characterEncoding=utf8", username, password);
        Statement st=c.createStatement();
        st.executeUpdate("insert into tabl values (\"катюшк\",20)");


        ResultSet rs=st.executeQuery("select * from tabl");

        rs.next();
        System.out.println(rs.getString("name"));
        System.out.println(rs.getString("age"));
        st.executeUpdate("delete from tabl");
    }
}
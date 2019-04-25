package dbViewer;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDriver {
    public static final String dbAddr="localhost:3306";
    public static final String dbName="testdb";
    public static final String username="usr";
    public static final String password="pwd";

    public SQLDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("mysql driver not found", e);
        }
    }

    @SneakyThrows
    public List<List<String>> query(String query){
        try (Connection c = DriverManager.getConnection("jdbc:mysql://"+dbAddr +"/"+ dbName, username, password);
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
        }
    }
}

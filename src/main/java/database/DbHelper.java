package database;

import database.annotation.Column;
import database.annotation.DatabaseTable;
import database.annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DbHelper<T> {

    private Connection con;

    public static <T> DbHelper<T> getConnection() throws SQLException {
        return new DbHelper<T>();
    }
    private DbHelper() throws SQLException {
        this.con =DriverManager.getConnection("jdbc:h2:C:\\Users\\dlwan\\Documents\\Study\\2020SQ\\SWE262P\\ZoomAPI-Java\\src\\db\\zoom", "admin", "123");
    }

    public void write(T t) throws SQLException, IllegalAccessException {
        // create a data in database
        Class<?> cls = t.getClass();
        Field[] declaredFields = cls.getDeclaredFields();
        Field pKey = null;
        List<Field> columns = new ArrayList<>();
        StringJoiner joiner = new StringJoiner(",");
        for (Field f : declaredFields) {
            if (f.isAnnotationPresent(PrimaryKey.class)) {
                pKey = f;
                // System.out.println("The Primary Key is : " + f.getName() + ", value is : " + f.get(t) + " \nand the columns are : ");
            } else if (f.isAnnotationPresent(Column.class)){
                columns.add(f);
                joiner.add(f.getName());
                // System.out.println("The Column is : " + f.getName() + ", value is : " + f.get(t)) ;
            }
        }
        int number = columns.size();
        String qMarks = IntStream.range(0, number).mapToObj(e -> "?").collect(Collectors.joining(","));
        String sql = "insert into " + cls.getAnnotation(DatabaseTable.class).value() + "( " + pKey.getName() + "," + joiner.toString() + ") " + "values ( default, " + qMarks + ")";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        int index = 1;
        for (Field f : columns) {
            f.setAccessible(true);
            if (f.getType() == int.class) {
                preparedStatement.setInt(index++, (int) f.get(t));
            } else if (f.getType() == String.class) {
                preparedStatement.setString(index++, (String) f.get(t));
            }
        }

        preparedStatement.executeUpdate();
    }

}

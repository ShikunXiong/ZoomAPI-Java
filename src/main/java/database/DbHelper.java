package database;

import database.annotation.Column;
import database.annotation.DatabaseTable;
import database.annotation.PrimaryKey;
import database.annotation.SearchKey;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DbHelper<T> {

    private final Connection con;

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

    public List<T> read(Class<T> cls) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<T> lst = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        String sql = "select * from " + cls.getAnnotation(DatabaseTable.class).value();
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            T t = cls.getConstructor().newInstance();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(PrimaryKey.class)) {
                    // primary key
                    f.set(t, rs.getLong(f.getName()));
                } else if (f.isAnnotationPresent(Column.class)) {
                    if (f.getType() == int.class) {
                        f.set(t, rs.getInt(f.getName()));
                    } else if (f.getType() == String.class) {
                        f.set(t, rs.getString(f.getName()));
                    }
                }
            }
            lst.add(t);
        }
        return lst;
    }

    public T read(Class<T> cls, String id) {
        // id is unique
        Field[] fields = cls.getDeclaredFields();
        Field sKey = null;
        // Find search field
        for (Field f : fields) {
            if (f.isAnnotationPresent(SearchKey.class)) {
                sKey = f;
                break;
            }
        }
        // Construct SQL
        String sql ="select * from " + cls.getAnnotation(DatabaseTable.class).value() + " where " +sKey.getName() + " = " + id;
        return null;
    }

}

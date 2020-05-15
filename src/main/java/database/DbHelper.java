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
        generateSQL(t, columns, sql);
    }

    public List<T> read(Class<T> cls) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<T> lst = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        String sql = "select * from " + cls.getAnnotation(DatabaseTable.class).value();
        getAllObjects(cls, lst, fields, sql);
        return lst;
    }

    public T read(Class<T> cls, long primaryKey) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // read based on primary key
        Field[] fields = cls.getDeclaredFields();
        Field pKey = null;
        for (Field f : fields) {
            if (f.isAnnotationPresent(PrimaryKey.class)) {
                pKey = f;
                break;
            }
        }
        String sql = "select * from " + cls.getAnnotation(DatabaseTable.class).value() + " where " + pKey.getName() + " = " + primaryKey;
        return getSingleObject(cls, fields, pKey, sql);
    }
    
    public T readBySearchKey(Class<T> cls, String searchKey) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        // read based on search id when returned type is definitely 1
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
        String sql ="select * from " + cls.getAnnotation(DatabaseTable.class).value() + " where " + sKey.getName() + " = '" + searchKey + "'";
        return getSingleObject(cls, fields, sKey, sql);
    }

    public List<T> read(Class<T> cls, String id) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // read based on search id
        List<T> lst = new ArrayList<>();
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
        String sql ="select * from " + cls.getAnnotation(DatabaseTable.class).value() + " where " + sKey.getName() + " = " + id;
        getAllObjects(cls, lst, fields, sql);
        return lst;
    }

    public List<T> read(Class<T> cls, String fieldName, String fieldValue) throws InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        List<T> lst = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        boolean flag = false;
        for (Field f : fields) {
            if (f.getName().equals(fieldName)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Field name does not exist.");
            return null;
        }
        String sql ="select * from " + cls.getAnnotation(DatabaseTable.class).value() + " where " + fieldName + " = '" + fieldValue + "'";
        getAllObjects(cls, lst, fields, sql);
        return lst;
    }

    public void update(T t) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException {
        // update all information based on primary key
        Class<?> cls = t.getClass();
        Field[] fields = cls.getDeclaredFields();
        List<Field> columns = new ArrayList<>();
        StringJoiner joiner = new StringJoiner(",");
        String searchKey = null;
        Field sKey = null;
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(SearchKey.class)) {
                sKey = f;
                searchKey = (String) f.get(t);
            } else if (f.isAnnotationPresent(Column.class)) {
                columns.add(f);
                String s = f.getName() + " = ?";
                joiner.add(s);
            }
        }
        T data = readBySearchKey((Class<T>) cls, searchKey);
        if (data == null) {
            write(t);
        } else {
            String sql = "update " + cls.getAnnotation(DatabaseTable.class).value() + " set " + joiner.toString() + " where " + sKey.getName() + " = '" + searchKey + "'";
            generateSQL(t, columns, sql);
        }
    }

    public void update(Class<T> cls, String id, String fieldName, String fieldValue) throws SQLException {
        // update based on search key
        Field[] fields = cls.getDeclaredFields();
        Field sKey = null;
        boolean flag = false;
        for (Field f : fields) {
            if (f.isAnnotationPresent(SearchKey.class)) {
                sKey = f;
            }
            if (f.getName().equals(fieldName)) {
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Field name does not exist.");
            return;
        }
        String sql = "update " + cls.getAnnotation(DatabaseTable.class).value() + " set " + fieldName + " = '" + fieldValue + "' where " + sKey.getName() + " = '" + id + "'";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    public void delete(T t) throws IllegalAccessException, SQLException {
        Class<?> cls = t.getClass();
        Field[] fields = cls.getDeclaredFields();
        long primaryKey = 0;
        Field pKey = null;
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(PrimaryKey.class)) {
                pKey = f;
                primaryKey = f.getLong(t);
            }
        }
        String sql = "delete from " + cls.getAnnotation(DatabaseTable.class).value() + " where " + pKey.getName() + " = " + primaryKey;
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    public void delete(Class<T> cls, String fieldName, String fieldValue) throws SQLException {
        // delete based on field name and value
        Field[] fields = cls.getDeclaredFields();
        boolean flag = false;
        for (Field f : fields) {
            if (f.getName().equals(fieldName)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Field name does not exist.");
            return;
        }
        String sql = "delete from " + cls.getAnnotation(DatabaseTable.class).value() + " where " + fieldName + " = '" + fieldValue  + "'";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    private void generateSQL(T t, List<Field> columns, String sql) throws SQLException, IllegalAccessException {
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

    private void getAllObjects(Class<T> cls, List<T> lst, Field[] fields, String sql) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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
    }

    public void closeConnection() throws SQLException {
        this.con.close();
    }

    private T getSingleObject(Class<T> cls, Field[] fields, Field sKey, String sql) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            T t = cls.getConstructor().newInstance();
            String searchKey = rs.getString(sKey.getName());
            sKey.setAccessible(true);
            sKey.set(t, searchKey);
            for (Field f : fields) {
                if (f.isAnnotationPresent(Column.class)) {
                    f.setAccessible(true);
                    if (f.getType() == int.class) {
                        f.set(t, rs.getInt(f.getName()));
                    } else if (f.getType() == String.class) {
                        f.set(t, rs.getString(f.getName()));
                    }
                }
            }
            return t;
        } else {
            System.out.println("This primary key does not exist, return NULL");
            return null;
        }
    }

}

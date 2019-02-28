package db;

import java.awt.TexturePaint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 阮严冬
 *
 */
public class DBUtils {
	
	private Connection connection;
	//private String url = "jdbc:mysql://127.0.0.1:3306/swu_plant?useUnicode=true&characterEncoding=utf-8";
	//private String user = "root";
	//private String password = "root";
	private Statement statement;
	private ResultSet resultSet;
	
	/**
	 * 连接数据库
	 * @return
	 */
	public boolean openConnection() {
		connection = C3p0Utils.getConnection();
		if (connection != null) {
			System.out.println("数据库连接成功");
			return true;
		}else {
			System.out.println("数据库连接失败");
			return false;
		}
	}
	
	/**
	 * 查询user表的结果
	 * @return
	 */
	public ResultSet getQueryUserTableResult() {
		String mQueryUserSql = "select * from user";
		try {
			 statement = connection.createStatement();
		    resultSet = statement.executeQuery(mQueryUserSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	/**
	 * 注册时核对某个用户是否存在
	 * @param username
	 * @return
	 */
	public boolean checkUserExist(String username) {
		boolean isExist = false;
		ResultSet resultSet= getQueryUserTableResult();
		try {
			if (resultSet!=null) {
				while(resultSet.next()) {
					if (resultSet.getString("username").equals(username)) {
						System.out.println("用户已经存在");
						isExist = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isExist = false;
		}
		return isExist;
	}
	
	/**
	 *  登录时检测用户名和密码是否正确
	 * @param username
	 * @param password
	 * @return
	 */
	public  boolean checkUserExist(String username,String password) {
		boolean isExist = false;
		ResultSet resultSet = getQueryUserTableResult();
		if (resultSet != null) {
			try {
				while(resultSet.next()) {
					if (resultSet.getString("username").equals(username) && 
							resultSet.getString("password").equals(password)) {
						isExist = true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isExist;
	}
	
	/**	
	 * 插入新用户
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean insertNewUserData(String username,String password) {
		boolean isSuccess = true;
		String sql = "insert into user(username,password) values(" + "'" +username+ "', " + "'" + password
				+ "' )";
		try {
			statement = connection.createStatement();
			/**
			 * true表示第一个返回值是一个ResultSet对象；false表示这是一个更新个数或者没有结果集。
			 * 如果sql是select语句，且成功查询到相应记录，则返回true；
			 * 如果sql预计是insert、update之类的不反悔结果集的语句，或者虽然是select语句，但是没有查询到相应结果集的时候，则返回false。 
			 */
			isSuccess = statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			isSuccess = true;
		}
		return isSuccess;
	}
	
	public void close() {
		
		try {
			if (connection != null) {
					connection.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			System.out.println("数据库关闭成功！！！");
		} catch (SQLException e) {
			System.out.println("数据库关闭出错："+e.getMessage());
			e.printStackTrace();
		}
	}
	
}

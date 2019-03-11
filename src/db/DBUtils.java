package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
						
						System.out.println(resultSet.getString("username")+"用户已经存在");
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String registerTime = format.format(date);
		
		String sql = "insert into user(username,password,register_time) values(" + "'" +username+ "', " + "'" + password
				+"', " + "'" + registerTime+ "' )";
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
	
	/**
	 * 修改用户密码
	 * @param username
	 * @param newPassword
	 * @return
	 */
	public boolean updateUserPassword(String username,String password) {
		String sql = "update user set password = ? where username = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, password);
			statement.setString(2, username);
			int count = statement.executeUpdate();
			if (count>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 插入用户收藏的植物数据
	 * @param plantChineseName
	 * @param username
	 * @return
	 */
	public boolean insertCollectionData(String plantChineseName,String username) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String collectionTime = format.format(date);
		boolean isSuccess = false;
		String sql = "insert into collection(plant_chinese_name,username,collection_time) values(" + "'" +plantChineseName+ "', " + "'" + username
				+"', " + "'" +collectionTime+ "' )";
		try {
			statement = connection.createStatement();
			isSuccess = statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			isSuccess = true;
		}
		return isSuccess;
	}
	
	/**
	 * 批量插入数据
	 * @param plants
	 * @param username
	 * @return
	 */
	public boolean batchInsertCollection(String[] plants,String username) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String collectionTime = format.format(date);
		
		boolean isSuccess = false;
		String sql = "insert into collection(plant_chinese_name,username,collection_time) values(?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i<plants.length;i++) {
				preparedStatement.setString(1, plants[i]);
				preparedStatement.setString(2, username);
				preparedStatement.setString(3, collectionTime);
				preparedStatement.addBatch();
			}
			int[] rows = preparedStatement.executeBatch();
			preparedStatement.close();
			if (rows.length == plants.length) {
				isSuccess = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	/**
	 * 查询用户收藏
	 * @param username
	 * @return
	 */
	public List<String> queryCollection(String username){
		String sql = "select plant_chinese_name from collection where username = ?";
		List<String> plants = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while(resultSet.next()) {
					String plant = resultSet.getString("plant_chinese_name");
					if (plant != null) {
						plants.add(plant);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return plants;
	}
	
	/**
	 * 删除用户收藏数据
	 * @param plantChineseName
	 * @param username
	 * @return
	 */
	public boolean deleteCollectionData(String plantChineseName,String username) {
		String sql = "delete from collection where plant_chinese_name = ? and username = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, plantChineseName);
			preparedStatement.setString(2, username);
			int count = preparedStatement.executeUpdate();
			preparedStatement.close();
			if (count>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 批量删除收藏数据
	 * @param plants
	 * @param username
	 * @return
	 */
	public boolean batchDeleteCollection(String[] plants,String username) {
		String sql = "delete from collection where plant_chinese_name = ? and username = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i<plants.length;i++) {
				preparedStatement.setString(1, plants[i]);
				preparedStatement.setString(2, username);
				preparedStatement.addBatch();
			}
			int[] rows = preparedStatement.executeBatch();
			preparedStatement.close();
			if (rows.length == plants.length) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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

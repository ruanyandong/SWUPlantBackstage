package db;

import java.awt.TexturePaint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ���϶�
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
	 * �������ݿ�
	 * @return
	 */
	public boolean openConnection() {
		connection = C3p0Utils.getConnection();
		if (connection != null) {
			System.out.println("���ݿ����ӳɹ�");
			return true;
		}else {
			System.out.println("���ݿ�����ʧ��");
			return false;
		}
	}
	
	/**
	 * ��ѯuser��Ľ��
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
	 * ע��ʱ�˶�ĳ���û��Ƿ����
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
						System.out.println("�û��Ѿ�����");
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
	 *  ��¼ʱ����û����������Ƿ���ȷ
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
	 * �������û�
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
			 * true��ʾ��һ������ֵ��һ��ResultSet����false��ʾ����һ�����¸�������û�н������
			 * ���sql��select��䣬�ҳɹ���ѯ����Ӧ��¼���򷵻�true��
			 * ���sqlԤ����insert��update֮��Ĳ����ڽ��������䣬������Ȼ��select��䣬����û�в�ѯ����Ӧ�������ʱ���򷵻�false�� 
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
			System.out.println("���ݿ�رճɹ�������");
		} catch (SQLException e) {
			System.out.println("���ݿ�رճ���"+e.getMessage());
			e.printStackTrace();
		}
	}
	
}

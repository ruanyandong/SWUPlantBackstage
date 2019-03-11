package servlet;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import bean.BaseBean;
import bean.UserBean;
import db.DBUtils;
import utils.TextUtils;

/**
 * 
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		       
		String username = request.getParameter("username"); // 获取客户端传过来的参数
		String password = request.getParameter("password");
		
		System.out.println("username:"+username+" password:"+password);
		
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			System.out.println("用户名或密码为空");
			return;
		} 
		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnection();
		// 打开数据库连接
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
		UserBean userBean = new UserBean(); // user的对象
		if (dbUtils.checkUserExist(username)) {
			// 判断账号是否存在
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("该账号已存在");
		} else if (!dbUtils.insertNewUserData(username, password)) {
			// 注册成功
			data.setCode(0);
			data.setMsg("注册成功");
			ResultSet rs = dbUtils.getQueryUserTableResult();
			int id = -1;
			if (rs != null) {
				System.out.println("rs不等于null");
				try {
					while (rs.next()) {
						if (rs.getString("username").equals(username) 
								&& rs.getString("password").equals(password)) {
							id = rs.getInt("id");
							System.out.println(id);
							break;
						}
					}
					userBean.setId(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.setUsername(username);
			userBean.setPassword(password);
			data.setData(userBean);
		} else {
			// 注册不成功，这里错误没有细分，都归为数据库错误
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("数据库错误");
		}
		Gson gson = new Gson();
		String json = gson.toJson(data);
		// 将对象转化成json字符串
		Writer writer = null;
		try {
			writer = response.getWriter();
			if (writer != null) {
				writer.write(json);// 将json数据传给客户端
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close(); // 关闭这个流，不然会发生错误的
			}
		}
		dbUtils.close(); // 关闭数据库连接}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

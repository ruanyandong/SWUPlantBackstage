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

@WebServlet("/UpdateUserPasswordServlet")
public class UpdateUserPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateUserPasswordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		       
		String username = request.getParameter("username"); // 获取客户端传过来的参数
		String password = request.getParameter("newPassword");
		
		System.out.println("username is "+username);
		System.out.println("newPassword is "+password);
		
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			return;
		}
		
		BaseBean baseBean = new BaseBean();
		UserBean userBean = new UserBean();
		Writer writer = response.getWriter();
		
		DBUtils dbUtils = new DBUtils();
		boolean isSuccess = dbUtils.openConnection();
		if (isSuccess) {
			if (dbUtils.checkUserExist(username)) {
				boolean isUpdate =  dbUtils.updateUserPassword(username, password);
				if (isUpdate) {
					baseBean.setCode(200);
					baseBean.setMsg("修改成功");
					userBean.setUsername(username);
					userBean.setPassword(password);
					ResultSet resultSet = dbUtils.getQueryUserTableResult();
					if (resultSet != null) {
						try {
							while(resultSet.next()) {
								if (resultSet.getString("username").equals(username)) {
									userBean.setId(resultSet.getInt("id"));
									break;
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					baseBean.setData(userBean);
				}else {
					baseBean.setCode(300);
					baseBean.setMsg("修改失败");
					userBean.setUsername(username);
					userBean.setPassword(password);
					ResultSet resultSet = dbUtils.getQueryUserTableResult();
					if (resultSet != null) {
						try {
							while(resultSet.next()) {
								if (resultSet.getString("username").equals(username)) {
									userBean.setId(resultSet.getInt("id"));
									break;
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					baseBean.setData(userBean);
				}
			}else {
				baseBean.setCode(404);
				baseBean.setMsg("用户不存在");
				userBean.setId(-1);
				userBean.setUsername(username);
				userBean.setPassword(password);
				baseBean.setData(userBean);
			}
		}else {
			baseBean.setCode(500);
			baseBean.setMsg("数据库连接失败");
			baseBean.setData(userBean);
		}
		
		Gson gson =new Gson();
		String result = gson.toJson(baseBean);
		writer.write(result);
		writer.close();
		dbUtils.close();
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

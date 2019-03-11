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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			return;
		}
        // 打开数据库连接
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnection();
        
        BaseBean  baseBean = new BaseBean();
        UserBean userBean = new UserBean();
        // 该用户不存在
        if (!dbUtils.checkUserExist(username)) {
			baseBean.setCode(-1);
			userBean.setId(-1);
			userBean.setUsername(username);
			userBean.setPassword(password);
			baseBean.setData(userBean);
			baseBean.setMsg("该用户不存在");
		}else if (dbUtils.checkUserExist(username) && !dbUtils.checkUserExist(username, password)) {
			baseBean.setCode(1);
			userBean.setId(-1);
			userBean.setUsername(username);
			userBean.setPassword(password);
			baseBean.setData(userBean);
			baseBean.setMsg("密码错误");
		}else if (dbUtils.checkUserExist(username, password)) {
			baseBean.setCode(0);
			baseBean.setMsg("登陆成功");
			ResultSet resultSet = dbUtils.getQueryUserTableResult();
			int id = -1;
			if (resultSet != null) {
				try {
					while(resultSet.next()) {
						if (resultSet.getString("username").equals(username) &&
								resultSet.getString("password").equals(password)) {
							id = resultSet.getInt("id");
						}
					}
					userBean.setId(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.setUsername(username);
			userBean.setPassword(password);
			baseBean.setData(userBean);
		}else {
			baseBean.setCode(500);
			baseBean.setData(userBean);
			baseBean.setMsg("数据库错误，登陆失败");
		}
        
        Gson gson = new Gson();
        String resultBack = gson.toJson(baseBean);
        Writer writer = null;
        try {
			writer = response.getWriter();
			if (writer != null) {
				writer.write(resultBack);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (writer != null) {
				writer.close();
			}
		}
        dbUtils.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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
		       
		String username = request.getParameter("username"); // ��ȡ�ͻ��˴������Ĳ���
		String password = request.getParameter("password");
		
		System.out.println("username:"+username+" password:"+password);
		
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			System.out.println("�û���������Ϊ��");
			return;
		} 
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnection();
		// �����ݿ�����
		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		UserBean userBean = new UserBean(); // user�Ķ���
		if (dbUtils.checkUserExist(username)) {
			// �ж��˺��Ƿ����
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("���˺��Ѵ���");
		} else if (!dbUtils.insertNewUserData(username, password)) {
			// ע��ɹ�
			data.setCode(0);
			data.setMsg("ע��ɹ�");
			ResultSet rs = dbUtils.getQueryUserTableResult();
			int id = -1;
			if (rs != null) {
				System.out.println("rs������null");
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
			// ע�᲻�ɹ����������û��ϸ�֣�����Ϊ���ݿ����
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("���ݿ����");
		}
		Gson gson = new Gson();
		String json = gson.toJson(data);
		// ������ת����json�ַ���
		Writer writer = null;
		try {
			writer = response.getWriter();
			if (writer != null) {
				writer.write(json);// ��json���ݴ����ͻ���
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close(); // �ر����������Ȼ�ᷢ�������
			}
		}
		dbUtils.close(); // �ر����ݿ�����}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

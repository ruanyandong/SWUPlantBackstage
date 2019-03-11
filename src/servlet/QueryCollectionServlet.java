package servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.BaseBean;
import bean.CollectionBean;
import db.DBUtils;

@WebServlet("/QueryCollectionServlet")
public class QueryCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public QueryCollectionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String username = request.getParameter("username");
	
	BaseBean baseBean = new BaseBean();
	CollectionBean collectionBean =new CollectionBean();
	DBUtils dbUtils =new DBUtils();
	Writer writer =response.getWriter();
	List<String> plants ;
	if (username == null) {
		baseBean.setCode(100);
		baseBean.setMsg("参数为空");
		baseBean.setData(collectionBean);
	}else {
		boolean isSuccess = dbUtils.openConnection();
		if (isSuccess) {
			plants = dbUtils.queryCollection(username);
			if (plants != null ) {
				baseBean.setCode(200);
				baseBean.setMsg("查询成功");
			}else {
				baseBean.setCode(400);
				baseBean.setMsg("查询失败");
			}
			collectionBean.setUsername(username);
			collectionBean.setPlantNames(plants);
			baseBean.setData(collectionBean);
		}else {
			baseBean.setCode(500);
			baseBean.setMsg("数据库错误");
			baseBean.setData(collectionBean);
		}
		
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

package servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import bean.BaseBean;
import bean.CollectionBean;
import db.DBUtils;

@WebServlet("/PlantCollectionServlet")
public class PlantCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PlantCollectionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String isCollection = request.getParameter("isCollection");
		String username = request.getParameter("username");
		String plantNames = request.getParameter("plantName");
		
		System.out.println("是否收藏："+isCollection);
		System.out.println("用户名："+username);
		System.out.println("植物名："+plantNames);
		
		BaseBean baseBean = new BaseBean();
		CollectionBean collectionBean = new CollectionBean();
		Writer writer = response.getWriter();
		DBUtils dbUtils = new DBUtils();
		
		if (isCollection == null || username == null || plantNames == null) {
			System.out.println("请求参数为空");
			baseBean.setCode(100);
			baseBean.setMsg("请求参数为空");
			baseBean.setData(collectionBean);
		}else {
			String[] plants = plantNames.split(",");
			boolean whetherCollection = Boolean.valueOf(isCollection);
			boolean isSuccess = dbUtils.openConnection();
			if (isSuccess) {
				if (whetherCollection) {
					boolean isInsert=dbUtils.batchInsertCollection(plants, username);
					if (isInsert) {
						baseBean.setCode(200);
						baseBean.setMsg("收藏成功");
					}else {
						baseBean.setCode(400);
						baseBean.setMsg("收藏失败");
					}
				}else {
					boolean isDelete = dbUtils.batchDeleteCollection(plants, username);
					if (isDelete) {
						baseBean.setCode(200);
						baseBean.setMsg("取消收藏成功");
					}else {
						baseBean.setCode(400);
						baseBean.setMsg("取消收藏失败");
					}
				}
				collectionBean.setUsername(username);
				collectionBean.setPlantNames(Arrays.asList(plants));
				baseBean.setData(collectionBean);
			}else {
				baseBean.setCode(500);
				baseBean.setMsg("数据库连接失败");
				baseBean.setData(collectionBean);
			}
		}
		
		Gson gson =new Gson();
		String result = gson.toJson(baseBean);
		writer.write(result);
		dbUtils.close();
		writer.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}

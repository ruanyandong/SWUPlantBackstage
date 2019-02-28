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

import utils.ReadXmlToString;
import utils.XmlParser;

@WebServlet("/PlantFernServlet")
public class PlantFernServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public PlantFernServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String filePath = this.getClass().getResource("/plantFern.xml").getPath();
		String fernXml = ReadXmlToString.getInstance().readXml(filePath);
		List<String> fernList = XmlParser.newInstance().parseXmlToStringList(fernXml);
		
		Gson gson = new Gson();
		String fernJson = gson.toJson(fernList);
		
		Writer writer = response.getWriter();
		if (writer != null) {
			writer.write(fernJson);
		}
		if (writer != null) {
			writer.close();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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

@WebServlet("/PlantBambooServlet")
public class PlantBambooServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PlantBambooServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String filePath = this.getClass().getResource("/plantBamboo.xml").getPath();
		String bambooXml = ReadXmlToString.getInstance().readXml(filePath);
		List<String> bambooList = XmlParser.newInstance().parseXmlToStringList(bambooXml);
		
		Gson gson = new Gson();
		String bambooJson = gson.toJson(bambooList);
		
		Writer writer = response.getWriter();
		if (writer != null) {
			writer.write(bambooJson);
		}
		if (writer != null) {
			writer.close();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

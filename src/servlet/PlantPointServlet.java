package servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.PointInfo;
import utils.ReadXmlToString;
import utils.XmlParser;

@WebServlet("/PlantPointServlet")
public class PlantPointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PlantPointServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String plantPointPlantFilePath = this.getClass().getResource("/plantPoint.xml").getPath();
		String pointXml = ReadXmlToString.getInstance().readXml(plantPointPlantFilePath);
		
		List<List<String>> pointList = XmlParser.newInstance().parseXmlToPointList(pointXml);
		List<PointInfo> pointInfos = new ArrayList<>();
		
		for(int i = 0 ; i < pointList.size() ; i++) {
			List<String> childPoint = pointList.get(i);
			
			double latitude = Double.valueOf(childPoint.get(0));
            double longitude = Double.valueOf(childPoint.get(1));
            List<String> nameList = new ArrayList<>();
            for (int j=2;j<childPoint.size();j++){
                nameList.add(childPoint.get(j));
            }
            int pointNumber = i;
            
            PointInfo pointInfo= new PointInfo();
            
            pointInfo.setPointNumber(pointNumber);
            pointInfo.setLatitude(latitude);
            pointInfo.setLongitude(longitude);
            pointInfo.setPlantNameList(nameList);
            
            pointInfos.add(pointInfo);
		}
		
		Gson gson = new Gson();
		String plantPointInfo = gson.toJson(pointInfos);
		
		Writer writer = response.getWriter();
		if (writer != null) {
			writer.write(plantPointInfo);
		}
		
		if (writer != null) {
			writer.close();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

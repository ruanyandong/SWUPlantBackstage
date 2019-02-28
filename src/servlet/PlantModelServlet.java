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
import bean.PlantModel;
import utils.ImageURL;
import utils.ReadXmlToString;
import utils.XmlParser;

@WebServlet("/PlantModelServlet")
public class PlantModelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PlantModelServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
	
		String plantChineseNameFilePath = this.getClass().getResource("/plantCN.xml").getPath();
		String plantEnglishNameFilePath = this.getClass().getResource("/plantEN.xml").getPath();
		String plantAttributeFilePath = this.getClass().getResource("/plantAttribute.xml").getPath();
		String plantDescriptionFilePath = this.getClass().getResource("/plantDescription.xml").getPath();
		String plantDistributionFilePath = this.getClass().getResource("/plantDistribution.xml").getPath();
		String plantImageURLFilePath = this.getClass().getResource("/plantImage.xml").getPath();
		
		String chineseNameXml = ReadXmlToString.getInstance().readXml(plantChineseNameFilePath);
		String englishNameXml = ReadXmlToString.getInstance().readXml(plantEnglishNameFilePath);
		String attributeXml = ReadXmlToString.getInstance().readXml(plantAttributeFilePath);
		String descriptionXml = ReadXmlToString.getInstance().readXml(plantDescriptionFilePath);
		String distributionXml = ReadXmlToString.getInstance().readXml(plantDistributionFilePath);
		String imageNameXml = ReadXmlToString.getInstance().readXml(plantImageURLFilePath);

		List<String> chineseNameList = XmlParser.newInstance().parseXmlToStringList(chineseNameXml);
		List<String> englishNameList = XmlParser.newInstance().parseXmlToStringList(englishNameXml);
		List<String> attributeList = XmlParser.newInstance().parseXmlToStringList(attributeXml);
		List<String> descriptionList = XmlParser.newInstance().parseXmlToStringList(descriptionXml);
		List<String> distributionList = XmlParser.newInstance().parseXmlToStringList(distributionXml);
		List<String> imageNameList = XmlParser.newInstance().parseXmlToStringList(imageNameXml);

		List<String> imageURLList = new ArrayList<>();
		for(int i = 0;i<imageNameList.size();i++) {
			String imageURL = ImageURL.getImageBaseUrl()+imageNameList.get(i)+".png";
			System.out.println(imageURL);
			imageURLList.add(imageURL);
		}

		List<PlantModel> plantModels = new ArrayList<>();
		for(int i = 0;i<chineseNameList.size();i++) {
			PlantModel plantModel = new PlantModel();
			plantModel.setPlantChineseName(chineseNameList.get(i));
			plantModel.setPlantEnglishName(englishNameList.get(i));
			plantModel.setPlantProperty(attributeList.get(i));
			plantModel.setPlantDescription(descriptionList.get(i));
			plantModel.setPlantDistribution(distributionList.get(i));
			plantModel.setPlantImageURL(imageURLList.get(i));
			plantModels.add(plantModel);
		}
		
		Gson gson = new Gson();
		String plantModelList = gson.toJson(plantModels);
		
		Writer writer = response.getWriter();
		if (writer != null) {
			writer.write(plantModelList);
		}
		
		if (writer != null) {
			writer.close();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

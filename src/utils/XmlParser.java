package utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlParser {
	
	private XmlParser() {
	}
	private static final class SingleTon{
		private static final XmlParser INSTANCE = new XmlParser();
	}
	public static XmlParser newInstance() {
		return SingleTon.INSTANCE;
	}
	
	public List<String> parseXmlToStringList(String xml){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		List<String> list = new ArrayList<>();
		try {
			DocumentBuilder  builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
			Element element  = document.getDocumentElement();
			NodeList nodeList = element.getElementsByTagName("item");
			for(int i = 0;i<nodeList.getLength();i++) {
				String nodeContent = nodeList.item(i).getTextContent();
				System.out.println(nodeContent);
				list.add(nodeContent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// https://blog.csdn.net/banana1006034246/article/details/83383400
	public List<List<String>> parseXmlToPointList(String xml){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		List<List<String>> list = new ArrayList<>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getChildNodes();
			for(int i = 0;i<nodeList.getLength();i++) {
				Node node = nodeList.item(i);
				//System.out.println("Node name is "+node.getNodeName());
				//System.out.println("Node type is "+node.getNodeType());
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodeList = node.getChildNodes();
					List<String> stringList = new ArrayList<>();
					for(int j = 0;j<childNodeList.getLength();j++) {
						Node node2 = childNodeList.item(j);
//						System.out.println(">>>>>child node name===>"+node2.getNodeName());
//						System.out.println(">>>>>child node type===>"+node2.getNodeType());
						if (node2.getNodeName().equals("item")) {
//							System.out.println("item type is "+node2.getFirstChild().getNodeType());
//							System.out.println("item value is "+node2.getFirstChild().getNodeValue());
							stringList.add(node2.getFirstChild().getNodeValue());
						}
					}
					list.add(stringList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}


}

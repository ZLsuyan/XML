package hit.dom4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 使用DOM4J解析XML文档、生成XML文件
 * 
 * @author zengli
 * @date 2016/5/29
 */
public class DOM4JTest {
	public static void main(String[] args) {
		// 使用SAX的方式进行读取
		SAXReader reader = new SAXReader();
		try {
			// 读取XML文档，返回一个Document对象
			Document document = reader.read(new File("User.hbm.xml"));
			// 取得Root节点
			Element rootElement = document.getRootElement();
			p(rootElement.getName());

			/*
			 * DOM4J提供至少三种遍历节点的方法： 1、枚举（Iterator）：
			 */
			for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				p(element.getName());
				// 获得子节点class的子节点，两个property
				for (Iterator j = element.elementIterator(); j.hasNext();) {
					Element element2 = (Element) j.next();
					p("------" + element2.getName());
				}
				/*
				 * 2、枚举名称为foo (这里枚举的是class元素下的名称为property的节点) 的节点
				 */
				for (Iterator t = element.elementIterator("property"); t
						.hasNext();) {
					Element foo = (Element) t.next();
					p(foo.getName());
				}
			}

			/*
			 * 2、枚举名称为foo的节点
			 */
			for (Iterator i = rootElement.elementIterator("class"); i.hasNext();) {
				Element foo = (Element) i.next();
				p(foo.getName());
			}

			// for(Iterator j = rootElement.attributeIterator();j.hasNext();){
			// Attribute attribute = (Attribute)j.next();
			// p(attribute.getName()+"-"+attribute.getValue());
			// }
			//			
			//			
			// 使用XPath访问节点
			List<Node> list = document.selectNodes("//hibernate-mapping/class/property");
			for (Node n : list) {
				p(n.getName());
				p(n.valueOf("@name"));
			}

			// 取得所有符合XPath中的所有节点中的第一个节点
			Node node = document.selectSingleNode("//hibernate-mapping/class/property");
			p(node.valueOf("@name"));
			
			
			createDocument();

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void p(Object o) {
		System.out.println(o);
	}

	
	//生成XML文件  
	public static Document createDocument(){
		Document document = DocumentHelper.createDocument();
		//加入根节点
		Element root = document.addElement("hibernate-mapping");
		//链式编程
		Element classElement = root.addElement("class")
		.addAttribute("name", "hit.User")
		.addAttribute("table", "t_user");
		classElement
		.addElement("property")
		.addAttribute("name", "username");
//		classElement
//		.addAttribute("name", "password");
		
//		//文件输出
//		FileWriter out;
//		try {
//			out = new FileWriter("User2.hbm.xml");
//			document.write(out);
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		//美化格式的输出
		OutputFormat format = OutputFormat.createPrettyPrint();		
		try {
			XMLWriter writer = new XMLWriter(
					new FileWriter("User2.hbm.xml"),format
					);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
			
		return document;
	}
}

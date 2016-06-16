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
 * ʹ��DOM4J����XML�ĵ�������XML�ļ�
 * 
 * @author zengli
 * @date 2016/5/29
 */
public class DOM4JTest {
	public static void main(String[] args) {
		// ʹ��SAX�ķ�ʽ���ж�ȡ
		SAXReader reader = new SAXReader();
		try {
			// ��ȡXML�ĵ�������һ��Document����
			Document document = reader.read(new File("User.hbm.xml"));
			// ȡ��Root�ڵ�
			Element rootElement = document.getRootElement();
			p(rootElement.getName());

			/*
			 * DOM4J�ṩ�������ֱ����ڵ�ķ����� 1��ö�٣�Iterator����
			 */
			for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				p(element.getName());
				// ����ӽڵ�class���ӽڵ㣬����property
				for (Iterator j = element.elementIterator(); j.hasNext();) {
					Element element2 = (Element) j.next();
					p("------" + element2.getName());
				}
				/*
				 * 2��ö������Ϊfoo (����ö�ٵ���classԪ���µ�����Ϊproperty�Ľڵ�) �Ľڵ�
				 */
				for (Iterator t = element.elementIterator("property"); t
						.hasNext();) {
					Element foo = (Element) t.next();
					p(foo.getName());
				}
			}

			/*
			 * 2��ö������Ϊfoo�Ľڵ�
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
			// ʹ��XPath���ʽڵ�
			List<Node> list = document.selectNodes("//hibernate-mapping/class/property");
			for (Node n : list) {
				p(n.getName());
				p(n.valueOf("@name"));
			}

			// ȡ�����з���XPath�е����нڵ��еĵ�һ���ڵ�
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

	
	//����XML�ļ�  
	public static Document createDocument(){
		Document document = DocumentHelper.createDocument();
		//������ڵ�
		Element root = document.addElement("hibernate-mapping");
		//��ʽ���
		Element classElement = root.addElement("class")
		.addAttribute("name", "hit.User")
		.addAttribute("table", "t_user");
		classElement
		.addElement("property")
		.addAttribute("name", "username");
//		classElement
//		.addAttribute("name", "password");
		
//		//�ļ����
//		FileWriter out;
//		try {
//			out = new FileWriter("User2.hbm.xml");
//			document.write(out);
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		//������ʽ�����
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

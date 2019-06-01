package tools;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.print.attribute.standard.PrinterLocation;
import javax.swing.JOptionPane;
import javax.swing.RootPaneContainer;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ClientOperationXML 
{
	public static void main(String[] args) {
	ClientOperationXML xml = new ClientOperationXML();
	System.out.println(xml.ReadNode("473034128", "question"));
}
	//indexes索引属性(ID)  key需求属性(need) value修改成的值(result)
	public void ChangeXmlDocument(String index, String key, String value)
	{
		try 
		{
			//使用 SAXReader 解析 XML 文档
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read("system/Document/ClientUserInformation.xml");
			//获取节点
			Element root = document.getRootElement();
			List<Element> ElementList = root.elements();
			
			for (Element element : ElementList)
			{
				if (element.attribute("ID").getValue().equals(index))
				{
					List<Attribute> AttributesList = element.attributes();
					for (Attribute attribute : AttributesList)
					{
						if (attribute.getName().equals(key))
						{
							attribute.setValue(value);
							System.out.println(attribute.getValue());
							break;
						}
					}
					break;
				}
				else
				{
					System.out.println("用户不存在！");
				}
			}
			
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			outputFormat.setEncoding("utf-8");
			Writer out;
			try {
				 out = new FileWriter("system/Document/ClientUserInformation.xml");
				 XMLWriter writer = new XMLWriter(out, outputFormat);
				 writer.write(document);
				 writer.close();
				 System.out.println("修改数据成功！");
			} catch (Exception e) {
				System.out.println("修改数据失败！");
			}
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void DeleNode(String ID)
	{
		try 
		{
			//使用 SAXReader 解析 XML 文档
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read("system/Document/ClientUserInformation.xml");
			//获取节点
			Element root = document.getRootElement();List<Element> ElementList = root.elements();
			Boolean flag = true;
			for (Element element : ElementList)
			{
				System.out.println(element.attribute("ID").getValue());
				if (element.attribute("ID").getValue().equals(ID))
				{
					flag = false;
					root.remove(element);
					break;
				}
			}
			if (!flag)
			{
				System.out.println("用户信息注销成功！");
			}
			else
			{
				System.out.println("用户不存在！");
			}
			
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			outputFormat.setEncoding("utf-8");
			Writer out;
			
			 try {
				 out = new FileWriter("system/Document/ClientUserInformation.xml");
				 XMLWriter writer = new XMLWriter(out, outputFormat);
				 writer.write(document);
				 writer.close();
			} catch (Exception e) {
				System.out.println("用户信息注销失败！");
			}
		
			
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String ReadNode(String index, String key)
	{
		String temp = null;
		try 
		{
			//使用 SAXReader 解析 XML 文档
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read("system/Document/ClientUserInformation.xml");
			//获取节点
			Element root = document.getRootElement();
			List<Element> elementsList = root.elements();
			
			for (Element element : elementsList)
			{
				if (element.attribute("ID").getValue().equals(index))
				{
					;
					if ((temp = element.attribute(key).getValue()) != null)
					{
						return temp;
					}
					else
						return null;
				}
			}
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
		return temp;
	}
	public void AddXmlNode(String ID, String name,String question, 
			String answer, String portraitNum, String autograph)
	{
		
		if (ReadNode(ID, "ID") == null)
		{
			try 
			{
				//使用 SAXReader 解析 XML 文档
				SAXReader saxReader = new SAXReader();
				Document document = saxReader.read("system/Document/ClientUserInformation.xml");
				Element root = document.getRootElement();
				Element element = root.addElement("User");
				//子节点赋予ID属性
				element.addAttribute("ID", ID);
				//给子节点追加节点，用于存放昵称，头像
				element.addAttribute("name", name);
				element.addAttribute("question", question);
				element.addAttribute("answer", answer);
				element.addAttribute("portraitNum",portraitNum);
				element.addAttribute("autograph", autograph);
				
				OutputFormat outputFormat = OutputFormat.createPrettyPrint();
				outputFormat.setEncoding("utf-8");
				Writer out;
				
				out = new FileWriter("system/Document/ClientUserInformation.xml");
				 XMLWriter writer = new XMLWriter(out, outputFormat);
				 writer.write(document);
				 writer.close();
				 System.out.println("添加信息成功！");
			} 
			catch (Exception e) 
			{
				
				 System.out.println("添加信息失败！");
			}
		}
		else
		{
			System.out.println("用户已存在！");
		}
		
	}
	
	
	public void CreateXmlDocument(String ID, String name,String question, String answer,
			String portraitNum, String autograph)
	{
		//创建一个xml文档
		Document document = DocumentHelper.createDocument();
		//创建一个根节点
		Element root = document.addElement("Information");
		//给根节点追加一个子节点
		Element element = root.addElement("User");
		//子节点赋予ID属性
		element.addAttribute("ID", ID);
		//给子节点追加节点，用于存放昵称，头像
		element.addAttribute("name", name);
		element.addAttribute("question", question);
		element.addAttribute("answer", answer);
		element.addAttribute("portraitNum",portraitNum);
		element.addAttribute("autograph", autograph);
		
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setEncoding("utf-8");
		Writer out;
		try {
			 out = new FileWriter("system/Document/ClientUserInformation.xml");
			 XMLWriter writer = new XMLWriter(out, outputFormat);
			 writer.write(document);
			 writer.close();
			 System.out.println("生成文件成功！");
		} catch (Exception e) {
			System.out.println("生成文件失败！");
		}
	}
}

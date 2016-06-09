package com.ep.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ep.Factory;

/**
 * 配置文件
 * 
 * @author zhaohui
 * 
 */
public class ConfigInfo {
	private String language;// 语言
	private String endfix;// 后缀
	private String procRoot;// 协议生成路径
	private String beanRoot;// bean生成路径
	private String encode;// 编码
	private String charSet;// 字符集

	private List<File> fileList = new ArrayList<File>();// 模块协议文件列表

	private String configXml;
	private String progenXml;

	public ConfigInfo(String configXml, String progenXml) {
		this.configXml = configXml;
		this.progenXml = progenXml;
	}

	public void init() {
		try {
			Element outputRoot = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(configXml).getDocumentElement();
			// 相关参数
			System.out.println(outputRoot.getElementsByTagName("genout")
					.getLength());
			Element output = (Element) outputRoot
					.getElementsByTagName("genout").item(0);
			setBeanRoot(output.getAttribute("beanRoot"));
			setProcRoot(output.getAttribute("procRoot"));
			setLanguage(output.getAttribute("language"));
			setEncode(output.getAttribute("encode"));
			setEndfix(Factory.getEndfix(getLanguage()));
			setCharSet(output.getAttribute("charset"));
			String rootPath = output.getAttribute("rootPath");
			if (null != rootPath && !rootPath.endsWith("/")) {
				rootPath += "/";
			}

			Element root = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(progenXml).getDocumentElement();
			// 协议文件
			NodeList inputList = root.getElementsByTagName("file");
			int manSize = inputList.getLength();
			for (int m = 0; m < manSize; ++m) {
				Element input = (Element) inputList.item(m);
				String fileName = rootPath + input.getAttribute("name");
				File file = new File(fileName);
				getFileList().add(file);
			}
		} catch (Exception e) {
			throw new RuntimeException("init config error !", e);
		}
	}

	public void checkConfig() {
		if (null == language || null == beanRoot || null == procRoot
				|| null == encode || null == endfix || null == charSet) {
			usage();
			throw new RuntimeException(" config param error ! ");
		}
		if (getFileList().isEmpty()) {
			throw new RuntimeException(" filelist is empty error !");
		}
	}

	private static void usage() {
		System.out.println("options");
		System.out.println("    -language dest code computer language");
		System.out.println("    -beanroot dest beans generate root path");
		System.out.println("    -procRoot dest protocols generate root path");
		System.out.println("    -encode output file encode ");
		System.exit(0);
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getProcRoot() {
		return procRoot;
	}

	public void setProcRoot(String procRoot) {
		this.procRoot = procRoot;
	}

	public String getBeanRoot() {
		return beanRoot;
	}

	public void setBeanRoot(String beanRoot) {
		this.beanRoot = beanRoot;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	public String getEndfix() {
		return endfix;
	}

	public void setEndfix(String endfix) {
		this.endfix = endfix;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

}

package com.ep.genpro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ep.Helper;
import com.ep.config.Bean;
import com.ep.config.Protocol;

/**
 * 协议文件结构
 * @author zhaohui
 *
 */
public abstract class Struct {

	protected final Set<String> importfile = new HashSet<String>();
	protected final Set<String> namespaces = new HashSet<String>();

	protected final Map<String, Element> beans = new HashMap<String, Element>();
	protected final Map<String, Bean> beanStructs = new TreeMap<String, Bean>();

	protected final Set<Integer> types = new HashSet<Integer>();
	protected final Map<String, Element> protocols = new HashMap<String, Element>();
	protected final Map<String, Protocol> protocolStructs = new TreeMap<String, Protocol>();

	/**
	 * 解析协议文档
	 * @param xml
	 */
	public void parseFileStruct(String xml) {
		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(xml);
			Element root = doc.getDocumentElement();
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i != nodes.getLength(); ++i) {
				Node n = nodes.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) n;
					if ("Import".equals(element.getNodeName())) {
						String filename = getFullName(xml,
								element.getAttribute("file"));
						if (importfile.contains(filename)) {
							continue;
						}
						parseFileStruct(filename);
						importfile.add(filename);
					}
					if ("Namespace".equals(element.getNodeName())) {
						String namespace = element.getAttribute("name");
						if (namespace.equals("")) {
							throw new RuntimeException(
									"protocol name valid: no name");
						}
						namespaces.add(namespace);
						analyzeBeans(namespace, element);
						analyzeProtocols(namespace, element);
					}
				}
			}

			for (int i = 0; i != nodes.getLength(); ++i) {
				Node n = nodes.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) n;
					if (element.getNodeName().equals("Namespace")) {
						String namespace = element.getAttribute("name");
						for (Entry<String, Element> e : beans.entrySet()) {
							if (inThisNameSpace(namespace, e.getKey()))
								verifyBean(namespace, e.getKey(), e.getValue());
						}
						for (Entry<String, Element> e : protocols.entrySet()) {
							if (inThisNameSpace(namespace, e.getKey()))
								verifyProtocol(namespace, e.getKey(),
										e.getValue());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 解析bean
	 * @param namespace
	 * @param element
	 */
	private void analyzeBeans(String namespace, Element element) {
		NodeList allNodes = element.getChildNodes();
		for (int i = 0; i != allNodes.getLength(); ++i) {
			Node n = allNodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				if (e.getNodeName().equals("Bean")) {
					String beanName = Helper.changeFirstCharName(
							e.getAttribute("name"), true);
					if (null != namespace && !namespace.equals(""))
						beanName = namespace + "." + beanName;
					if (beanName.contains("_"))
						throw new RuntimeException(
								"bean can not use name that contains _");
					if (beans.containsKey(beanName))
						throw new RuntimeException("duplicate bean name:"
								+ beanName);
					beans.put(beanName, e);
				}
			}
		}
	}

	/**
	 * 解析protocol
	 * @param namespace
	 * @param element
	 */
	private void analyzeProtocols(String namespace, Element element) {
		NodeList allNodes = element.getChildNodes();
		for (int i = 0; i != allNodes.getLength(); ++i) {
			Node n = allNodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				if (e.getNodeName().equals("Protocol")) {
					String protocolName = Helper.changeFirstCharName(
							e.getAttribute("name"), true);
					if (null != namespace && !namespace.equals(""))
						protocolName = namespace + "." + protocolName;
					if (beans.containsKey(protocolName)) {
						throw new RuntimeException(
								"protocol can not use duplicate name with bean, name:"
										+ protocolName);
					}
					if (protocols.containsKey(protocolName)) {
						throw new RuntimeException("duplicate protocol name:"
								+ protocolName);
					}
					protocols.put(protocolName, e);
				}
			}
		}
	}

	private final void verifyBean(String namespace, String beanName,
			Element bean) {
		NodeList childNodes = bean.getChildNodes();
		if (childNodes.getLength() == 0)
			throw new RuntimeException("bean:" + bean.getNodeName()
					+ " has no variables.");
		Bean bs = new Bean(beanName, getBeanComment(bean));
		beanStructs.put(beanName, bs);
		Set<String> variableNames = new HashSet<String>();
		for (int i = 0; i != childNodes.getLength(); ++i) {
			Node v = childNodes.item(i);
			if (v.getNodeType() != Node.ELEMENT_NODE)
				continue;
			Element node = (Element) v;
			if (node.getNodeName() == "variable") {
				String vName = node.getAttribute("name");
				if (variableNames.contains(vName))
					throw new RuntimeException("bean:" + beanName
							+ " variable:" + vName + " duplicate");
				variableNames.add(vName);
				Variable variable = verifyVariable(namespace, "bean", beanName,
						vName, node);
				bs.putVariable(variable);
			} else {
				throw new RuntimeException("bean:" + beanName + " 's node "
						+ node.getNodeName() + " invalid");
			}
		}
	}

	private void verifyProtocol(String namespace, String protocolName,
			Element protocol) {
		NodeList childNodes = protocol.getChildNodes();
		if (childNodes.getLength() == 0) {
			throw new RuntimeException("protocol:" + protocol.getNodeName()
					+ " has no variables.");
		}
		if (!protocol.hasAttribute("type")) {
			throw new RuntimeException("protocol:" + protocolName
					+ " has no type");
		}
		if (!protocol.hasAttribute("maxsize")) {
			throw new RuntimeException("protocol:" + protocolName
					+ " has no maxsize");
		}
		int type = Integer.valueOf(protocol.getAttribute("type"));
		int maxsize = Integer.valueOf(protocol.getAttribute("maxsize"));
		if (types.contains(type))
			throw new RuntimeException("protocol:" + protocolName
					+ " has duplicate type:" + type);
		types.add(type);
		Protocol p = new Protocol(protocolName, getBeanComment(protocol), type,
				maxsize);
		protocolStructs.put(protocolName, p);
		Set<String> variableNames = new HashSet<String>();
		for (int i = 0; i != childNodes.getLength(); ++i) {
			Node v = childNodes.item(i);
			if (v.getNodeType() != Node.ELEMENT_NODE)
				continue;
			Element node = (Element) v;
			if ("variable".equals(node.getNodeName())) {
				String vName = node.getAttribute("name");
				if (variableNames.contains(vName)) {
					throw new RuntimeException("protocol:" + protocolName
							+ " variable:" + vName + " duplicate");
				}
				variableNames.add(vName);
				Variable variable = verifyVariable(namespace, "protocol",
						protocolName, vName, node);
				p.putVariable(variable);
			} else {
				throw new RuntimeException("protocol:" + protocolName
						+ " 's node " + node.getNodeName() + " invalid");
			}
		}
	}

	private Variable verifyVariable(String namespace, String nodeType,
			String nodeName, String vName, Element variable) {
		String vType = getFormatType(namespace, variable.getAttribute("type"));
		if (vType.equals(nodeName)) {
			throw new RuntimeException(nodeType + ":" + nodeName + " variable:"
					+ vName + " is this bean self");
		}
		if (!isValidBeanValue(vType)) {
			throw new RuntimeException(nodeType + ":" + nodeName + " variable:"
					+ vName + " type:" + vType + " has not defined");
		}
		String vDefault = null;
		if (variable.hasAttribute("default")) {
			if (isInnerType(vType)) {
				Type t = getInnerType(vType);
				vDefault = variable.getAttribute("default");
				if (!t.isInitialValid(vDefault))
					throw new RuntimeException(nodeType + ":" + nodeName
							+ " variable:" + vName + " type:" + vType
							+ " default invalid");
			} else
				throw new RuntimeException(nodeType + ":" + nodeName
						+ " variable:" + vName + " type:" + vType
						+ " can not use default");
		}
		String vKey = null;
		String vValue = null;
		if (vType.equals("List") || vType.equals("Set")
				|| vType.equals("Array") || vType.equals("Vector")) {
			vValue = getFormatType(namespace, variable.getAttribute("value"));
			if (!isValidBeanValue(vValue))
				throw new RuntimeException(nodeType + ":" + nodeName
						+ " variable:" + vName + " type " + vType + "'s value:"
						+ vValue + " has not defined or was container");
		} else if (vType.equals("Map")) {
			vKey = getFormatType(namespace, variable.getAttribute("key"));
			vValue = getFormatType(namespace, variable.getAttribute("value"));
			if (!isValidBeanValue(vKey))
				throw new RuntimeException(nodeType + ":" + nodeName
						+ " variable:" + vName + " type map's key:" + vKey
						+ " has not defined or was container");
			if (!isValidBeanValue(vValue))
				throw new RuntimeException(nodeType + ":" + nodeName
						+ " variable:" + vName + " type map's value:" + vValue
						+ " has not defined or was container");
		}
		return variable(vName, vType, vDefault, vKey, vValue,
				getComment(variable));
	}

	protected abstract String getFormatType(String namespace, String xmlType);

	/**
	 * 检查是否是有效的类型(包括原始类型和bean)
	 * @param value
	 * @return
	 */
	protected abstract boolean isValidBeanValue(String value);

	/**
	 * 获取基础类型
	 * @param vType
	 * @return
	 */
	protected abstract Type getInnerType(String vType);

	/**
	 * 是否是基础类型
	 * @param vType
	 * @return
	 */
	protected abstract boolean isInnerType(String vType);

	/**
	 * 实例化bean和protocol的Variable
	 * @param vName
	 * @param vType
	 * @param vDefault
	 * @param vKey
	 * @param vValue
	 * @param commnet
	 * @return
	 */
	protected abstract Variable variable(String vName, String vType,
			String vDefault, String vKey, String vValue, String commnet);

	/**
	 * 检查bean和protocol是否在命名空间内
	 * @param namespace
	 * @param name
	 * @return
	 */
	private boolean inThisNameSpace(String namespace, String name) {
		int index = name.lastIndexOf(".");
		String p = name.substring(0, index);
		return p.equals(namespace);
	}

	/**
	 * 获取import文件的路径
	 * 
	 * @param orgFile
	 * @param importName
	 * @return
	 */
	private String getFullName(String orgFile, String importName) {
		if (null == orgFile) {
			throw new RuntimeException(" getFullName orgFile is null !");
		}
		int index1 = orgFile.lastIndexOf("/");
		int index2 = orgFile.lastIndexOf("\\");
		int length = orgFile.length();
		if ((index1 < 0 && index2 < 0) || index1 >= length || index2 >= length) {
			throw new RuntimeException(" getFullName orgFile=" + orgFile);
		}
		if (index1 >= 0) {
			return orgFile.substring(0, index1 + 1) + importName;
		} else {
			return orgFile.substring(0, index2 + 1) + importName;
		}
	}

	/**
	 * 获取bean的注释
	 * @param self
	 * @return
	 */
	private String getBeanComment(Element self) {
		String comment = "";
		// previous sibling comment
		for (Node c = self.getPreviousSibling(); null != c; c = c
				.getPreviousSibling()) {
			if (Node.ELEMENT_NODE == c.getNodeType()) {
				break;
			}
			if (Node.COMMENT_NODE == c.getNodeType()) {
				comment = c.getTextContent().trim();
				break;
			}
		}
		return comment.trim();
	}

	/**
	 * 获取Variable的注释
	 * @param n
	 * @return
	 */
	protected String getComment(Node n) {
		String comment = "";
		Node c = n.getNextSibling();
		if (c != null && Node.TEXT_NODE == c.getNodeType())
			comment = c.getTextContent().trim().replaceAll("[\r\n]", "");
		if (!comment.isEmpty())
			comment = " // " + comment;
		return comment.trim();
	}

	protected final String changeNamespace(String namespace, String name) {
		if (!name.contains(".") && null != namespace && !namespace.equals("")) {
			return namespace + "." + name;
		} else {
			return name;
		}
	}

	public Set<String> getNamespaces() {
		return namespaces;
	}

	public Map<String, Bean> getBeanStructs() {
		return beanStructs;
	}

	public Map<String, Protocol> getProtocolStructs() {
		return protocolStructs;
	}

}

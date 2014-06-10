package com.flow.js.jdom;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;

import jdk.nashorn.api.scripting.JSObject;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.w3c.dom.Document;

/**
 * http://www.w3schools.com/dom/dom_node.asp,
 * http://www.javascriptkit.com/domref/documentmethods.shtml
 */
public abstract class AbstractJsNode extends AbstractJsObject {
  public Element element;

  protected AbstractJsNode(Element element, String type) {
    super(type);
    this.element = element;
  }

  public static AbstractJsNode fromElement(Element element) {
    return element.getChildren().size() > 0 ? new JsNode(element, "Node") : new JsTextNode(element);
  }

  // ---------------------------
  // dom properties
  // ---------------------------
  @Override
  public Object getMember(String arg0) {
    switch (arg0) {
      case "attributes":
        return attributes();
      case "childNodes":
        return childNodes();
      case "firstChild":
        return firstChild();
      case "lastChild":
        return lastChild();
      case "nodeName":
        return nodeName();
      case "baseURI":
        return baseURI();
      case "textContent":
        return textContent();
      case "getAttribute":
        return new GetAttribute();
      case "hasAttributes":
        return new HasAttributes();
      case "hasChildNodes":
        return new HasChildNodes();
      case "nodeValue":
        return nodeValue();
      default:
        return null;
    }
  }

  public JsNamedNodeMap attributes() {
    LinkedHashMap<String, JSObject> m = new LinkedHashMap<>();
    element.getAttributes().forEach(att -> m.put(att.getName(), new JsAttr(att.getName(), att.getValue())));
    return new JsNamedNodeMap(m);
  }

  /**
   * Returns the absolute base URI of a node
   * 
   * @throws URISyntaxException
   */
  public String baseURI() {
    try {
      return element.getXMLBaseURI().toString();
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
  }

  protected JsNodeList childNodes() {
    return null;
  }

  protected AbstractJsObject firstChild() {
    return null;
  }

  protected AbstractJsObject lastChild() {
    return null;
  }

  protected String localName() {
    return null;
  }

  protected String namespaceURI() {
    return null;
  }

  protected AbstractJsNode nextSibling() {
    return null;
  }

  protected AbstractJsNode previousSibling() {
    return null;
  }

  protected String nodeName() {
    return element == null ? null : element.getName();
  }

  protected String nodeValue() {
    return toString();
  }

  protected AbstractJsNode parentNode() {
    return null;
  }

  protected String prefix() {
    return null;
  }

  protected String textContent() {
    return element.getValue();
  }

  // ---------------------------
  // dom methods
  // ---------------------------
  protected class HasAttributes extends JsFunction {
    @Override
    public Object call(Object thiz, final Object... args) {
      return element != null && element.hasAttributes();
    }
  }

  protected class HasChildNodes extends JsFunction {
    @Override
    public Object call(Object thiz, final Object... args) {
      return element != null && element.getChildren().size() > 0;
    }
  }

  protected class GetAttribute extends JsFunction {
    @Override
    public Object call(Object thiz, final Object... args) {
      if (element == null) return null;
      String attributeName = safeStringParam(0, args);
      Attribute at = element.getAttribute(attributeName);
      if (at != null) return new JsAttr(at);
      return null;
    }
  }

  /** Returns the root element (document object) for a node */
  public Document ownerDocument() {
    throw new UnsupportedOperationException("ownerDocument");
  }

  /** Returns whether the specified namespaceURI is the default */
  public boolean isDefaultNamespace(String s) {
    throw new UnsupportedOperationException("isDefaultNamespace");
  }

  /** Returns the namespace URI associated with a given prefix */
  public String lookupNamespaceURI(String s) {
    throw new UnsupportedOperationException("lookupNamespaceURI");
  }

  /** Returns the prefix associated with a given namespace URI */
  public String lookupPrefix(String s) {
    throw new UnsupportedOperationException("lookupPrefix");
  }
}

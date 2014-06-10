package com.flow.js.jdom;

import java.util.List;

import org.jdom2.Element;
import org.w3c.dom.Document;

/**
 * http://www.w3schools.com/dom/dom_node.asp,
 * http://www.javascriptkit.com/domref/documentmethods.shtml
 */
public class JsNode extends AbstractJsNode {
  protected JsNode(Element element, String type) {
    super(element, type);
    this.element = element;
  }

  protected JsNode(String type) {
    this(null, type);
  }

  // ---------------------------
  // dom properties
  // ---------------------------
  @Override
  public JsNodeList childNodes() {
    return new JsNodeList(element.getChildren());
  }

  @Override
  public AbstractJsNode firstChild() {
    List<Element> children = element.getChildren();
    return children == null || children.size() == 0 ? null : fromElement(children.get(0));
  }

  /** Returns the last child of a node */
  @Override
  public AbstractJsNode lastChild() {
    List<Element> children = element.getChildren();
    return children == null || children.size() == 0 ? null : fromElement(children.get(children.size() - 1));
  }

  /** Returns the local part of the name of a node */
  @Override
  public String localName() {
    return element.getName();
  }

  /** Returns the namespace URI of a node */
  @Override
  public String namespaceURI() {
    return element.getNamespaceURI();
  }

  /** Returns the node immediately following a node */
  @Override
  public AbstractJsNode nextSibling() {
    Element e = element.getParentElement();
    if (e != null) {
      boolean found = false;
      for (Element child : e.getChildren()) {
        if (found) return fromElement(child);
        if (child == this.element) found = true;
      }
    }
    return null;
  }

  /** Returns the node immediately before a node */
  @Override
  public AbstractJsNode previousSibling() {
    Element e = element.getParentElement();
    if (e != null) {
      int found = -1;
      List<Element> children = e.getChildren();
      if (children.size() < 2) return null;
      for (int i = 1; i < children.size(); i++)
        if (children.get(i) == this.element) {
          found = i;
          break;
        }
      return found == -1 ? null : fromElement(children.get(found - 1));
    }
    return null;
  }

  /** Returns the name of a node, depending on its type */
  @Override
  public String nodeName() {
    return element.getName();
  }

  /** Returns the parent node of a node */
  @Override
  public AbstractJsNode parentNode() {
    return fromElement(element.getParentElement());
  }

  /** Sets or returns the namespace prefix of a node */
  @Override
  public String prefix() {
    return element.getNamespacePrefix();
  }

  // ---------------------------
  // dom methods
  // ---------------------------
  /** Returns the root element (document object) for a node */
  @Override
  public Document ownerDocument() {
    throw new UnsupportedOperationException("ownerDocument");
  }

  /** Returns whether the specified namespaceURI is the default */
  @Override
  public boolean isDefaultNamespace(String s) {
    throw new UnsupportedOperationException("isDefaultNamespace");
  }

  /** Returns the namespace URI associated with a given prefix */
  @Override
  public String lookupNamespaceURI(String s) {
    throw new UnsupportedOperationException("lookupNamespaceURI");
  }

  /** Returns the prefix associated with a given namespace URI */
  @Override
  public String lookupPrefix(String s) {
    throw new UnsupportedOperationException("lookupPrefix");
  }
}

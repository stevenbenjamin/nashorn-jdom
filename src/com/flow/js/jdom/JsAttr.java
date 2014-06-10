package com.flow.js.jdom;

import org.jdom2.Attribute;

public class JsAttr extends AbstractJsObject {
  public final String name;
  public final String nodeValue;

  public JsAttr(String name, String value) {
    super("Attr");
    this.name = name;
    this.nodeValue = value;
  }

  public JsAttr(Attribute attr) {
    this(attr.getName(), attr.getValue());
  }

  @Override
  public Object getMember(String arg0) {
    switch (arg0) {
      case "isId":
        return "id".equals(name);
      case "localName":
      case "name":
      case "nodeName":
        return name;
      case "nodeType":
        return 2;
      case "nodeValue":
      case "textContent":
      case "value":
        return nodeValue;
      default:
        return null;
    }
  }
}

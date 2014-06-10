package com.flow.js.jdom;

public class JsAttr extends AbstractJsObject {
  public final String name;
  public final String nodeValue;

  public JsAttr(String name, String value) {
    super("Attr");
    this.name = name;
    this.nodeValue = value;
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

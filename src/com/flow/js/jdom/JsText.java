package com.flow.js.jdom;

import java.util.ArrayList;
import java.util.Collection;

public class JsText extends AbstractJsNode {
  public final String data;

  public JsText(String s) {
    super(null, "Text");
    data = s;
  }

  public int length() {
    return data == null ? 0 : data.length();
  }

  public boolean isElementContentWhitespace() {
    return data.matches(".*\\s.*");
  }

  @Override
  public Object getMember(String arg0) {
    switch (arg0) {
      case "data":
      case "textContent":
      case "nodeValue":
        return data;
      case "length":
        return length();
      case "isElementContentWhitespace":
        return isElementContentWhitespace();
      case "nodeType":
        return 3;
      default:
        return null;
    }
  }

  @Override
  public Object getSlot(int arg0) {
    return hasSlot(arg0) ? data.charAt(arg0) : null;
  }

  @Override
  public boolean hasSlot(int arg0) {
    return arg0 < data.length();
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public Collection<Object> values() {
    ArrayList<Object> al = new ArrayList<>();
    for (char c : data.toCharArray()) {
      al.add(c);
    }
    return al;
  }

  @Override
  protected JsNodeList childNodes() {
    return null;
  }

  @Override
  protected AbstractJsObject firstChild() {
    return null;
  }

  @Override
  protected AbstractJsObject lastChild() {
    return null;
  }
}

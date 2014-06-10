package com.flow.js.jdom;

import org.jdom2.Element;

/*
 * A JsNode that holds a single text-bearing element. This extra container level
 * is to make the js behavior more like the expected dom behavior.
 */
public class JsTextNode extends AbstractJsNode {
  public JsText textNode;

  protected JsTextNode(Element contentNode) {
    super(contentNode, "TextNode");
    this.textNode = new JsText(contentNode.getText());
  }

  @Override
  protected JsNodeList childNodes() {
    return new JsNodeList(this);
  }

  @Override
  protected AbstractJsObject firstChild() {
    return textNode;
  }

  @Override
  protected String nodeValue() {
    return textNode.data;
  }

  @Override
  protected AbstractJsObject lastChild() {
    return textNode;
  }
}

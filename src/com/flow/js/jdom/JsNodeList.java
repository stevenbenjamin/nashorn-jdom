package com.flow.js.jdom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;

public class JsNodeList extends AbstractJsObject {
  protected List<AbstractJsNode> _nodeList = new ArrayList<>();

  public JsNodeList(List<Element> elements) {
    super("NodeList");
    elements.forEach(x -> _nodeList.add(AbstractJsNode.fromElement(x)));
  }

  public JsNodeList(Iterator<Element> elements) {
    super("NodeList");
    while (elements.hasNext()) {
      _nodeList.add(AbstractJsNode.fromElement(elements.next()));
    }
  }

  public JsNodeList(AbstractJsNode node) {
    super("NodeList");
    _nodeList.add(node);
  }

  @Override
  public Object getMember(String name) {
    switch (name) {
      case "length":
        return _nodeList.size();
      case "item":
        return new Item();
      default:
        return super.getMember(name);
    }
  }

  protected class Item extends JsFunction {
    @Override
    public Object call(Object thiz, final Object... args) {
      Integer i = safeIntParam(0, args);
      return i < _nodeList.size() ? _nodeList.get(i) : null;
    }
  }

  @Override
  public boolean hasSlot(int i) {
    return i > -1 && i < _nodeList.size();
  }

  @Override
  public AbstractJsObject getSlot(int i) {
    return _nodeList.get(i);
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public void setSlot(int arg0, Object arg1) {
    _nodeList.set(arg0, (JsNode) arg1);
  }

  @Override
  public Collection<Object> values() {
    return new ArrayList<>(_nodeList);
  }
}

package com.flow.js.jdom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Stream;

import jdk.nashorn.api.scripting.JSObject;

public class JsNamedNodeMap extends AbstractJsObject {
  protected LinkedHashMap<String, JSObject> _nodeMap;

  public JsNamedNodeMap(LinkedHashMap<String, JSObject> m) {
    super("NamedNodeMap");
    _nodeMap = m;
  }

  @Override
  public Object getSlot(int arg0) {
    return arg0 < _nodeMap.size() ? new ArrayList<>(_nodeMap.values()).get(arg0) : null;
  }

  @Override
  public boolean hasSlot(int arg0) {
    return arg0 < _nodeMap.size();
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public boolean hasMember(String arg0) {
    return _nodeMap.containsKey(arg0);
  }

  @Override
  public Set<String> keySet() {
    return _nodeMap.keySet();
  }

  @Override
  public Collection<Object> values() {
    return new ArrayList<>(_nodeMap.values());
  }

  @Override
  public Object getMember(String arg0) {
    switch (arg0) {
      case "length":
        return _nodeMap.size();
      case "item":
        return new Item();
      case "getNamedItem":
        return new GetNamedItem();
      default:
        return super.getMember(arg0);
    }
  }

  protected class Item extends JsFunction {
    @Override
    public Object call(Object thiz, final Object... args) {
      Integer i = safeIntParam(0, args);
      if (_nodeMap.size() <= i) return null;
      Stream<JSObject> s = _nodeMap.values().stream();
      if (i > 0) s = s.skip(i - 1);
      return s.findFirst().orElse(null);
    }
  }

  protected class GetNamedItem extends JsFunction {
    @Override
    public Object call(Object thiz, final Object... args) {
      String name = safeStringParam(0, args);
      switch (name) {
        case "length":
          return _nodeMap.size();
        default:
          return _nodeMap.get(name);
      }
    }
  }
}

package com.flow.js.jdom;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;

/**
 * http://www.w3schools.com/dom/dom_node.asp,
 * http://www.javascriptkit.com/domref/documentmethods.shtml
 */
public class JsDocument extends JsNode {
  public final Document document;

  public static Document parse(String raw) throws SAXException, IOException, ParserConfigurationException,
      JDOMException {
    return new SAXBuilder().build(new StringReader(raw));
  }

  public JsDocument(String raw) throws SAXException, IOException, ParserConfigurationException, JDOMException {
    super(null, "Document");
    document = parse(raw);
    this.element = document.getRootElement();
  }

  @Override
  public Object getMember(String arg0) {
    switch (arg0) {
      case "nodeType":
        return 9;
      case "nodeName":
        return "#document";
      case "getElementById":
        return new GetElementById();
      case "getElementsByTagName":
        return new GetElementsByTagName();
      default:
        return super.getMember(arg0);
    }
  }

  // --------------------------------------------
  // dom method function objects
  // --------------------------------------------
  protected class GetElementById extends JsFunction {
    @Override
    public Object call(Object thiz, final Object... args) {
      final String arg = safeStringParam(0, args[0]);
      Filter<Element> f = new ElementFilter() {
        @Override
        public Element filter(Object content) {
          if (content instanceof Element) {
            Attribute a = ((Element) content).getAttribute("id");
            return a != null && a.getValue().equals(arg) ? ((Element) content) : null;
          }
          return null;
        }
      };
      Iterator<Element> iter = element.getDescendants(f);
      return iter.hasNext() ? fromElement(iter.next()) : null;
    }
  }

  /**
   * Returns an array of elements whose tag name matches the parameter
   */
  protected class GetElementsByTagName extends JsFunction {
    @Override
    public Object call(Object thiz, final Object... args) {
      String arg = safeStringParam(0, args[0]);
      return new JsNodeList(element.getDescendants(new ElementFilter(arg)));
    }
  }
}

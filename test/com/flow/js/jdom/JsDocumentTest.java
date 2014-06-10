package com.flow.js.jdom;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import org.junit.Assert;
import org.junit.Test;

public class JsDocumentTest {
  static String booksXml = "<bookstore>" + //
      "  <book category=\"cooking\" id=\"a\">" +          //
      "    <title lang=\"en\">Everyday Italian</title>" +          //
      "    <author>Giada De Laurentiis</author>" +          //
      "    <year>2005</year>" +          //
      "    <price>30.00</price>" +          //
      "  </book>" +          //
      "  <book category=\"web\" id=\"b\">" +          //
      "    <title lang=\"en\">XQuery Kick Start</title>" +          //
      "    <author>James McGovern</author>" +          //
      "    <author>Per Bothner</author>" +          //
      "    <year id=\"y\">2003</year>" +          //
      "    <price>49.99</price>" +          //
      "  </book>" +          //
      "<book/>" + "</bookstore>";

  @Test
  @SuppressWarnings("unchecked")
  public void testAttributes() throws ScriptException {
    Bindings b = eval("var node=doc.getElementsByTagName('book')[0];\n" + //
        "var attribute=node.attributes[0];\n" +     //
        "var txt = attribute.textContent;\n" +        //
        "var att1=node.getAttribute('category').textContent;\n" +         //
        "var att2=node.getAttribute('id').textContent;");
    Assert.assertEquals(b.get("attribute").getClass(), JsAttr.class);
    Assert.assertEquals(b.get("txt"), "cooking");
    Assert.assertEquals(b.get("att1"), "cooking");
    Assert.assertEquals(b.get("att2"), "a");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testParse() throws ScriptException {
    Bindings b = eval("var nodeName=doc.nodeName;\n" +        //
        "var nodeType=doc.nodeType;\n");
    Assert.assertEquals(b.get("doc").getClass(), JsDocument.class);
    Assert.assertEquals(b.get("nodeName"), "#document");
    Assert.assertEquals(b.get("nodeType"), 9);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testTextContent() throws ScriptException {
    Bindings b = eval("var node=doc.getElementsByTagName('book')[0];\n" +     //
        "var content=node.textContent;\n");
    Assert.assertEquals(b.get("content"), "    Everyday Italian    Giada De Laurentiis    2005    30.00  ");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetElementById() throws ScriptException {
    Bindings b = eval("var idNode=doc.getElementById('y');" + //
        "var textNode=idNode.childNodes[0];" +            //
        "var txt = textNode.nodeValue;");
    Assert.assertEquals(b.get("idNode").getClass(), JsTextNode.class);
    Assert.assertEquals(b.get("txt"), "2003");
  }

  @Test
  public void testNodeName() throws ScriptException {
    Bindings b = eval("var node = doc.getElementById('y');" + //
        "var nodeName=doc.getElementById('y').nodeName;");
    Assert.assertEquals(b.get("nodeName"), "year");
  }

  @Test
  public void testFirstChild() throws ScriptException {
    Bindings b = eval("var node = doc.getElementsByTagName('book')[0].firstChild;" + //
        "var nodeName=node.nodeName;" +        //
        "var nodeValue=node.firstChild.nodeValue");
    Assert.assertEquals(b.get("nodeName"), "title");
    Assert.assertEquals(b.get("nodeValue"), "Everyday Italian");
  }

  @Test
  public void testLastChild() throws ScriptException {
    Bindings b = eval("var node = doc.getElementsByTagName('book')[1].lastChild;" + //
        "var nodeName=node.nodeName;" +        //
        "var nodeValue=node.lastChild.nodeValue");
    Assert.assertEquals(b.get("nodeName"), "price");
    Assert.assertEquals(b.get("nodeValue"), "49.99");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetElementsByTagName() throws ScriptException {
    Bindings b = eval("var node=doc.getElementsByTagName('title')[0];" + //
        "var textNode=node.childNodes[0];" +     //
        "var txt = textNode.nodeValue;");
    Assert.assertEquals(b.get("node").getClass(), JsTextNode.class);
    Assert.assertEquals(b.get("txt"), "Everyday Italian");
  }

  @Test
  public void testGetElementsByTagNameIterate() throws ScriptException {
    Bindings b = eval("var arr=[];" + //
        "var nodes=doc.getElementsByTagName('title');\n" +        //
        "for (i=0;i<nodes.length;i++){\n" +        //
        "arr[i]=nodes[i].childNodes[0].nodeValue;\n" +        //
        "}");
    Object o = b.get("arr");
    ScriptObjectMirror sm = (ScriptObjectMirror) o;
    Assert.assertEquals(sm.getSlot(0), "Everyday Italian");
    Assert.assertEquals(sm.getSlot(1), "XQuery Kick Start");
  }

  @Test
  public void testJsText() throws ScriptException {
    JsText txt = new JsText("contents");
    Object o = evalObject("txt.data", "txt", txt);
    Assert.assertEquals(o, "contents");
    //
    o = evalObject("txt.length", "txt", txt);
    Assert.assertEquals(o, "contents".length());
    //
    o = evalObject("txt.isElementContentWhitespace", "txt", txt);
    Assert.assertEquals(o, Boolean.FALSE);
  }

  @Test
  public void testAttr() throws ScriptException {
    Object o = evalObject("attr.name", "attr", new JsAttr("k", "v"));
    Assert.assertEquals(o, "k");
    //
    o = evalObject("attr.nodeType", "attr", new JsAttr("k", "v"));
    Assert.assertEquals(o, 2);
    //
    o = evalObject("attr.nodeValue", "attr", new JsAttr("k", "v"));
    Assert.assertEquals(o, "v");
    //
    o = evalObject("attr.isId", "attr", new JsAttr("k", "v"));
    Assert.assertEquals(o, Boolean.FALSE);
    //
    o = evalObject("attr.isId", "attr", new JsAttr("id", "v"));
    Assert.assertEquals(o, Boolean.TRUE);
  }

  private static Bindings eval(String script) throws ScriptException {
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
    b.put("raw", booksXml);
    String js = "var DocType = Java.type(\"com.flow.js.jdom.JsDocument\");\n" + //
        "var doc = new DocType(raw);\n" + script;
    System.out.println("\neval:" + js);
    engine.eval(js);
    return b;
  }

  private static Object evalObject(String script, String bindKey, Object bindValue) throws ScriptException {
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
    b.put(bindKey, bindValue);
    String js = String.format(//
        "var DocType = Java.type(\"com.flow.js.jdom.JsDocument\");\n" //
            + "var obj = %s;", script);
    System.out.println("\neval:" + js);
    engine.eval(js);
    return b.get("obj");
  }
}

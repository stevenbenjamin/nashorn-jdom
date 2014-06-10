package com.flow.js.jdom;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
  public void testParse() throws ScriptException {
    Bindings b = eval(//
        "var doc = new DocType(raw);\n" +        //
            "var nodeName=doc.nodeName;\n" +        //
            "var nodeType=doc.nodeType;\n", "raw", booksXml);
    Assert.assertEquals(b.get("doc").getClass(), JsDocument.class);
    Assert.assertEquals(b.get("nodeName"), "#document");
    Assert.assertEquals(b.get("nodeType"), 9);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testTextContent() throws ScriptException {
    Bindings b = eval(//
        "var doc = new DocType(raw);\n" +        //
            "var node=doc.getElementsByTagName('book')[0];\n" +     //
            "var content=node.textContent;\n", "raw", booksXml);
    Assert.assertEquals(b.get("content"), "    Everyday Italian    Giada De Laurentiis    2005    30.00  ");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testAttributes() throws ScriptException {
    Bindings b = eval(//
        "var doc = new DocType(raw);\n" +        //
            "var node=doc.getElementsByTagName('book')[0];\n" + //
            "var attribute=node.attributes[0];\n" +     //
            "var txt = attribute.textContent;" + "", "raw", booksXml);
    Assert.assertEquals(b.get("attribute").getClass(), JsAttr.class);
    Assert.assertEquals(b.get("txt"), "cooking");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetElementById() throws ScriptException {
    Bindings b = eval(//
        "var doc = new DocType(raw);\n" + //
            "var idNode=doc.getElementById('y');" + //
            "var textNode=idNode.childNodes[0];" +            //
            "var txt = textNode.nodeValue;", "raw", booksXml);
    Assert.assertEquals(b.get("idNode").getClass(), JsTextNode.class);
    Assert.assertEquals(b.get("txt"), "2003");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testGetElementsByTagName() throws ScriptException {
    Bindings b = eval(//
        "var doc = new DocType(raw);\n" +        //
            "var node=doc.getElementsByTagName('title')[0];" + "var textNode=node.childNodes[0];" +     //
            "var txt = textNode.nodeValue;", "raw", booksXml);
    Assert.assertEquals(b.get("node").getClass(), JsTextNode.class);
    Assert.assertEquals(b.get("txt"), "Everyday Italian");
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

  private static Bindings eval(String script, String bindKey, Object bindValue) throws ScriptException {
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
    b.put(bindKey, bindValue);
    String js = "var DocType = Java.type(\"com.flow.js.jdom.JsDocument\");\n" + script;
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

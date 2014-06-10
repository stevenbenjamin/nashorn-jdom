package com.flow.js.jdom;

public abstract class JsFunction extends AbstractJsObject {
  public JsFunction() {
    super("function");
  }

  @Override
  public abstract Object call(Object thiz, Object... args);

  @Override
  public boolean isFunction() {
    return true;
  }

  protected static String safeStringParam(int index, Object... args) {
    if (args == null || args.length <= index) {
      return null;
    }
    return args[index] + "";
  }

  protected static Integer safeIntParam(int index, Object... args) {
    if (args == null || args.length <= index) {
      return null;
    }
    try {
      return Integer.parseInt(args[index] + "");
    } catch (Exception e) {
      return null;
    }
  }
}

package com.flow.js.jdom;

import java.util.Collection;
import java.util.Set;

import jdk.nashorn.api.scripting.JSObject;

public abstract class AbstractJsObject implements JSObject {
  public final String instanceName;

  protected AbstractJsObject(String s) {
    instanceName = s;
  }

  @Override
  public Object call(Object arg0, Object... arg1) {
    throw new UnsupportedOperationException("call");
  }

  @Override
  public Object eval(String arg0) {
    throw new UnsupportedOperationException("eval");
  }

  @Override
  public Object getMember(String arg0) {
    return null;
  }

  @Override
  public Object getSlot(int arg0) {
    return null;
  }

  @Override
  public boolean hasMember(String arg0) {
    return false;
  }

  @Override
  public boolean hasSlot(int arg0) {
    return false;
  }

  @Override
  public boolean isArray() {
    return false;
  }

  @Override
  public boolean isFunction() {
    return false;
  }

  @Override
  public String getClassName() {
    return instanceName;
  }

  @Override
  public boolean isInstance(Object arg0) {
    return instanceName.equals(arg0 + "");
  }

  @Override
  public boolean isInstanceOf(Object arg0) {
    return instanceName.equals(arg0 + "");
  }

  @Override
  public boolean isStrictFunction() {
    System.out.println("issf");
    return false;
  }

  @Override
  public Set<String> keySet() {
    return null;
  }

  @Override
  public Object newObject(Object... arg0) {
    return null;
  }

  @Override
  public void removeMember(String arg0) {
  }

  @Override
  public void setMember(String arg0, Object arg1) {
  }

  @Override
  public void setSlot(int arg0, Object arg1) {
  }

  @Override
  public double toNumber() {
    return 0;
  }

  @Override
  public Collection<Object> values() {
    return null;
  }
}

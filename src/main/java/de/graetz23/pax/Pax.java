/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file Pax.java
 */

package de.graetz23.pax;

import java.util.*;

public class Pax implements IPax {

  private String _tag = null; // member

  private String _val = null; // member

  private IPax _parent = null; // member

  private IChildren _children = null; // member

  private IAttributes _attributes = null; // member

  public Pax(String tag) {
    Tag(tag);
  } // constructor

  public Pax(String tag, String val) {
    Tag(tag);
    if (val != null && !val.isEmpty()) {
      Val(val);
    } // if
  } // constructor

  public Pax(IPax Pax) {

    if (Pax != null) {

      if (Pax.hasTag()) {
        Tag(Pax.Tag());
      } // if

      if (Pax.hasAttrib()) {
        List<IPax> attribs = Pax.Attrib().all();
        for (IPax attrib : attribs) {
          IPax attrib_ = Instances.Factory().copy(attrib);
          Attrib().add(attrib_);
        } // loop
      } // if

      if (Pax.hasVal()) { // either value ..
        Val(Pax.Val());
      } // if

      if (Pax.hasChild()) {
        List<IPax> list = Pax.Child().all();
        for (IPax child : list) { // go recursive ..
          IPax child_ = Instances.Factory().copy(child);
          Child().add(child_);
        } // loop
      } // if

    } // if
  } // constructor

  @Override
  public String Tag() {
    return _tag;
  } // method

  @Override
  public void Tag(String tag) {
    _tag = tag;
  } // method

  @Override
  public boolean hasTag() {
    return _tag != null && !_tag.isEmpty();
  } // method

  @Override
  public String Val() {
    return _val;
  } // method

  @Override
  public void Val(String val) {
    if (val != null && !val.isEmpty() && !val.isBlank() && !val.toLowerCase().equals(Statics.LineSeparator) && !val.toLowerCase().equals("\n")) {
      _val = val;
    } else {
      _val = null;
    } // if
  } // method

  @Override
  public boolean hasVal() {
    return _val != null && !_val.isEmpty() && !_val.isBlank(); // is safe on null pointers ..
  } // method

  @Override
  public IPax Parent() {
    return _parent;
  } // method

  @Override
  public void Parent(IPax parent) {
    _parent = parent;
  } // method

  @Override
  public boolean hasParent() {
    return _parent != null;
  } // method

  @Override
  public String Path() {
    String path = null;

    IPax current = this;
    List<IPax> list = new ArrayList<IPax>();
    list.add(current);
    while (current.hasParent()) {
      current = current.Parent();
      list.add(current);
    } // loop

    Collections.reverse(list);

    StringBuilder sb = new StringBuilder();
    for (IPax Pax : list) {
      String tag = Pax.Tag();
      sb.append("/");
      sb.append(tag);
    } // loop
    if (!sb.isEmpty()) {
      path = sb.toString();
    } // if
    return path;
  } // method

  @Override
  public IChildren Child() {
    if (_children == null) {
      _children = new Children(this);
    } // if
    return _children;
  } // method

  @Override
  public boolean hasChild() {
    boolean has = false;
    if (_children != null) {
      if (_children.cnt() > 0) {
        has = true;
      } // if
    } // if
    return has;
  } // method

  @Override
  public IAttributes Attrib() {
    if (_attributes == null) {
      _attributes = new Attributes(this);
    } // if
    return _attributes;
  } // method

  @Override
  public boolean hasAttrib() {
    boolean has = false;
    if (_attributes != null) {
      if (_attributes.cnt() > 0) {
        has = true;
      } // if
    } // if
    return has;
  } // method

  @Override
  public String XML() {
    StringBuilder xml = new StringBuilder(Statics.Indent());
    if (hasTag()) {
      if (!hasChild()) {
        if (hasVal()) {
          if (Tag().startsWith(Identity.COMMENT)) {
            xml.append("<!--").append(Val()).append("-->").append(Statics.LineSeparator);
          } else if (Tag().startsWith(Identity.CDATA)) {
            xml.append("<![CDATA[").append(Val()).append("]]>").append(Statics.LineSeparator);
          } else {
            if (hasAttrib()) {
              xml.append("<").append(Tag()).append(" ").append(Attrib().XML()).append(">").append(Val()).append("</").append(Tag()).append(">").append(Statics.LineSeparator);
              // xml += "<" + Tag() + ">" + Val() + "</" + Tag() + ">" +Statics.LineSeparator;
            } else {
              xml.append("<").append(Tag()).append(">").append(Val()).append("</").append(Tag()).append(">").append(Statics.LineSeparator);
            } // if
          } // if
        } else {
          if (hasAttrib()) {
            xml.append("<").append(Tag()).append(" ").append(Attrib().XML()).append("/>").append(Statics.LineSeparator);
            // xml += "<" + Tag()  + "/>" + Statics.LineSeparator;
          } else {
            xml.append("<").append(Tag()).append(" />").append(Statics.LineSeparator);
          } // if
        } // if
      } else {
        if (hasAttrib()) {
          xml.append("<").append(Tag()).append(" ").append(Attrib().XML()).append(">").append(Statics.LineSeparator);
          // xml += "<" + Tag() + ">" + Statics.LineSeparator;
        } else {
          xml.append("<").append(Tag()).append(">").append(Statics.LineSeparator);
        } // if
        Statics.incIndent();
        for (IPax child : Child().all()) { // goe recursive ..
          xml.append(child.XML());
        } // loop
        Statics.decindent();
        xml.append(Statics.Indent()).append("</").append(Tag()).append(">").append(Statics.LineSeparator);
      } // if
    } // if
    return xml.toString();
  } // method

  @Override
  public String XML_lined() {
    // StringBuilder xml = new StringBuilder(Statics.Indent());
    StringJoiner xml = new StringJoiner("");
    if (hasTag()) {
      if (!hasChild()) {
        if (hasVal()) {
          if (Tag().startsWith(Identity.COMMENT)) {
            xml.add("<!--").add(Val()).add("-->");
          } else if (Tag().startsWith(Identity.CDATA)) {
            xml.add("<![CDATA[").add(Val()).add("]]>");
          } else {
            if (hasAttrib()) {
              xml.add("<").add(Tag()).add(" ").add(Attrib().XML()).add(">").add(Val()).add("</").add(Tag()).add(">");
              // xml += "<" + Tag() + ">" + Val() + "</" + Tag() + ">" +Statics.LineSeparator;
            } else {
              xml.add("<").add(Tag()).add(">").add(Val()).add("</").add(Tag()).add(">");
            } // if
          } // if
        } else {
          if (hasAttrib()) {
            xml.add("<").add(Tag()).add(" ").add(Attrib().XML()).add("/>");
            // xml += "<" + Tag()  + "/>" + Statics.LineSeparator;
          } else {
            xml.add("<").add(Tag()).add(" />");
          } // if
        } // if
      } else {
        if (hasAttrib()) {
          xml.add("<").add(Tag()).add(" ").add(Attrib().XML()).add(">");
          // xml += "<" + Tag() + ">" + Statics.LineSeparator;
        } else {
          xml.add("<").add(Tag()).add(">");
        } // if
        Statics.incIndent();
        for (IPax child : Child().all()) { // goe recursive ..
          xml.add(child.XML_lined());
        } // loop
        Statics.decindent();
        xml.add("</").add(Tag()).add(">");
      } // if
    } // if
    return xml.toString();
  } // method

  protected final class Children extends Subset implements IChildren {

    public Children(IPax ancestor) {
      super(ancestor);
    } // constructor

    /**
     * Search a node by given path; like XPath, e.g. /child1/child4/wanted
     *
     * @param path as the path having slashes as hierarchical separators
     * @return null or the found IPax object
     */
    @Override
    public IPax search(String path) {
      IPax found = null;
      if (path != null && (path.startsWith("/") || path.startsWith("./"))) {

        IPax current = this.Ancestor();

        if (path.startsWith("/")) { // walk up root

          while (current.hasParent()) {
            current = current.Parent();
          } // loop

        } else if (path.startsWith(".")) { // start with child

          while (path.startsWith(".")) { // remove alls dots
            path = path.substring(1, path.length());
          } // if

          String tag = current.Tag();
          path = tag + path; // add myself for searching below

        } // if

        if (current != null) {

          while(path.contains("//")) {
            path = path.replaceAll("//", "/");
          } // loop

          while (path.startsWith("/")) { // remove all leading
            int endIndx = path.length();
            path = path.substring(1, endIndx);
          } // loop

          while (path.endsWith("/")) { // remove all trailing
            int endIndx = path.length() - 1;
            path = path.substring(0, endIndx);
          } // loop

          boolean wasFound = false;
          List<String> list = Arrays.stream(path.split("/")).toList();
          for (String tag : list) {

            if (current.Child().has(tag)) {

              IPax child = current.Child().get(tag);
              if (child != null) {
                current = child;
                wasFound = true;
              } else {
                wasFound = false;
              } // if

            } else {
              if(current.Tag().equals(tag)) {
                wasFound = true;
              } else {
                wasFound = false;
              } // if
            } // if

          } // loop

          if (wasFound) {
            found = current;
          } // if
        } // if
      } // if

      return found;
    } // method

  } // nested

  protected final class Attributes extends Subset implements IAttributes {

    public Attributes(IPax ancestor) {
      super(ancestor);
    } // constructor

    @Override
    public String XML() {
      StringBuilder xml = new StringBuilder();
      List<IPax> attribs = all();
      for (IPax attrib : attribs) {
        xml.append(attrib.Tag()).append("=\"").append(attrib.Val()).append("\" ");
      }
      return xml.toString().trim();
    } // method

  } // nested

} // class

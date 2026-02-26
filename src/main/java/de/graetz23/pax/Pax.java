/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.graetz23.pax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

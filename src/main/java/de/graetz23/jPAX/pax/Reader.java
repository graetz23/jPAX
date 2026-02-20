/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2026 jPAX Christian (graetz23@gmail.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.graetz23.jPAX.pax;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class Reader {

  public static Reader Instance = new Reader();

  private Reader() {
  } // constructor

  public IPax parse(String filename) {

    IPax root = null;
    SAXParserFactory factory = SAXParserFactory.newInstance();

    try {
      SAXParser parser = factory.newSAXParser();
      IPaxHandler handler = new IPaxHandler();
      parser.getXMLReader().setProperty(handler.LexicalHandlerProperty(), handler.LexicalHandler());
      FileInputStream fileInputStream = new FileInputStream(filename); // open file
      if (fileInputStream != null) {
        parser.parse(fileInputStream, handler);
        root = handler.getRoot();
      } else {
        System.out.println("InputStream is null - no file found");
      } //
    } catch (Exception exception) {
      exception.printStackTrace();
    } // try

    return root;
  } // method

  public IPax parseLocalFile(String filename) {

    IPax root = null;
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      SAXParser parser = factory.newSAXParser();
      IPaxHandler handler = new IPaxHandler();
      parser.getXMLReader().setProperty(handler.LexicalHandlerProperty(), handler.LexicalHandler());
      parser.parse(filename, handler);
      root = handler.getRoot();
    } catch (Exception exception) {
      exception.printStackTrace();
    } // try

    return root;
  } // method

  public IPax stream(InputStream stream) {

    IPax root = null;
    SAXParserFactory factory = SAXParserFactory.newInstance();

    try {
      SAXParser parser = factory.newSAXParser();
      IPaxHandler handler = new IPaxHandler();
      parser.getXMLReader().setProperty(handler.LexicalHandlerProperty(), handler.LexicalHandler());
      if (stream != null) {
        parser.parse(stream, handler);
        root = handler.getRoot();
      } else {
        System.out.println("InputStream is null - no file found");
      } //
    } catch (Exception exception) {
      exception.printStackTrace();
    } // try

    return root;
  } // method


  private class IPaxHandler extends DefaultHandler {

    private int _hierarchyLevel = -1; // member

    private boolean _wasRootFound = false;

    private IPax _parent = null; // member

    private LexicalHandler _lexicalHandler = null;

    private IPax _root = null; // member

    public IPaxHandler() {
      _lexicalHandler = new IPaxLexicalHandler(this);
    } // method

    public LexicalHandler LexicalHandler() {
      return _lexicalHandler;
    } // method

    public String LexicalHandlerProperty() {
      return "http://xml.org/sax/properties/lexical-handler";
    } // method

    public IPax getRoot() {
      return _root;
    } // method

    @Override
    public void startDocument() throws SAXException {
      _hierarchyLevel = -1;
      _wasRootFound = false;
      _parent = null;
      _root = null;
    } // method

    @Override
    public void endDocument() throws SAXException {
      if (_hierarchyLevel != -1) { // check for staring value
        String message = this.getClass().getSimpleName();
        message += " - ";
        message += "XML document is not closed, hierarchy ended at level: ";
        message += _hierarchyLevel + 1; // add one up
        throw new SAXException(message);
      } // if
    } // method

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
      _hierarchyLevel++; // one hierarchy up: 0, 1, 2, 3, ..

      IPax Pax = Instances.Factory().produce(qName); // typed new one, if tag is known

      if (_hierarchyLevel == 0) { // only for the root case
        if (_wasRootFound) {
          String message = this.getClass().getSimpleName();
          message += " - ";
          message += "XML document is keeping a 2nd XML root node";
          throw new SAXException(message);
        } // if
        _wasRootFound = true; // look for single root

        _root = Pax; // settle this as the root node

      } else { // all other cases
        _parent.Child().add(Pax); // new is child of parent
      } // if

      if (attributes.getLength() > 0) { // settle all attributes
        for (int i = 0; i < attributes.getLength(); i++) {
          String attr = attributes.getQName(i);
          String value = attributes.getValue(i);
          Pax.Attrib().add(attr, value);
        } // loop
      } // if

      _parent = Pax; // settle new as the next parent
    } // method

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
      String val = new String(ch, start, length);
      _parent.Val(val); // update value of current
    } // method

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      _hierarchyLevel--; // one hierarchy down: .. 3, 2, 1, 0
      _parent = _parent.Parent(); // get one up for new ones on level higher
    } // method

    public class IPaxLexicalHandler implements LexicalHandler {

      private IPaxHandler _handler = null;

      public IPaxLexicalHandler(IPaxHandler handler) {
        _handler = handler;
      } // constructor

      @Override
      public void startDTD(String name, String publicId, String systemId) throws SAXException {
      } // method

      @Override
      public void endDTD() throws SAXException {
      } // method

      @Override
      public void startEntity(String name) throws SAXException {
      } // method

      @Override
      public void endEntity(String name) throws SAXException {
      } // method

      @Override
      public void startCDATA() throws SAXException {
      } // method

      @Override
      public void endCDATA() throws SAXException {
      } // method

      @Override
      public void comment(char[] ch, int start, int length) throws SAXException {
        if (_handler._hierarchyLevel > -1) {
          IPax parent = _handler._parent;
          parent.Child().add(Identity.COMMENT, new String(ch, start, length));
        } // if
      } // method

    } // class
  } // class
} // class

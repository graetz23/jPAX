/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file XmlGenerator.java
 */

package de.graetz23.pax;

import java.util.StringJoiner;

public class XmlGenerator {

  public static String generate(IPax pax) {
    StringBuilder xml = new StringBuilder(Statics.Indent());
    return generateElement(pax, xml).toString();
  } // method

  public static String generateLined(IPax pax) {
    StringJoiner xml = new StringJoiner("");
    return generateElementLined(pax, xml).toString();
  } // method

  private static StringBuilder generateElement(IPax pax, StringBuilder xml) {
    if (pax == null || !pax.hasTag()) {
      return xml;
    }
    if (!pax.hasChild()) {
      if (pax.hasVal()) {
        if (pax.Tag().startsWith(Identity.COMMENT)) {
          xml.append("<!--").append(pax.Val()).append("-->").append(Statics.LineSeparator);
        } else if (pax.Tag().startsWith(Identity.CDATA)) {
          xml.append("<![CDATA[").append(pax.Val()).append("]]>").append(Statics.LineSeparator);
        } else {
          if (pax.hasAttrib()) {
            xml.append("<").append(pax.Tag()).append(" ").append(pax.Attrib().XML()).append(">").append(pax.Val()).append("</").append(pax.Tag()).append(">").append(Statics.LineSeparator);
          } else {
            xml.append("<").append(pax.Tag()).append(">").append(pax.Val()).append("</").append(pax.Tag()).append(">").append(Statics.LineSeparator);
          }
        }
      } else {
        if (pax.hasAttrib()) {
          xml.append("<").append(pax.Tag()).append(" ").append(pax.Attrib().XML()).append("/>").append(Statics.LineSeparator);
        } else {
          xml.append("<").append(pax.Tag()).append(" />").append(Statics.LineSeparator);
        }
      }
    } else {
      if (pax.hasAttrib()) {
        xml.append("<").append(pax.Tag()).append(" ").append(pax.Attrib().XML()).append(">").append(Statics.LineSeparator);
      } else {
        xml.append("<").append(pax.Tag()).append(">").append(Statics.LineSeparator);
      }
      Statics.incIndent();
      for (IPax child : pax.Child().all()) {
        generateElement(child, xml);
      }
      Statics.decindent();
      xml.append(Statics.Indent()).append("</").append(pax.Tag()).append(">").append(Statics.LineSeparator);
    }
    return xml;
  } // method

  private static StringJoiner generateElementLined(IPax pax, StringJoiner xml) {
    if (pax == null || !pax.hasTag()) {
      return xml;
    }
    if (!pax.hasChild()) {
      if (pax.hasVal()) {
        if (pax.Tag().startsWith(Identity.COMMENT)) {
          xml.add("<!--").add(pax.Val()).add("-->");
        } else if (pax.Tag().startsWith(Identity.CDATA)) {
          xml.add("<![CDATA[").add(pax.Val()).add("]]>");
        } else {
          if (pax.hasAttrib()) {
            xml.add("<").add(pax.Tag()).add(" ").add(pax.Attrib().XML()).add(">").add(pax.Val()).add("</").add(pax.Tag()).add(">");
          } else {
            xml.add("<").add(pax.Tag()).add(">").add(pax.Val()).add("</").add(pax.Tag()).add(">");
          }
        }
      } else {
        if (pax.hasAttrib()) {
          xml.add("<").add(pax.Tag()).add(" ").add(pax.Attrib().XML()).add("/>");
        } else {
          xml.add("<").add(pax.Tag()).add(" />");
        }
      }
    } else {
      if (pax.hasAttrib()) {
        xml.add("<").add(pax.Tag()).add(" ").add(pax.Attrib().XML()).add(">");
      } else {
        xml.add("<").add(pax.Tag()).add(">");
      }
      Statics.incIndent();
      for (IPax child : pax.Child().all()) {
        generateElementLined(child, xml);
      }
      Statics.decindent();
      xml.add("</").add(pax.Tag()).add(">");
    }
    return xml;
  } // method

} // class

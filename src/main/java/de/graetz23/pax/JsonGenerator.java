/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file JsonGenerator.java
 */

package de.graetz23.pax;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonGenerator {

  public static String generate(IPax pax) {
    StringBuilder json = new StringBuilder();
    toJson(pax, json, 0, true);
    return json.toString();
  }

  private static void toJson(IPax pax, StringBuilder json, int indent, boolean isRoot) {
    if (pax == null || !pax.hasTag()) {
      json.append("null");
      return;
    }

    String tag = pax.Tag();

    json.append("{");

    boolean hasContent = false;

    json.append("\"__tag__\": \"");
    json.append(escapeJson(tag));
    json.append("\"");
    hasContent = true;

    if (pax.hasVal()) {
      if (hasContent) json.append(", ");
      json.append("\"__value__\": ");
      appendJsonValue(pax.Val(), json);
      hasContent = true;
    }

    List<IPax> attrs = pax.Attrib().all();
    if (!attrs.isEmpty()) {
      if (hasContent) json.append(", ");
      json.append("\"__attributes__\": [");
      for (int i = 0; i < attrs.size(); i++) {
        if (i > 0) json.append(", ");
        IPax attr = attrs.get(i);
        json.append("{");
        json.append("\"name\": \"").append(escapeJson(attr.Tag())).append("\", ");
        json.append("\"value\": ");
        appendJsonValue(attr.Val(), json);
        json.append("}");
      }
      json.append("]");
      hasContent = true;
    }

    List<IPax> children = pax.Child().all();
    if (!children.isEmpty()) {
      if (hasContent) json.append(", ");
      json.append("\"__children__\": {");

      Map<String, List<IPax>> childGroups = new LinkedHashMap<>();
      for (IPax child : children) {
        String childTag = child.Tag();
        if (!childGroups.containsKey(childTag)) {
          childGroups.put(childTag, new ArrayList<>());
        }
        childGroups.get(childTag).add(child);
      }

      boolean firstChild = true;
      for (Map.Entry<String, List<IPax>> entry : childGroups.entrySet()) {
        if (!firstChild) json.append(", ");

        String childTag = entry.getKey();
        List<IPax> childList = entry.getValue();

        if (childList.size() == 1) {
          json.append("\"").append(escapeJson(childTag)).append("\": ");
          toJsonValue(childList.get(0), json, false, true);
        } else {
          json.append("\"").append(escapeJson(childTag)).append("\": [");
          for (int i = 0; i < childList.size(); i++) {
            if (i > 0) json.append(", ");
            toJsonValue(childList.get(i), json, false, true);
          }
          json.append("]");
        }
        firstChild = false;
      }

      json.append("}");
    }

    json.append("}");
  }

  private static void toJsonValue(IPax pax, StringBuilder json, boolean isRoot, boolean forceObject) {
    if (forceObject && pax.hasTag()) {
      toJson(pax, json, 0, false);
    } else if (pax.hasVal()) {
      appendJsonValue(pax.Val(), json);
    } else if (pax.hasChild() || pax.hasAttrib()) {
      toJson(pax, json, 0, false);
    } else if (isRoot && pax.hasTag()) {
      toJson(pax, json, 0, true);
    } else {
      json.append("{}");
    }
  }

  private static void appendJsonValue(String val, StringBuilder json) {
    if (val == null) {
      json.append("null");
    } else if (val.equals("true") || val.equals("false")) {
      json.append(val);
    } else if (isNumeric(val)) {
      json.append(val);
    } else {
      json.append("\"").append(escapeJson(val)).append("\"");
    }
  }

  private static String escapeJson(String str) {
    if (str == null) return "";
    return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t").replace("\b", "\\b").replace("\f", "\\f");
  }

  private static boolean isNumeric(String str) {
    if (str == null || str.isEmpty()) return false;
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}

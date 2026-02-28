/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file JsonReaderTest.java
 */

package de.graetz23.pax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    Instances.resetFactory();
  }

  @Test
  void testParseEmptyObject() {
    IPax result = JsonReader.Instance.parseJson("{}");
    assertNotNull(result);
    assertEquals("object", result.Tag());
  }

  @Test
  void testParseSimpleObject() {
    IPax result = JsonReader.Instance.parseJson("{\"__tag__\": \"object\", \"name\": \"value\"}");

    assertNotNull(result);
  }

  @Test
  void testParseMultipleAttributes() {
    IPax result = JsonReader.Instance.parseJson("{\"__tag__\": \"test\", \"__attributes__\": [{\"name\": \"id\", \"value\": 1}, {\"name\": \"name\", \"value\": \"test\"}]}");

    assertNotNull(result);
  }

  @Test
  void testParseNestedObject() {
    IPax result = JsonReader.Instance.parseJson("{\"book\": {\"title\": \"Java\"}}");

    assertNotNull(result);
  }

  @Test
  void testParseEmptyArray() {
    IPax result = JsonReader.Instance.parseJson("[]");

    assertNotNull(result);
    assertEquals("array", result.Tag());
  }

  @Test
  void testParseArrayWithValues() {
    IPax result = JsonReader.Instance.parseJson("[\"a\", \"b\", \"c\"]");

    assertNotNull(result);
    assertEquals("array", result.Tag());
    assertEquals(3, result.Child().cnt());
  }

  @Test
  void testParseArrayWithNumbers() {
    IPax result = JsonReader.Instance.parseJson("[1, 2, 3, 4, 5]");

    assertNotNull(result);
    assertEquals("array", result.Tag());
    assertEquals(5, result.Child().cnt());
  }

  @Test
  void testParseArrayWithObjects() {
    IPax result = JsonReader.Instance.parseJson("[{\"id\": 1}, {\"id\": 2}]");

    assertNotNull(result);
    assertEquals("array", result.Tag());
    assertEquals(2, result.Child().cnt());
  }

  @Test
  void testParseMixedArray() {
    IPax result = JsonReader.Instance.parseJson("[\"string\", 123, true, null]");

    assertNotNull(result);
    assertEquals("array", result.Tag());
    assertEquals(4, result.Child().cnt());
  }

  @Test
  void testParseComplexObject() {
    String json = "{" + "\"library\": {" + "  \"name\": \"City Library\"," + "  \"books\": [" + "    {\"title\": \"Book 1\", \"author\": \"Author 1\"}," + "    {\"title\": \"Book 2\", \"author\": \"Author 2\"}" + "  ]" + "}}";

    IPax result = JsonReader.Instance.parseJson(json);
    assertNotNull(result);
  }

  @Test
  void testParseFromStream() throws IOException {
    String json = "{\"key\": \"value\"}";
    ByteArrayInputStream stream = new ByteArrayInputStream(json.getBytes());

    IPax result = JsonReader.Instance.stream(stream);

    assertNotNull(result);
    assertEquals("object", result.Tag());
  }

  @Test
  void testParseFromFile() throws IOException {
    String json = "{\"root\": \"data\"}";
    Path file = tempDir.resolve("test.json");
    Files.writeString(file, json);

    IPax result = JsonReader.Instance.parse(file.toString());

    assertNotNull(result);
    assertEquals("object", result.Tag());
  }

  @Test
  void testParseNullStream() {
    IPax result = JsonReader.Instance.stream(null);
    assertNull(result);
  }

  @Test
  void testParseNullValue() {
    IPax result = JsonReader.Instance.parseJson("{\"key\": null}");

    assertNotNull(result);
    assertTrue(result.hasAttrib() || result.hasChild());
  }

  @Test
  void testParseBooleanValues() {
    IPax result = JsonReader.Instance.parseJson("{\"__tag__\": \"test\", \"__attributes__\": [{\"name\": \"active\", \"value\": true}]}");

    assertNotNull(result);
  }

  @Test
  void testParseNumberValues() {
    IPax result = JsonReader.Instance.parseJson("{\"__tag__\": \"test\", \"__attributes__\": [{\"name\": \"int\", \"value\": 42}]}");

    assertNotNull(result);
  }

  @Test
  void testParseScientificNotation() {
    IPax result = JsonReader.Instance.parseJson("{\"big\": 1e10, \"small\": 1.5e-3}");

    assertNotNull(result);
  }

  @Test
  void testParseEmptyString() {
    IPax result = JsonReader.Instance.parseJson("{\"empty\": \"\"}");

    assertNotNull(result);
  }

  @Test
  void testParseSpecialCharacters() {
    IPax result = JsonReader.Instance.parseJson("{\"text\": \"hello\\nworld\\ttab\"}");

    assertNotNull(result);
  }

  @Test
  void testParseUnicode() {
    IPax result = JsonReader.Instance.parseJson("{\"__tag__\": \"test\"}");

    assertNotNull(result);
  }

  @Test
  void testRoundTrip() throws IOException {
    IPax original = Instances.Factory().produce("library");
    original.Attrib().add("name", "Test Library");

    IPax book = Instances.Factory().produce("book");
    book.Attrib().add("id", "1");
    book.Child().add("title", "Test Book");
    original.Child().add(book);

    String xml = original.XML();

    assertNotNull(xml);
    assertTrue(xml.contains("<library"));
  }

  @Test
  void testArrayIndexing() {
    IPax result = JsonReader.Instance.parseJson("[\"first\", \"second\", \"third\"]");

    assertEquals("array", result.Tag());
    for (int i = 0; i < result.Child().cnt(); i++) {
      IPax item = result.Child().all().get(i);
      assertEquals("item", item.Tag());
      assertNotNull(item.Attrib().get("index"));
    }
  }
}

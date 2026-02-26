/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file Writer.java
 */

package de.graetz23.pax;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class Writer {

    public static Writer Instance = new Writer();

    private Writer() {
    } // constructor

    public boolean XML(IPax root) {
        boolean wasWritten = false;
        if (root != null) {
            String tag = "__file_noname";
            if (root.hasTag()) {
                tag = root.Tag();
            } // if
            wasWritten = XML(root, tag);
        } // if
        return wasWritten;
    } // method

    public boolean XML(IPax root, String fileName) {
        boolean wasWritten = false;
        if (root != null) {
            if (!fileName.toLowerCase().endsWith(".xml")) {
                fileName += ".xml";
            } // if

            try {
                OutputStream stream = new FileOutputStream(fileName); // streaming the content
                CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder(); // get sure to write UTF-8
                OutputStreamWriter file = new OutputStreamWriter(stream, encoder);
                String xml = root.XML();
                String header = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" + Statics.LineSeparator;
                file.write(header);
                file.write(xml);
                file.close();
                wasWritten = true;
            } catch (IOException e) {
                e.printStackTrace();
            } // try
        } // if
        return wasWritten;
    } // method

} // class

/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2025 jPAX Christian (graetz23@gmail.com)
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

package de.graetz23.jPAX.pax;

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

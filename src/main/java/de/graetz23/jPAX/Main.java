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

package de.graetz23.jPAX;

import de.graetz23.jPAX.pax.IPax;
import de.graetz23.jPAX.pax.Instances;
import de.graetz23.jPAX.pax.Reader;
import de.graetz23.jPAX.pax.Writer;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello and welcome!");

        IPax root = Instances.Factory().produce("root"); // produce a pax node as named root
        root.Child().add("child1"); // add a child node
        root.Child().add("child2");
        root.Child().add("child3");
        root.Child().get("child2").Attrib().add("is", "active"); // get a child by tag and add and attribute
        root.Child().get("child3").Attrib().add("is", "inactive");
        root.Child().get("child1").Child().add("child4"); // add child node to another by tag
        root.Child().get("child1").Child().get("child4").Attrib().add("is", "active");

        String xml = root.XML(); // generate XML from the node tree
        System.out.println(xml);

        Writer.Instance.XML(root, "root.xml"); // write XML to drive

        IPax loaded = Reader.Instance.parse("./root.xml"); // parse XML to node tree
        String xml_ = loaded.XML(); // generate XMl from loaded
        System.out.println(xml_);

        boolean stopHere = true;

    } // main

} // class

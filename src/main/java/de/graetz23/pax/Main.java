/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file Main.java
 */

package de.graetz23.pax;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello and welcome!");

        IPax root = Instances.Factory().produce("root"); // produce a pax node as named root
        root.Child().add("child1"); // add a child node
        root.Child().add("child2");
        IPax child3 = Instances.Factory().produce("child3");
        root.Child().add(child3);
        root.Child().get("child2").Attrib().add("is", "active"); // get a child by tag and add and attribute
        IPax child3_ = root.Child().get("child3");
        child3_.Attrib().add("is", "inactive");
        root.Child().get("child1").Child().add("child4"); // add child node to another by tag
        root.Child().get("child1").Child().get("child4").Attrib().add("is", "active");

        String xml = root.XML(); // generate XML from the node tree
        System.out.println(xml);

        Writer.Instance.XML(root, "root.xml"); // write XML to drive

        IPax loaded = Reader.Instance.parse("./root.xml"); // parse XML to node tree
        String xml_ = loaded.XML(); // generate XMl from loaded
        System.out.println(xml_);

        // search for some nodes by absolute or relative path; no wildcards yet

        String xpath_absolute1 = "/root/child1/child4/"; // hit
        IPax found1 = root.Child().search(xpath_absolute1);

        String xpath_absolute2 = "/root/child1/child5/"; // miss
        IPax found2 = root.Child().search(xpath_absolute2);

        String xpath_relative1 = "./child1/child4/"; // hit
        IPax found3 = root.Child().search(xpath_relative1);

        String xpath_relative2 = "./child1/child5/"; // miss
        IPax found4 = root.Child().search(xpath_relative2);

        IPax found5 = found3.Child().search("/root"); // somewhere

        IPax found6 = found3.Child().search("///root///////child1////"); // somewhere

        boolean stopHere = true;

    } // main

} // class

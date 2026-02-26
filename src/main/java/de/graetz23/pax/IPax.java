/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file IPax.java
 */

package de.graetz23.pax;

public interface IPax {

    String Tag(); // method

    void Tag(String tag); // method

    boolean hasTag(); // method

    String Val(); // method

    void Val(String val); // method

    boolean hasVal(); // method

    IPax Parent(); // method

    void Parent(IPax parent); // method

    boolean hasParent(); // method

    String Path(); // method

    IChildren Child(); // method

    boolean hasChild(); // method

    IAttributes Attrib(); // method

    boolean hasAttrib(); // method

    String XML(); // method

    String XML_lined(); // method

} // interface

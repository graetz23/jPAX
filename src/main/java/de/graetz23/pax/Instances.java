/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file Instances.java
 */

package de.graetz23.pax;

public class Instances {

    private final static IFactory _base = new Factory(); // member

    private static IFactory _factory = _base; // member

    public static void resetFactory() { _factory = _base; } // method

    public static IFactory Factory() {
        return _factory;
    } // method

    public static void Factory(IFactory factory) {
        _factory = factory;
    } // method

} // class

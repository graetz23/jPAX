/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file Statics.java
 */

package de.graetz23.pax;

public class Statics {

    public static final String LineSeparator = System.lineSeparator(); // member
    public static final String Separation = " "; // member
    private static int _sizeIndent = 2; // member
    private static int _currentIndent = 0; // member

    public static String Indent() {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < _currentIndent; i++) {
            indent.append(" ");
        } // loop
        return indent.toString();
    } // method

    public static void incIndent() {
        _currentIndent += _sizeIndent;
    } // method

    public static void decindent () {
        _currentIndent -= _sizeIndent;
        if(_currentIndent < 0) {
            _currentIndent = 0;
        } // if
    } // method

    private static long _cnt = 0; // member
    public static long Next() {
        _cnt = _cnt + 1;
        return _cnt;
    } // method

} // class

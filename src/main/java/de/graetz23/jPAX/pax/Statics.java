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

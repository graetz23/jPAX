/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
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

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

import java.util.List;

/**
 * Class is part of the IPax interfacing, realizing the list operations for the attributes and
 * children stored at some IPax instance.
 */
interface ISubset {

    IPax Ancestor();

    void Ancestor(IPax ancestor);

    boolean hasAncestor();

    IPax First(); // method

    boolean has(String tag); // method

    boolean has(IPax Pax); // method

    IPax get(int i); // method

    IPax get(String tag); // method

    boolean add(String tag); // method

    boolean add(String tag, String val); // method

    boolean add(IPax Pax); // method

    boolean set(String tag, String val); // method

    boolean set(IPax Pax);

    boolean del(String key); // method

    boolean del(IPax Pax); // method

    boolean del(); // method

    int cnt(); // method

    List<IPax> all(); // method

    List<IPax> all(String tag); // method

    <T extends IPax> List<T> typed(); // method

    <T extends IPax> List<T> typed(String tag); // method

} // interface

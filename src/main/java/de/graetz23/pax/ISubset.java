/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file ISubset.java
 */

package de.graetz23.pax;

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

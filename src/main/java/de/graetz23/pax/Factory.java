/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file Factory.java
 */

package de.graetz23.pax;

public class Factory implements IFactory {

    public IPax produce(String tag) {
        return new Pax(tag);
    } // method

    public IPax produce(String tag, String val) {
        return new Pax(tag, val);
    } // method

    public IPax copy(IPax Pax) {
        return new Pax(Pax);
    } // method

} // class

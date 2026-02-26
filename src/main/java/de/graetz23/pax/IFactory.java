/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file IFactory.java
 */

package de.graetz23.pax;

public interface IFactory {

    IPax produce(String tag); // method

    IPax produce(String tag, String val); // method

    IPax copy(IPax Pax); // method

} // interface

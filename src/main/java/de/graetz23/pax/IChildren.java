/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file IChildren.java
 */

package de.graetz23.pax;

public interface IChildren extends ISubset {

  // overload or add methods, if necessary

  /**
   * Search a node by given path; like XPath, e.g. /child1/child4/wanted
   * @param path as the path having slashes as hierarchical separators
   * @return null or the found IPax object
   */
  IPax search(String path);

} // interface

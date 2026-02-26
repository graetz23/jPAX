/**
 * @brief pax
 * @details A Java written generator for plain old XML (POX) data domains
 * @copyright Copyright (c) 2017-2026 Christian (graetz23@gmail.com)
 * @author Christian (graetz23@gmail.com)
 * @file SubSet.java
 */

package de.graetz23.pax;

import java.util.*;

public class Subset implements ISubset {

    private final LinkedHashMap<String, IPax> _hashMap = new LinkedHashMap<String, IPax>();

    private IPax _ancestor = null;

    public Subset(IPax ancestor) {
        _ancestor = ancestor;
    } // constructor

    @Override
    public IPax Ancestor() {
        return _ancestor;
    } // method

    @Override
    public void Ancestor(IPax ancestor) {
        _ancestor = ancestor;
    } // method

    @Override
    public boolean hasAncestor() {
        return _ancestor != null;
    } // method

    @Override
    public IPax First() {
        IPax first = null;
        if (!_hashMap.isEmpty()) {
            first = get(0);
        } // if
        return first;
    } // method

    @Override
    public boolean has(String tag) {
        return _hashMap.containsKey(tag);
    } // method

    @Override
    public boolean has(IPax Pax) {
        return _hashMap.containsValue(Pax);
    } // method

    @Override
    public IPax get(int i) {
        IPax Pax = null;
        int currentIndx = 0;
        for (Map.Entry<String, IPax> entry : _hashMap.entrySet()) {
            if (currentIndx == i) {
                Pax = entry.getValue();
                break;
            } // if
            currentIndx++;
        } // loop
        return Pax;
    } // method

    @Override
    public IPax get(String tag) {
        IPax Pax = null;
        if (has(tag)) {
            Pax = _hashMap.get(tag);
        } // if
        return Pax;
    } // method

    @Override
    public boolean add(String tag) {
        boolean wasAdded = false;
        if (!tag.isEmpty()) {
            if (!has(tag)) {
                IPax Pax = Instances.Factory().produce(tag);
                Pax.Parent(Ancestor());
                _hashMap.put(Pax.Tag(), Pax);
            } else { // a helping hand to put correctly ..
                String tag_ = tag + Statics.Separation + Statics.Next();
                IPax Pax = Instances.Factory().produce(tag);
                Pax.Parent(Ancestor());
                _hashMap.put(tag_, Pax);
            } // if
            wasAdded = true;
        } // if
        return wasAdded;
    } // method

    @Override
    public boolean add(String tag, String val) {
        boolean wasAdded = false;
        if (!tag.isEmpty()) {
            if (!has(tag)) {
                IPax Pax = Instances.Factory().produce(tag, val);
                Pax.Parent(Ancestor());
                _hashMap.put(Pax.Tag(), Pax);
            } else { // a helping hand to put correctly ..
                String tag_ = tag + Statics.Separation + Statics.Next();
                IPax Pax = Instances.Factory().produce(tag, val);
                Pax.Parent(Ancestor());
                _hashMap.put(tag_, Pax);
            } // if
            wasAdded = true;
        } // if
        return wasAdded;
    } // method

    @Override
    public boolean add(IPax Pax) {
        boolean wasAdded = false;
        if (Pax != null) {
            Pax.Parent(Ancestor());
            if (!has(Pax.Tag())) {
                _hashMap.put(Pax.Tag(), Pax);
            } else { // a helping hand to put correctly ..
                String tag = Pax.Tag();
                String tag_ = tag + Statics.Separation + Statics.Next();
                _hashMap.put(tag_, Pax);
            } // if
            wasAdded = true;
        } // if
        return wasAdded;
    } // method

    @Override
    public boolean set(String tag, String val) {
        boolean wasSet = false;
        if (has(tag)) {
            IPax Pax = get(tag);
            if (Pax != null) {
                Pax.Val(val);
                wasSet = true;
            } // if
        } else {
            add(tag, val);
            wasSet = true;
        } // if
        return wasSet;
    } // method

    @Override
    public boolean set(IPax pax) {
        boolean wasSet = false;
        if (pax != null) {
            pax.Parent(Ancestor()); // Set the parent
            if (_hashMap.containsKey(pax.Tag())) {
                _hashMap.replace(pax.Tag(), pax); // Replace or add
            } else {
                _hashMap.put(pax.Tag(), pax);
            } // if
            wasSet = true;
        } // if
        return wasSet;
    } // method

    @Override
    public boolean del(String key) {
        boolean wasDeleted = false;
        if (has(key)) {
            _hashMap.get(key).Parent(null);
            _hashMap.remove(key);
            wasDeleted = true;
        } // if
        return wasDeleted;
    } // method

    @Override
    public boolean del(IPax Pax) {
        boolean wasDeleted = false;
        if (has(Pax)) {
            String key_memento = null;
            IPax Pax_memento = null;
            Set<Map.Entry<String, IPax>> entries = _hashMap.entrySet();
            for (Map.Entry<String, IPax> entry : entries) {
                IPax Pax_ = entry.getValue();
                if (Pax == Pax_) {
                    key_memento = entry.getKey(); // important to take this key ..
                    Pax_memento = Pax_;
                    break;
                } // if
            } // loop
            if (key_memento != null && Pax_memento != null) { // do family business ..
                Pax_memento.Parent(null);
                _hashMap.remove(key_memento);
                wasDeleted = true;
            } // if
        } // if
        return wasDeleted;
    } // method

    @Override
    public boolean del() {
        boolean wasDeleted = false;
        if (!_hashMap.isEmpty()) { // delete em all ..
            _hashMap.clear();
            wasDeleted = true;
        } // if
        return wasDeleted;
    } // method

    @Override
    public int cnt() {
        return _hashMap.size();
    } // method

    @Override
    public List<IPax> all() {
        List<IPax> all = new ArrayList<>();
        if (cnt() > 0) {
            all = _hashMap.values().stream().toList();
        } // if
        return all;
    } // method

    @Override
    public List<IPax> all(String tag) {
        List<IPax> filtered = new ArrayList<>();
        if (cnt() > 0) {
            List<IPax> all = _hashMap.values().stream().toList();
            for (IPax child : all) {
                if (child.Tag().toLowerCase().startsWith(tag.toLowerCase())) {
                    filtered.add(child);
                } // if
            } // loop
        } // if
        return filtered;
    } // method

    @Override
    public <T extends IPax> List<T> typed() {
        List<T> filtered = new ArrayList<>();
        if (cnt() > 0) {
            List<IPax> all = _hashMap.values().stream().toList();
            for (IPax child : all) {
                T typedPax = (T) child;
                filtered.add(typedPax);
            } // loop
        } // if
        return filtered;
    } // method

    @Override
    public <T extends IPax> List<T> typed(String tag) {
        List<T> filtered = new ArrayList<>();
        if (cnt() > 0) {
            List<IPax> all = _hashMap.values().stream().toList();
            for (IPax child : all) {
                if (child.Tag().toLowerCase().startsWith(tag.toLowerCase())) {
                    T typedPax = (T) child;
                    filtered.add(typedPax);
                } // if
            } // loop
        } // if
        return filtered;
    } // method

} // class

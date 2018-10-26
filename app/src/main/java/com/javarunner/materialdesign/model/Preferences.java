package com.javarunner.materialdesign.model;

import java.util.Set;

public interface Preferences {
    public void saveInteger(String key, int value);
    int loadInteger(String key, int defValue);
    void saveStringSet(String key, Set<String> value);
    Set<String> loadStringSet(String key, Set<String> defValue);
}

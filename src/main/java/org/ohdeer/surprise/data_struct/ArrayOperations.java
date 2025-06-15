package org.ohdeer.surprise.data_struct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayOperations {
    private int[] array;

    public ArrayOperations(int size) {
        this.array = new int[size];
    }

    public void set(int index, int value) {
        array[index] = value;
    }

    public int[] getArray() {
        return array;
    }

    public int getSize() {
        return array.length;
    }

    public List<Integer> findDuplicates() {
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();

        for (int num : array) {
            if (!seen.add(num) && !duplicates.contains(num)) {
                duplicates.add(num);
            }
        }
        return duplicates;
    }
}
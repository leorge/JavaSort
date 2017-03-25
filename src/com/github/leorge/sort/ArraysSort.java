package com.github.leorge.sort;

public class ArraysSort implements Algorithm {

   
    @Override
    public String toString() {
        return "sort with a Library: java.util.Arrays.sort()";
    }

    @Override
    public void sort(Object[] a) {
        java.util.Arrays.sort(a);
    }
}

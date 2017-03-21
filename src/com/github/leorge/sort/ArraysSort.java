package com.github.leorge.sort;

public class ArraysSort implements Algorithm {

    private static final String myName = "QsortLib";
    
    @Override
    public String name() {
        // TODO Auto-generated method stub
        return myName;
    }

    @Override
    public String description() {
        return "java.util.Arrays.sort()";
    }

    @Override
    public void sort(Object[] a) {
        java.util.Arrays.sort(a);
    }
}

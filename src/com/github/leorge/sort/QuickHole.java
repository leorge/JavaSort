package com.github.leorge.sort;

/* The simplest new quicksort with a pivot hole */
public class QuickHole implements Algorithm {
    
    private static final String myName = "QuickHole";

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return myName;
    }

    @Override
    public String description() {
        return "The simplest quicksort with a pivot hole";
    }

    @Override
    public void sort(Object[] a) {
        sort(a, 0, a.length - 1);
    }
    
    private void sort(Object[] a, int lo, int hi) {
        if (lo < hi) {
            @SuppressWarnings("unchecked")
            Comparable<Object> pivot = (Comparable<Object>) a[hi];
            int lt = lo, gt = hi, hole = gt--;
            for (; lt < hole; lt++) {
                if (pivot.compareTo(a[lt]) < 0) {
                    a[hole] = a[lt]; hole = lt;
                    for (; gt > hole; gt--) {
                        if (pivot.compareTo(a[gt]) > 0) {
                            a[hole] = a[gt]; hole = gt;
                        }
                    }
                }
            }
            a[hole] = pivot;    // restore the pivot
            sort(a, lo, hole - 1);
            sort(a, hole + 1, hi);
        }
    }
    
    /**
     * @param args filename
     */
    public static void main(String[] args) {
        QuickHole obj = new QuickHole();
        JavaSort.test(obj, args[0]);
    }
}
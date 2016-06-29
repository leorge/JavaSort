package com.github.leorge.sort;

//import java.util.Random;

/* Hybrid sort of quicksort and simple insertion sort */
public class QuickHole implements Algorithm {
    
    private static final String myName = "QuickHole";

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return myName;
    }

    @Override
    public String description() {
        return "prototype of quicksort with hole";
    }

    @Override
    public void sort(Object[] a) {
        sort(a, 0, a.length - 1);
    }
    
    public void sort(Object[] a, int lo, int hi) {
        sort(a, lo, hi, 0);
    }

    private void sort(Object[] a, int lo, int hi, int depth) {
        if (lo < hi) {
            @SuppressWarnings("unchecked")
            Comparable<Object> pivot = (Comparable<Object>) a[hi];
            int lt = lo, gt = hi, eq = lo - 1, hole = gt--;
            for (; lt < hole; lt++) {
                if (pivot.compareTo(a[lt]) <= 0) {
                    a[hole] = a[lt]; hole = lt;
                    for (; gt > hole; gt--) {
                        int chk = pivot.compareTo(a[gt]);
                        if (chk > 0) {
                            a[hole] = a[gt]; hole = gt;
                            eq = lo - 1;
                        }
                        else if (chk < 0) eq = lo - 1;  // clear
                        else if (eq < lo) eq = gt;      // chk == 0 i.e. a[hole] == pivot
                    }
                }
            }
            a[hole] = pivot;    // restore the pivot
            sort(a, lo, hole - 1, depth);
            sort(a, (eq < lo ? hole : eq) + 1, hi, depth);
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
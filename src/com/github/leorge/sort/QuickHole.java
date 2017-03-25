package com.github.leorge.sort;

/* Hybrid sort of quicksort and simple insertion sort */
public class QuickHole implements Algorithm {
    
    @Override
    public String toString() {
        return "the simplest new quicksort with Hole";
    }

    @Override
    public void sort(Object[] a) {
        sort(a, 0, a.length - 1);
    }
    
    public void sort(Object[] a, int lo, int hi) {
        if (lo < hi) {
            @SuppressWarnings("unchecked")
            Comparable<Object> pivot = (Comparable) a[hi];	// Save a pivot
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
}
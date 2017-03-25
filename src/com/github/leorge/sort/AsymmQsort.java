package com.github.leorge.sort;

import java.util.Random;

/* Hybrid sort of quicksort and simple insertion sort */
public class AsymmQsort implements Algorithm {
    
    private static Random rnd = new Random(System.currentTimeMillis() + System.nanoTime());

    @Override
    public String toString() {
        return "Asymmetrick quicksort";
    }

    @Override
    public void sort(Object[] a) {
        sort(a, 0, a.length - 1);
    }
    
    public void sort(Object[] a, int lo, int hi) {
        while (lo < hi) {
            int N = hi - lo + 1, hole;
            if (N < JavaSort.threshold()) {    // A pivot is the middle element
                hole = lo + ((hi - lo) >>> 1);
            } else {  // median of 5
                int p1, p2, p3, p4, p5, distance = (N >>> 3) + (N >>> 4);	// = 3N/16
                p5 = (p4 = (p3 = (p2 = (p1 = lo + rnd.nextInt(N >>> 2)) + distance) + distance) + distance) + distance;
                int t;  // temporary index
                if (((Comparable) a[p2 ]).compareTo(a[p4]) < 0) {t = p2; p2 = p4; p4 = t;}		// a[p2] <--> a[p4]
                if (((Comparable) a[p3]).compareTo(a[p2]) < 0) {t = p3; p3 = p2; p2 = t;}		// a[p2] <--> a[p3]
                else if (((Comparable) a[p4]).compareTo(a[p3]) < 0) {t = p3; p3 = p4; p4 = t;} 	// a[p3] <--> a[p4] 
                if (((Comparable) a[p1]).compareTo(a[p5]) > 0) {t = p1; p1 = p5; p5 = t;}    // --> a[p1] < a[p5]
                hole = ((Comparable) a[p3]).compareTo(a[p1]) < 0 ? (((Comparable) a[p1]).compareTo(a[p4]) < 0 ? p1: p4):
                      (((Comparable) a[p5]).compareTo(a[p3]) < 0 ? (((Comparable) a[p5]).compareTo(a[p2]) < 0 ? p2: p5): p3);
            }

            @SuppressWarnings("unchecked")
            Comparable<Object> pivot = (Comparable) a[hole]; a[hole] = a[hi];	// pivot <-- a[hole] <- a[hi]
            int gt = (hole = hi) - 1, lt = lo, eq = -1;
            for (int chk; lt < hole; lt++) {
                if ((chk = pivot.compareTo(a[lt])) <= 0) {
                	if (chk < 0) eq = -1;
                	else if (eq < 0) eq = hole;
                    a[hole] = a[lt]; hole = lt;
                    for (; gt > hole; gt--) {
                        if ((chk = pivot.compareTo(a[gt])) > 0) {
                            a[hole] = a[gt]; hole = gt;
                        }
                        else if (chk < 0) eq = -1;	// clear
                        else if (eq < lo) eq = gt;  // chk == 0 i.e. a[hole] == pivot
                    }
                }
            }
            a[hole] = pivot;    // restore the pivot
            if (eq < 0) eq = hole;
            int n_lo = hole - lo;	// the number of elements in the left sub-array
            int n_hi = hi - eq;
            if (n_lo < n_hi) {	// The left sub-array is shorter than the right sub-array.
            	sort(a, lo, hole - 1);	// sort the shorter left sub-array
            	lo = eq + 1;			// prepare to sort the right sub-array
            } else {
            	sort(a, eq + 1, hi);
            	hi = hole - 1;
            }
        }
    }
    
    /**
     * @param args filename
     */
    public static void main(String[] args) {
        AsymmQsort obj = new AsymmQsort();
        JavaSort.test(obj, args[0]);
    }
}
package com.github.leorge.sort;

import java.util.Random;

/* Hybrid sort of quicksort and simple insertion sort */
public class QuickRandom implements Algorithm {
    
    private static Random rnd = new Random(System.currentTimeMillis() + System.nanoTime());
    private static final String myName = "QuickRandom";

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return myName;
    }

    @Override
    public String description() {
        return "quicksort median of random elements";
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
            int N = hi - lo + 1;
            int distance, lower, middle, higher, median;
            if (N < 9) {    // 3 elements * 3 blocks
                median = lo + ((hi - lo) >>> 1);
            } else if (N < 32){ // 2^5
                distance = N / 3;
                lower = distance >> 1;
                higher = (middle = (lower += lo) + distance) + distance;
                median = ((Comparable) a[lower ]).compareTo(a[higher]) < 0 ?
                        (((Comparable) a[middle]).compareTo(a[lower])  < 0 ? lower:  (((Comparable) a[middle]).compareTo(a[higher]) < 0 ? middle: higher)) :
                        (((Comparable) a[middle]).compareTo(a[higher]) < 0 ? higher: (((Comparable) a[middle]).compareTo(a[lower])  < 0 ? middle: lower));
            }
            else {  // N >= 2^5
                int highest, lowest = (distance = N / 5) >> 1;  // choose the median of 5 elements
                if (depth < 3) {
                    ++depth;
                    lowest = rnd.nextInt(distance); // 0 <= lower < N / 5
                }
                highest = (higher = (middle = (lower = (lowest += lo) + distance) + distance) + distance) + distance;
                int t;  // temporary index
                if (((Comparable) a[lowest]).compareTo(a[highest]) > 0) {t = lowest; lowest = highest; highest = t;}    // --> a[lowest] < a[highest]
                if (((Comparable) a[lower ]).compareTo(a[higher]) < 0) {    // l < h
                    if (((Comparable) a[middle]).compareTo(a[lower]) < 0) {t = middle; middle = lower; lower = t;}  // m < l < h
                    else if (((Comparable) a[higher]).compareTo(a[middle]) < 0) {t = middle; middle = higher; higher = t;}  // l < h < m
                } else if (((Comparable) a[middle]).compareTo(a[higher]) < 0) {t = middle; middle = higher; higher = lower; lower = t;} // m < h < l
                else if (((Comparable) a[lower]).compareTo(a[middle]) < 0) {t = middle; middle = lower; lower = higher; higher = t;}    // h < l < m
                else {t = lower; lower = higher; higher = t;}   // h < m < l
                median = ((Comparable) a[middle]).compareTo(a[lowest]) < 0 ? (((Comparable) a[lowest]).compareTo(a[higher]) < 0 ? lowest: higher) :
                      (((Comparable) a[highest]).compareTo(a[middle]) < 0 ? (((Comparable) a[highest]).compareTo(a[lower]) < 0 ?   lower: highest): middle);
            }

            @SuppressWarnings("unchecked")
            Comparable<Object> pivot = (Comparable) a[median];
            int lt = lo, gt = hi, eq = lo - 1, hole;
            a[median] = a[hole = gt--];
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
        QuickRandom obj = new QuickRandom();
        JavaSort.test(obj, args[0]);
    }
}
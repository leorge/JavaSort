package com.github.leorge.sort;

import java.util.Random;

/* Hybrid sort of quicksort and simple insertion sort */
public class AsymmetricQuicksort implements Algorithm {

    private static Random rnd = new Random(System.currentTimeMillis() + System.nanoTime());
    private static final String myName = "QuickPivot";

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return myName;
    }

    @Override
    public String description() {
        return "Asymmetric quicksort";
    }

    @Override
    public void sort(Object[] a) {
        sort((Comparable[])a, 0, a.length - 1);
    }
    
    private void sort(Comparable[] a, int lo, int hi) {
        if (lo < hi) {
            int N = hi - lo + 1;
            int distance, p1, p2, p3, p4, p5, median;
            if (N < JavaSort.middle()) {    // middle
                median = lo + (N >>> 1);
            } else if (N < JavaSort.med3()){ // median-of-3
                p3 = (p2 = (p1 = lo + rnd.nextInt(N >> 1)) + (distance = N >>> 2)) + distance;	// [0, N/2), [N/4, 3N/4), [N/2, N)+
                median = (a[p1]).compareTo(a[p3]) < 0 ?
                        ((a[p2]).compareTo(a[p1]) < 0 ? p1: ((a[p2]).compareTo(a[p3]) < 0 ? p2: p3)) :
                        ((a[p2]).compareTo(a[p3]) < 0 ? p3: ((a[p2]).compareTo(a[p1]) < 0 ? p2: p1));
            }
            else {  // median-of-5
                p5 = (p4 = (p3 = (p2 = (p1 = lo + rnd.nextInt((N >>> 2))) + (distance = (N >>> 3) + (N >>> 4))) + distance) + distance) + distance;
                int t;  // temporary index
                if ((a[p2]).compareTo(a[p4]) > 0) {t = p2; p2 = p4; p4 = t;}
                if ((a[p2]).compareTo(a[p3]) > 0) {t = p3; p3 = p2; p2 = t;}
                else if ((a[p3]).compareTo(a[p4]) > 0) {t = p3; p3 = p4; p4 = t;}
                if ((a[p1]).compareTo(a[p5]) > 0) {t = p1; p1 = p5; p5 = t;}
                median = (a[p3]).compareTo(a[p1]) < 0 ? ((a[p1]).compareTo(a[p4]) < 0 ? p1: p4):
                        ((a[p5]).compareTo(a[p3]) < 0 ? ((a[p5]).compareTo(a[p2]) < 0 ? p2: p5): p3);
            }

            @SuppressWarnings("unchecked")
            Comparable<Object> pivot = (Comparable<Object>) a[median];
            int lt = lo, gt = hi, eq = -1, hole;
            a[median] = a[hole = gt--];
            for (int chk; lt < hole; lt++) {
                if ((chk = pivot.compareTo(a[lt])) <= 0) {
                	if (chk < 0) eq = -1;
                	else if (eq < 0) eq = hole;
                    a[hole] = a[lt]; hole = lt;
                    for (; gt > hole; gt--) {
                        chk = pivot.compareTo(a[gt]);
                        if (chk > 0) {
                            a[hole] = a[gt]; hole = gt;
                            eq = -1;
                        }
                        else if (chk < 0) eq = -1;  // clear
                        else if (eq < 0) eq = gt;      // chk == 0 i.e. a[hole] == pivot
                    }
                }
            }
            a[hole] = pivot;    // restore the pivot
            sort(a, lo, hole - 1);
            sort(a, (eq < 0 ? hole : eq) + 1, hi);
        }
    }
    
    /**
     * @param args filename
     */
    public static void main(String[] args) {
        AsymmetricQuicksort obj = new AsymmetricQuicksort();
        JavaSort.test(obj, args[0]);
    }
}
package com.github.leorge.sort;

import java.security.InvalidParameterException;
import java.util.ArrayList;

// https://commons.apache.org/proper/commons-math/
import org.apache.commons.math3.stat.descriptive.SynchronizedSummaryStatistics;

public class ElapsedTime {
    
    ArrayList<Long> array = new ArrayList<Long>();
    int maxIndex = 0;   // for possible error
    long maxValue = 0;
    int skip = 1;       // for systematic error
    
    public void skipCount(int count) {
        if (count < 0) throw new InvalidParameterException("Ain't you in the negative world?");
        skip = count;
    }
    
    public void clear() {
        array.clear();
    }

    public void add(long elapsedTime) {
        if (elapsedTime <= 0) throw new InvalidParameterException("Ain't you a time traveler?");
        int count = array.size();
        if (count < skip) {
            maxIndex = 0;
            maxValue = 0;
        } else if (count == skip) {
            maxValue = elapsedTime;
            maxIndex = count;
        } else if (elapsedTime > maxValue) {
            maxValue = elapsedTime;
            maxIndex = count;
        }
        array.add(elapsedTime);
    }
    
    public double mean() {
        int count = array.size();
        SynchronizedSummaryStatistics stats = new SynchronizedSummaryStatistics();
        stats.clear();
        for (int i = skip; i < count; i++)
            if (i != maxIndex) stats.addValue(array.get(i));
        return count > skip ? stats.getMean(): 0;
    }
    
    public double stdDev() {
        int count = array.size();
        SynchronizedSummaryStatistics stats = new SynchronizedSummaryStatistics();
        stats.clear();
        for (int i = skip; i < count; i++)
            if (i != maxIndex) stats.addValue(array.get(i));
        return count > skip ? stats.getStandardDeviation() : 0;
    }

    public String result(String msg) {
        int count = array.size();
        switch (count) {
        case 0:
            return "empty";
        case 1:
            return "data = " + array.get(0);
        default:
            StringBuilder buf = new StringBuilder();
            double avg = mean();
            double spread = stdDev();
            buf.append(msg);
            buf.append("\tusec = "); buf.append(usec(avg));
            buf.append("\tspread = "); buf.append(usec(spread));
            buf.append("\t"); buf.append((int)(100. * spread / avg + 0.5)); buf.append(" %");
            for (int i = 0; i < skip; i++)
                buf.append(" [" + usec(array.get(i)) + "]");
            for (int i = skip; i < count; i++) {
                buf.append(" ");
                long elapsed = array.get(i);
                if (i != maxIndex) buf.append(usec(elapsed));
                else buf.append("(" + usec(elapsed) + ")");
            }
            return new String(buf);
        }
    }
    
    private String usec(double nano) {  // nono seconds --> micro seconds
        return String.format("%.0f", nano / 1000.);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        String strArray[] = JavaSort.readStrArray(args.length > 0 ? args[0] : "/dev/stdin");
        if (strArray == null || strArray.length == 0) return;
        ElapsedTime obj = new ElapsedTime();
        for (int i = 0; i < strArray.length; i++) obj.add(Long.parseLong(strArray[i]));
        System.out.println(obj.result("main()"));
    }
}

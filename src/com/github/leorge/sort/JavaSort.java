package com.github.leorge.sort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import com.townleyenterprises.command.*;    // http://te-code.sourceforge.net
public class JavaSort {
    private Algorithm sorter;
    private static int myThreshold = 64;

    public JavaSort(Algorithm obj) {
        // TODO Auto-generated constructor stub
        sorter = obj;
    }
    
    public void sort(Object[] a) {
        sorter.sort(a);
    }

    public static int threshold() {
        return myThreshold;
    }

    public static String join(Object[] a){
        return join(a, 0, a.length - 1);
    }

    private static String join(Object[] a, int lo, int hi) {
        StringBuilder buf = new StringBuilder();
        while (lo <= hi) buf.append(":" + a[lo++]);
        return buf.toString().substring(1);     
    }
    
    public static String dumpArray(Object[] a) {
        return dumpArray(a, 0, a.length - 1);
    }
    public static String dumpArray(Object[] a, int lo, int hi) {
        int size = hi - lo + 1;
        if (size <= 32) return join(a, lo, hi);
        else return join(a, lo, lo + 15) + ":...:" + join(a, hi - 15, hi);
    }
    
    public static String[] readStrArray(String filename) {
        String fileContentStr = null;
        try {
            byte[] fileContentBytes;
            fileContentBytes = Files.readAllBytes(Paths.get(filename));
            fileContentStr = new String(fileContentBytes, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            System.out.println("File error : " + e.getMessage());
            return null;
        }
        String strArray[] = fileContentStr.split("\n");
//      System.out.println("data count = " + strArray.length + "  " + dumpArray(strArray));
        return strArray;
    }
    
    static boolean compareStr(String name, final String[] source, final String[] sorted) {
//      if (! isSorted(sorted, name + " failed to sort. - ")) return false;
        String[] check = source.clone();
        java.util.Arrays.sort(check);       
        for (int i = 0; i < sorted.length; ++i) {
            if (! check[i].equals(sorted[i])) {
                System.out.println("right : " + dumpArray(sorted));
                System.out.println("wrong : " + dumpArray(source));
                System.out.println(name + " : Data a[" + i + "] = " + sorted[i] + " should be " + check[i]);        
                return false;
            }
        }
        return true;
    }

    static void test(Algorithm obj, String filename) {
        String[] source = readStrArray(filename.isEmpty() ? "/dev/stdin" : filename);
        if (source == null || source.length == 0) return;
        String[] sorted = source.clone();
        obj.sort(sorted);
        
        // sort as String
        if (! compareStr(obj.name(), source, sorted)) System.exit(0);
        System.out.println("OK - " + dumpArray(sorted));        
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        ElapsedTime eTime = new ElapsedTime();

        Map<String, Algorithm> programs = new HashMap<String, Algorithm>();
        programs.put("l", new QsortLib());
        programs.put("h", new QuickHole());
        programs.put("r", new QuickPivot());
        
        String postAmble = "Algorithm:\n";
        for (String key : programs.keySet()) {
            postAmble += "  " + key + " : " + programs.get(key).description() + "\n";
        }
        CommandOption cmdCutOff = new CommandOption("threshold", 'C', true, "<N>", "Cut-off number.");
        CommandOption cmdNum = new CommandOption("Number", 'N', true, "<N>", "Number of elements.");
        CommandOption cmdRepeat = new CommandOption("repeat", 'R', true, "<times>", "Repeat count to sort (skip+10).");
        CommandOption cmdSkip = new CommandOption("skip", 'S', true, "<count>", "number of Skip data.");
        CommandOption cmdPass = new CommandOption("pass", 'T', true, "<percent>", "uncertainty percenT to pass a Test (2%).");
        CommandOption[] mainOptions = { cmdCutOff, cmdNum, cmdRepeat, cmdSkip, cmdPass};
        
        // parse command.
        
        CommandParser parser = new CommandParser("JavaSort", "Algorithms [DataFile]");
        parser.addCommandListener(new DefaultCommandListener("options", mainOptions));
        parser.setExtraHelpText("", postAmble);
        parser.parse(args);

        if (cmdCutOff.getMatched()) {   // threshold to switch algorithm
            myThreshold = Integer.parseInt(cmdCutOff.getArg());
        }
        
        int N = 0;  // Number of elements
        if (cmdNum.getMatched()) {
            N = Integer.parseInt(cmdNum.getArg());
            if (N <= 0) throw new InvalidParameterException("The number of elements must be a positive number.");
        }

        int skipCount = 1;
        if (cmdSkip.getMatched()) { // skip count
            skipCount = Integer.parseInt(cmdSkip.getArg());
        }
        try {
            eTime.skipCount(skipCount);
        }
        catch (Exception e) {
            System.out.println(e);
            return;
        }
        
        int repeatCount = skipCount + 10;   // repeat times
        if (cmdRepeat.getMatched()) {
            repeatCount = Integer.parseInt(cmdRepeat.getArg());
            if (repeatCount <= 0) throw new InvalidParameterException("repeat count must be a positive number.");
        }

        double limit = 0.025;   // less than 2%
        if (cmdPass.getMatched()) {
            int i = Integer.parseInt(cmdPass.getArg());
            if (i <= 0) throw new InvalidParameterException("Threshold to pass a test must be a positive percent.");
            limit = (2. * i + 1.) / 200.;
        }
        
        String[] largs = parser.getUnhandledArguments();
        if (largs.length == 0) return;  // No algorithm
        CharSequence algorithms = largs[0];
        
        // read data.
        
        String[] readBuffer = readStrArray(largs.length > 1 ? largs[1] : "/dev/stdin");
        if (readBuffer == null) return;
        else {
            int i = readBuffer.length;
            if (i == 0) return;     // no data
            if (N == 0 || N > i) N = i;
        }
        
        String[] strArray = new String[N];
        System.arraycopy(readBuffer, 0, strArray, 0, N); readBuffer = null;

        // sort

        for (int pos = 0; pos < algorithms.length(); pos++) {
            Algorithm sorter = programs.get(String.valueOf(algorithms.charAt(pos)));
            if (sorter != null) {
                String[] strDup = null;
                do {    // evaluate
                    eTime.clear();
                    for (int i = 0; i < repeatCount; i++) {
                        strDup = strArray.clone();          
                        long startTime = System.nanoTime();
                        sorter.sort(strDup);
                        eTime.add(System.nanoTime() - startTime);
                    }
                    System.out.println(eTime.result(sorter.name()));
                } while (eTime.stdDev() / eTime.mean() >= limit);
                // check result
                if (! compareStr(sorter.name(), strArray, strDup)) return;
            }
            else System.out.println("Algorithm \"" + algorithms.charAt(pos) + "\" is undefined.");
        }
    }
}

# JavaSort

I improved quicksort faster than java.util.Arrays.sort(Object [ ]).

This project is based on my another
[project](https://github.com/leorge/qmisort)
in C language.

If you have installed eclipse,
change current directory to your workspace.

    $ cd  <work space>

Download this project.  

    $ git clone https://github.com/leorge/JavaSort
    $ cd JavaSort
If you don't want to installed **git**,
click **<> code** button on the top tab in this page,
and click **Download ZIP** button at the right of the page.
However, I recommend to install git
because it is easy to update files by the following command.  

    $ git pull

This project needs 2 jar files.

1. [Apache Commons Math 3](https://commons.apache.org/math/download_math.cgi)
to calculate statistics.
1. [Command line parser](http://te-code.sourceforge.net/).

Extract **commons-math3-3.5.jar** in the
[commons-math3-3.5-bin.tar.gz](http://www.us.apache.org/dist//commons/math/binaries/commons-math3-3.5-bin.tar.gz)
and **te-common.jar** in the
[te-common-3.0.0-pre3.zip](http://sourceforge.net/projects/te-code/files/latest/download?source=files)
to bin/ sub-directory.  

The following list exhibits a sample of test.

    $ ./random.awk 12 | xargs echo     # sample of output
    10 03 08 07 05 11 03 04 11 06 08 00
    $ ./random.awk 100000 | ./run -T 10 lhm    # test
    QsortLib    usec = 43478 spread = 16658 38 % [184867] 87865 (90076) 39435 37565 37188 37766 37951 38148 38108 37280
    QsortLib    usec = 37519 spread = 105   0 % [37555] 37389 37543 37521 (37852) 37409 37688 37558 37448 37657 37454
    QuickHole   usec = 37734 spread = 111   0 % [164494] 37612 37763 37854 37616 37877 37844 37646 (38872) 37775 37623
    QuickMedian usec = 32596 spread = 314   1 % [161201] 32228 32946 32423 (33899) 32619 32151 32602 32970 32973 32449
> [random.awk](https://github.com/leorge/JavaSort/blob/master/random.awk)
: awk script to generate a random data sequence.  
xargs : linux command to build and execute command lines from standard input  
echo : linux command to  display a line of text  
[run](https://github.com/leorge/JavaSort/blob/master/run)
: shell script to launch a test program to sort String[ ].  
-T 10 : Standard deviation is 10% to pass the **T**est.
Default is 2%.  

If you took a long time to complete a test,
retry the test on the CUI console
launched by Ctrl+Alt+F1-F6 keys after GUI logout.
Press Ctrl+Alt+F7 to return to GUI login screen.  

The first field in the output lines is a class name.
[**QsortLib**](https://github.com/leorge/JavaSort/blob/master/src/com/github/leorge/sort/QsortLib.java)
sorts a String array as Object[ ] by java.util.Arrays.*sort*().
[**QuickHole**](https://github.com/leorge/JavaSort/blob/master/src/com/github/leorge/sort/QuickHole.java)
is the simplest prototype of quicksort algorithm with
[hole instead of swaps](https://github.com/leorge/qmisort/wiki/Hole-instead-of-swaps).
[**QuickMedian**](https://github.com/leorge/JavaSort/blob/master/src/com/github/leorge/sort/QuickMedian.java)
chooses a pivot at the middle of array when N \< 9(=3\*3),
median of 3 elements when N \< 32(=2^5) else median of 5 using random numbers,
where N is the number of elements.  
The 4th field is mean of 10 elapsed time in 12 data.
The first data enclosed in square bracket is omitted because every time large.
This is a systematic error.
And the largest data in 11 data, enclosed in parentheses, is also omitted because sometime it is large.
This is a possible error that may caused by randomness or interrupt handlers.  
The 7th filed is standard deviation (STDEV) of 10 data.  
The 8th field is percentage of STDEV: 100 \* Stdev / Mean.
The test repeats until the percentage becomes 2% or less.

The following chart exhibits the relative performances of the algorithms.
Y axis is normalized ***elapsed time* / *n*log(*n*)**,
where the elapsed time of quick_hole() at N=2^20=1M is 1.

![performance](https://github.com/leorge/JavaSort/blob/master/performance.png)  
> [data](https://github.com/leorge/JavaSort/blob/master/compare.sh)

**QsortLib is the slowest** because the algorithm of
[java.util.Arrays](http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/7u40-b43/java/util/Arrays.java?av=f).sort(Object[ ] a)
is
[ComparableTimSort](http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/7u40-b43/java/util/ComparableTimSort.java#ComparableTimSort.sort%28java.lang.Object%5B%5D%29).
[TimSort](https://en.wikipedia.org/wiki/Timsort) is a hybrid sort of merge sort and insertion sort,
invented by Tim Peters:
the number of copies in merge sort is about 2 times of it in quicksort as below.
The following list exhibits numbers of times in my another
[C project](https://github.com/leorge/qmisort/wiki/Hybrid-sort).

    $ N=10000; src/random.awk $N | Debug/Sort -N $N -mrqC 3 -D 3 -A M -l 8 -V 1
    arguments : -N 10000 -mrqC 3 -D 3 -A M -l 8 -V 1
    qsort(3)       usec = 8893 call = 0     compare = 120417 copy = 0
    merge_sort()   usec = 4716 call = 9999  compare = 120417 copy = 130416
    quick_random() usec = 4648 call = 6247  compare = 139147 copy = 73943
    quick_hybrid() usec = 4602 call = 10000 compare = 120447 copy = 15032
The number copy copies in merge_sort() is added number of comparisons and calls
because every comparisons bring about coping and when a sub-array empties another sub-array is merged.
On the contrary, probability of copies after comparisons is 0.5 in quicksort.
The number of copies in quick_hybrid() is the least because it calls **indirect** merge sort.
The number of copies in
[indirect sort](https://github.com/leorge/qmisort/wiki/Indirect-sort)
is nearly N.

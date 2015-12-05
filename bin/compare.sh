#!/bin/sh
sed -i -e '1,/^@/!d' $0    # Delete data part
LANG=C; date -u >> $0       # Add date
uname -norpv >> $0
echo "operator : `id -un`" >> $0
echo "option   : $*" >> $0
#
# Scan N
#
if [ $# -lt 2 ]; then
    echo "usage : $0 max_N options"
    echo "\tmax_N - The max. number of elements in power of 2"
    exit
fi

./evaluate.sh $*
OUT=tmp.evaluate.sh
expand -t `./col.awk $OUT` $OUT >> $0
exit
@
Fri Dec  4 00:32:28 UTC 2015
FX8300 3.13.0-71-generic #114-Ubuntu SMP Tue Dec 1 02:34:22 UTC 2015 x86_64 GNU/Linux
operator : leo
option   : 22 lhm
1           12    13   14   15    16    17    18     19     20     21      22
QsortLib    922   1976 4324 9658  22218 50241 120748 293563 688396 1569430 3569299
QuickHole   882   1954 4452 9385  21636 49161 117624 284912 659787 1477572 3534181
QuickMedian 807   1790 3888 8627  19151 43004 104870 252376 580635 1297765 3066357

2           12    13   14   15    16    17    18     19     20     21      22
QsortLib    913   1946 4361 9662  21917 49955 120295 291070 683732 1573877 3556470
QuickHole   882   1923 4279 9446  22172 48084 115466 285590 626320 1474344 3436907
QuickMedian 808   1776 3838 8516  19488 43340 104387 251960 581589 1365501 3012480

3           12    13   14   15    16    17    18     19     20     21      22
QsortLib    3147  1974 4433 10172 22357 50362 121081 291160 684800 1566291 3531867
QuickHole   835   1910 4254 9365  22480 47619 117199 274424 661293 1494028 3412853
QuickMedian 813   1770 3828 8321  19924 42899 103968 249409 584372 1305321 3039809

4           12    13   14   15    16    17    18     19     20     21      22
QsortLib    870   1973 4363 9815  22231 50010 119379 292332 679382 1576627 3547082
QuickHole   11554 1873 4304 9138  21157 46680 119482 272584 661716 1450046 3401476
QuickMedian 11573 1771 3837 8373  19420 43056 108758 248221 565758 1282849 3018557

5           12    13   14   15    16    17    18     19     20     21      22
QsortLib    5882  2055 4395 9818  22444 50488 121304 290401 684780 1568502 3545142
QuickHole   858   1932 4224 9304  21505 47429 118548 291523 658319 1398794 3427441
QuickMedian 816   1777 3838 8488  19394 43061 105033 247420 569308 1296045 3012883

6           12    13   14   15    16    17    18     19     20     21      22
QsortLib    881   2005 4411 9677  22428 50223 120572 290390 686899 1576629 3576925
QuickHole   11596 1916 4430 9220  21752 47888 118210 285150 636594 1553207 3499132
QuickMedian 810   1773 3856 8492  19265 42402 105646 249161 577742 1315162 3077133

7           12    13   14   15    16    17    18     19     20     21      22
QsortLib    893   1996 4723 9660  22201 50051 120710 292167 689472 1566540 3559788
QuickHole   871   1876 4224 9385  22083 46220 116005 292901 643287 1500714 3540949
QuickMedian 807   1761 3950 8491  19328 42983 105691 249689 572133 1310821 3034641

8           12    13   14   15    16    17    18     19     20     21      22
QsortLib    877   1970 4369 9668  22356 50295 120161 291774 685094 1558885 3564549
QuickHole   846   1982 4465 9332  21988 49029 122098 272717 642920 1464074 3482845
QuickMedian 823   1782 3834 8486  19189 42808 104337 249593 569707 1299362 3086888

9           12    13   14   15    16    17    18     19     20     21      22
QsortLib    7670  1994 4372 9711  22059 50335 121121 297405 687981 1582855 3574200
QuickHole   865   2013 4100 9243  22071 49632 118619 287425 633845 1469947 3604624
QuickMedian 803   1788 3857 8604  18978 44849 104445 250446 582130 1345828 3147210

10          12    13   14   15    16    17    18     19     20     21      22
QsortLib    885   2006 4447 9852  23158 51156 120584 295425 684716 1566101 3541632
QuickHole   880   1918 4246 9458  20787 46448 117393 286584 628810 1576621 3549452
QuickMedian 803   1811 3961 8448  19246 42403 104380 254150 571113 1308693 3061498


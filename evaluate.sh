#!/bin/sh
#
# Scan N
#
if [ $# -lt 2 ]; then
    echo "usage : $0 max_N options"
    echo "\tmax_N - The max. number of elements in power of 2"
    exit
fi

rm -f tmp$$
to=$1; shift	# get parameters
for i in `seq 1 10`; do
    ./random.awk `echo 2^$to | bc` > data
    for j in `seq 12 $to`; do
        N=`echo 2^$j | bc`; echo "$i:$j" | tee -a tmp$$
        ./run -N $N $* ../data | tee -a tmp$$
    done;
done
echo ""

OUT=../tmp.`basename $0`
awk -f - tmp$$ <<'EOF' | tee $OUT
BEGIN {OFS = "\t"}
NF==1 {
    split($1, info, ":")
    seq = info[1]; N = info[2]; # Sequence and log2(N)
    if (seq != last_seq) {
        seq_list = seq_list OFS seq;
        last_seq = seq;
        last_N = 0; headline[seq] = seq;
    }
    if (N > last_N) {     	# max sequence
        headline[seq] = headline[seq] OFS N;
        last_N = N;
    }
}
$2=="usec" {
    usec[seq, N, $1] = $4;
    if (! (a = index(functions OFS, OFS $1 OFS)))
        functions = functions OFS $1;
}
END {
    S = split(substr(seq_list, 2), sequences, OFS);
    F = split(substr(functions, 2), fname, OFS);
    for (s=0; s++ < S; ) {
        seq = sequences[s];
        head = headline[s];
        print head;
        N = split(head, num, OFS);
        for (f=0; f++ < F; ) {
            name = fname[f]; buff = name;
            for (n = 1; n++ < N;) buff = buff OFS usec[seq, num[n], name];
            print buff;
        }
        print "";
    }
}
EOF

rm  tmp$$

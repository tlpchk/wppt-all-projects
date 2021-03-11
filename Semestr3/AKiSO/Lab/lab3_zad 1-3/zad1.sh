#!/bin/bash


BARCHART(){
MAXVALUE=$( sort -nr data | head -n1 )
CHARTHEIGHT=20 #$MAXVALUE

CHARTLINE=$CHARTHEIGHT 
echo $(echo "$MAXVALUE*2"|bc) kB/s
while [ $CHARTLINE -gt 0 ]; do
    (( CHARTLINE-- ))
    REDUCTION=$(( $MAXVALUE*$CHARTLINE/$CHARTHEIGHT ))
    while read VALUE; do
        VALUE=$(( $VALUE-$REDUCTION ))
        if [ $VALUE -le 0 ]; then  
	     echo -n "    "
        else
             echo -n "▓▓▓ "
        fi
    done < ./data
    echo
done
echo
}

while [ 1==1 ]
do
speed_of_read_1=$(echo ` cat  /proc/diskstats| awk 'FNR == 11 {print $6}'`);
speed_of_write_1=$(echo ` cat  /proc/diskstats| awk 'FNR == 11 {print $10}'`);
#load_avg_1=$(echo` echo  "$(cat  /proc/loadavg| awk '{print $1}')*2"|bc`);

sleep 1

speed_of_read_2=$(echo ` cat  /proc/diskstats| awk 'FNR == 11 {print $6}'`);
speed_of_write_2=$(echo ` cat  /proc/diskstats| awk 'FNR == 11 {print $10}'`);
#load_avg_2=$(echo` echo  "$(cat  /proc/loadavg| awk '{print $1}')*2"|bc`);

read_per_sec=$(echo "2*($speed_of_read_2-$speed_of_read_1)"|bc);
write_per_sec=$(echo "2*($speed_of_write_2-$speed_of_write_1)"|bc);
if [[ $(cat data|wc -l) > 6 ]]; then
	(sed -e '1d' data)>data1
	 mv data1 data
fi
echo $write_per_sec>>./data
clear
BARCHART
done

#!/bin/sh

wget -q -O catxml.xml http://thecatapi.com/api/images/get?format=xml&results_per_page=1 
wait
wget -q -O catjpg.jpg `  xmllint --xpath "string(//url)" catxml.xml`
img2txt catjpg.jpg
wget -q -O joke http://api.icndb.com/jokes/random
cat joke|jq '.value .joke'


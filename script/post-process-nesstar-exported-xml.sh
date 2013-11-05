#!/bin/bash

tempfile=.tmp

for f in ../roda-nesstar/*.xml
do
    echo $f
    sed 's/^<codeBook version="1.2.2" ID="/&RODA/' <"$f" >"$tempfile"
    mv -f "$tempfile" "$f"
done
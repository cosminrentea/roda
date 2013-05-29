#!/bin/bash

OFILE=tables/process-doc-collated.htm
rm $OFILE

echo '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type"><Link href="../css/doc.css" rel="stylesheet" type="text/css"></head><body>' >>$OFILE

for f in tables/*.html
do
    cat $f | tail -n +19 | head -n -2 >>$OFILE
done

echo "</body></html>" >>$OFILE

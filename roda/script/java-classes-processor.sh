#!/bin/bash

tmpfile=.tmp
for javafile in ../src/ddi122/*.java
do
sed '
s/^.*package .*$/& import javax.persistence.Entity;import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;import javax.persistence.Id;import javax.xml.bind.annotation.XmlTransient;/g
s/^.*public class .*$/@Entity & @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @XmlTransient private long id_; public long getId_() { return id_;} public void setId_(long id_) { this.id_ = id_; } /g
' <$javafile >$tmpfile
rm $javafile
mv $tmpfile $javafile
done
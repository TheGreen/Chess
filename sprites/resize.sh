#!/usr/bin/env bash

for sprite in *.png
do
    convert $sprite -resize 90x90 "90_"$sprite
    convert $sprite -resize 50x50 "50_"$sprite
done

#!/bin/sh

if [ $# -gt 0 ]; then
    _JAVA_AWT_WM_NONREPARENTING=1 exec ./gradlew bootRun --args="$*"
fi

 _JAVA_AWT_WM_NONREPARENTING=1 exec ./gradlew bootRun


#!/bin/sh

ARGS=""
if [ $# -gt 0 ]; then
    ARGS=--args="$*"
fi

_JAVA_AWT_WM_NONREPARENTING=1 exec ./gradlew bootRun "$ARGS"

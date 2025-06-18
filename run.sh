#!/bin/sh

_JAVA_AWT_WM_NONREPARENTING=1 exec ./gradlew bootRun --args="$*"

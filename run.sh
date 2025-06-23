#!/bin/sh

# if first run gradlew is not available
if [ -f "./gradlew" ]; then
    GRADLE_CMD="./gradlew"
else
    GRADLE_CMD="gradle"
fi

if [ $# -gt 0 ]; then
    _JAVA_AWT_WM_NONREPARENTING=1 exec $GRADLE_CMD bootRun --args="$*"
else
    _JAVA_AWT_WM_NONREPARENTING=1 exec $GRADLE_CMD bootRun
fi

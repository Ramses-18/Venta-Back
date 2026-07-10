#!/bin/sh
# Maven Wrapper script
# Adapted from https://github.com/apache/maven-wrapper

set -e

MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(cd "$(dirname "$0")" && pwd)}"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

if [ -f "$MAVEN_WRAPPER_PROPERTIES" ]; then
    MAVEN_DIST_URL=$(grep "distributionUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)
    WRAPPER_URL=$(grep "wrapperUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)
fi

MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-3.9.8"

if [ ! -d "$MAVEN_HOME" ]; then
    mkdir -p "$MAVEN_HOME"
    echo "Downloading Maven..."
    curl -fsSL "$MAVEN_DIST_URL" -o /tmp/maven.zip
    unzip -q /tmp/maven.zip -d "$HOME/.m2/wrapper/dists/"
    rm /tmp/maven.zip
fi

MAVEN_CMD="$MAVEN_HOME/apache-maven-3.9.8/bin/mvn"

# Download wrapper jar if needed
WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
if [ ! -f "$WRAPPER_JAR" ] && [ -n "$WRAPPER_URL" ]; then
    curl -fsSL "$WRAPPER_URL" -o "$WRAPPER_JAR"
fi

exec "$MAVEN_CMD" "$@"
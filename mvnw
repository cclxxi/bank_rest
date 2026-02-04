#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Maven Wrapper startup batch script, version 3.2.0
#
# Optional ENV vars
# -----------------
#   JAVA_HOME - location of a JDK home dir, the default is "java"
#   MAVEN_OPTS - parameters passed to the Java VM when running Maven
#     e.g. to debug Maven itself, use
#       set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
#   MAVEN_SKIP_RC - flag to disable loading of mavenrc files
# ----------------------------------------------------------------------------

if [ -z "$MAVEN_SKIP_RC" ]; then
  if [ -f /etc/mavenrc ]; then
    . /etc/mavenrc
  fi
  if [ -f "$HOME/.mavenrc" ]; then
    . "$HOME/.mavenrc"
  fi
fi

# OS specific support. $var _must_ be set to either true or false.
cygwin=false
darwin=false
mingw=false
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  Darwin*) darwin=true ;;
  MINGW*) mingw=true ;;
esac

if [ -z "$JAVA_HOME" ]; then
  java_executable="$(which java)"
  if [ -z "$java_executable" ]; then
    if [ -x /usr/libexec/java_home ]; then
      JAVA_HOME="$(/usr/libexec/java_home)"
    fi
  fi
  if [ -z "$JAVA_HOME" ]; then
    if [ -d /usr/lib/jvm/java-17-openjdk-amd64 ]; then
      JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    fi
  fi
else
  java_executable="$JAVA_HOME/bin/java"
fi

if [ ! -x "$java_executable" ]; then
  java_executable=java
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME="$(cygpath --unix "$JAVA_HOME")"
fi

# For MinGW, ensure paths are in UNIX format before anything is touched
if $mingw; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME="$(cygpath --unix "$JAVA_HOME")"
fi

# Find the project base dir, i.e. the directory that contains the folder ".mvn".
# Fallback to current working directory if not found.
project_base_dir="$(pwd)"
while [ "$project_base_dir" != "/" ] && [ ! -d "$project_base_dir/.mvn" ]; do
  project_base_dir="$(dirname "$project_base_dir")"
done

# Check for .mvn/wrapper/maven-wrapper.jar
wrapper_jar="$project_base_dir/.mvn/wrapper/maven-wrapper.jar"
if [ ! -f "$wrapper_jar" ]; then
  # Download the wrapper jar if it doesn't exist
  wrapper_url=$(sed -n '/^wrapperUrl=/ s/.*=//p' "$project_base_dir/.mvn/wrapper/maven-wrapper.properties")
  if [ -z "$wrapper_url" ]; then
    wrapper_url="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
  fi
  echo "Downloading Maven Wrapper..."
  if command -v curl >/dev/null 2>&1; then
    curl -sS -L -o "$wrapper_jar" "$wrapper_url"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$wrapper_jar" "$wrapper_url"
  else
    echo "Error: curl or wget not found. Please install one of them to download the wrapper jar." >&2
    exit 1
  fi
fi

# Execute the wrapper
exec "$java_executable" \
  $MAVEN_OPTS \
  -classpath "$wrapper_jar" \
  "-Dmaven.multiModuleProjectDirectory=$project_base_dir" \
  org.apache.maven.wrapper.MavenWrapperMain \
  "$@"

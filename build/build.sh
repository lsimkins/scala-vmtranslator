#!/usr/bin/env bash

# Builds project for coursera submission
sbt +package
cp target/scala-2.11/nand2tetris_2.11-0.1.jar build/project7/VMTranslator.jar
zip -r -j build/project7.zip build/project7
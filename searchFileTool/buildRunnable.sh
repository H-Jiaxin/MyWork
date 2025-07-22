#!/bin/bash
rm -r ./out/*
javac -cp src/lib/ThreadPool.jar -d out  src/com/SearchFileTool/* src/Main.java
cp -r src/lib/ out/
jar -cvfm out/SearchTool.jar META-INF/MANIFEST.MF -C out .

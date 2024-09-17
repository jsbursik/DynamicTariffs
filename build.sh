#!/bin/bash

javac -d ./classes/ ./src/dynamictariffs/DynamicTariffsPlugin.java -sourcepath ./src/ -cp "./lib/json.jar:./lib/log4j-1.2.9.jar:./lib/starfarer.api.jar:./lib/xstream-1.4.10.jar"
jar cvf ./jars/DynamicTariffs.jar ./classes/
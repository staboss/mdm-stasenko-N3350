#!/bin/bash

sms=$(java -jar ./libs/sms.jar "./resources/data.csv" "915783624" "5")
calls=$(java -jar ./libs/calls.jar "./resources/data.csv" "915783624" "0" "1" "10")
internet=$(java -jar ./libs/internet.jar "./resources/traffic.csv" "192.168.250.39" "0.5" "1000")

python main.py "$internet" "$calls" "$sms"

exit 0
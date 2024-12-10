#!/bin/sh

cp battery.sh /Applications/battery.sh
chmod +x /Applications/battery.sh
cp com.user.battery.plist ~/Library/LaunchAgents/com.user.battery.plist
launchctl load ~/Library/LaunchAgents/com.user.battery.plist
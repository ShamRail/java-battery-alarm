#!/bin/sh

launchctl unload ~/Library/LaunchAgents/com.user.battery.plist
rm ~/Library/LaunchAgents/com.user.battery.plist
rm /Applications/battery.sh
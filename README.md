### Java Battery Alarm

Tool for notifying when battery percent less than min or more than max levels.
It will be useful to save laptop battery life. By default, min = 25 and max = 25.

Percent detecting is based on WMI services running in PowerShell as child JVM process.
So program can be run only in Windows.

### Running
1. Install JDK 17 or JRE 17. ```java``` must be available from command line;
2. Make shortcut from launch_bat.vbs;
3. Press ```Win + R``` and type ```shell:startup```;
4. Copy already created shortcut to opened folder.

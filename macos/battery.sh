#!/bin/sh

# Cyclic logic
#while :
#do
#  percent_line=$(system_profiler SPPowerDataType | sed -n '/Charge Information:/,/System Power Settings:/p' | sed '$d' | grep State)
#  chargening=$(system_profiler SPPowerDataType | sed -n '/Charge Information:/,/System Power Settings:/p' | sed '$d' | grep Charging | grep -oE '[^[:space:]]+$')
#  percent=${percent_line: -2}
#  echo $percent $chargening
#  if [[ $chargening = 'No' ]] && ((percent <= 20))
#    then say "Текущий заряд батареи $percent процентов. Подключи зарядник" ; osascript -e 'display notification "Заряд батареи '$percent'" with title "Battery"'
#  elif [[ $chargening = 'Yes' ]] && ((percent >= 80))
#    then say "Текущий заряд батареи $percent процентов. Отключи зарядник" ; osascript -e 'display notification "Заряд батареи '$percent'" with title "Battery"'
#  fi
#  sleep 60
#done

# Only check logic
percent_line=$(system_profiler SPPowerDataType | sed -n '/Charge Information:/,/System Power Settings:/p' | sed '$d' | grep State)
chargening=$(system_profiler SPPowerDataType | sed -n '/Charge Information:/,/System Power Settings:/p' | sed '$d' | grep Charging | grep -oE '[^[:space:]]+$')
percent=${percent_line: -2}
echo $percent $chargening
if [[ $chargening = 'No' ]] && ((percent <= 20))
  then say "Подключите зарядное устройство" ; osascript -e 'display notification "Заряд батареи '$percent'" with title "Battery"'
elif [[ $chargening = 'Yes' ]] && ((percent >= 80))
  then say "Отключите зарядное устройство" ; osascript -e 'display notification "Заряд батареи '$percent'" with title "Battery"'
fi
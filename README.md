"# java" 

Положить файл для чтения в корень проекта.

Первым аргументом при запуске программы указывается имя файла из которого необходимо осуществить чтение(В качестве примера приложен файл sampleInput.txt).

При работе с консолью:
1. Currency - имя валюты (нет ограничений);
2. Amount - количесво валюты в сделке ("+" - добавить, "-" - вычесть);
3. RateToUSD - курс валюты относительно USD (amount / rateToUSD);

input 
```
USD 1000
HKD 100
USD -100
RMB 2000
HKD 200
```

output 
```
USD 900
RMB 2000
HKD 300
```

Если указан курс
```
USD 900
RMB 2000 (USD 314.60)
HKD 300 (USD 38.62)
```
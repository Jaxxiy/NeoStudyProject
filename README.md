# Калькулятор отпускных

REST API микросервис для подсчета отпускных на SpringBoot + Java 11 c одним API: GET "/calculate".

Для локального запуска контейнера:

```
docker build -t vacation-calculator .
docker run -p 8080:8080 vacation-calculator
```

Для тестирования API передаём body:

{
    "avgSalary" : //число,
    "daysCount": //число,
    "vacationStartDate": //можем либо указывать дату либо нет  
}

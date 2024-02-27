# Java hunting-resources

## О сервисе
Это RESTful web-сервис для обработки завяок на различные ресурсы. Реализовано CRUD для заявок, а также возможность проверки этих заявок для одобрения или отклонения

## Stack
Java, Spring Boot, PostgreSQL, JPA(Hibernate), Maven, MockMvc, Junit, Lombok.

## Архитектура
_______________________________________________________________
web-сервис состоит из 2 модулей. Сервис для добавления, редактирования, удаления и получения заявок и сервис для проверки заявок
_______________________________________________________________

## end-points

Для сервиа добавления, редактирования, удаление и получения заявок, есть слеующие end-point:
1) Post запрос для добавления заявок.
   Адрес: "localhost:8080/request".
   Пример body:
   {
    "name": "Viktor", 
    "sureName": "B",
    "patronymic": "Evgenievich",
    "type": "DRAWING_LOTS",
    "issueDate": "2024-02-23", 
    "series": 23,
    "number": 239468,
    "askingResourceDtoList": 
    [
        {
            "area": "Russia", 
            "name": "Wolf",
            "count": 5
        }, 
        {
            "area": "Russia",
            "name": "Wolf",
            "count": 5
        }
    ]
  }

2) Get запрос для полученя списка всех заявок.
   Адрес: "localhost:8080/request"

3) Patch запрос для редактирования заявки по её идентификатору.
   Адрес: "localhost:8080/request/{requestId}"
   Пример body:
   {
    "name": "Biktor", 
    "sureName": "B",
    "patronymic": "Evgenievich",
    "type": "DRAWING_LOTS",
    "issueDate": "2024-02-23", 
    "series": 23,
    "number": 239468,
    "askingResourceDtoList": 
    [
        {
            "area": "Russia", 
            "name": "Fish",
            "count": 5
        }
    ]
  }

4) Delete для удаления заявки
   Адрес: "localhost:8080/request/{requestId}"

Для сервиа одобрения или отклонения заявок есть слеующие end-point:

1) Patch запрос для начала проврки всех непроверенных заявок.
   Адрес: "localhost:8081/check/start"

2) Patch запрос для остановки проверки всех непроверенных заявок.
   Адрес: "localhost:8081/check/start"

## Использование
Чтобы запустить приложение, клонируйте себе проект с помощью команды:

```sh
$ git clone https://github.com/PriestSexist/Java-hunting-resources.git
```

Затем запустите проект через idea и запустите 2 файла. 
1) RequestResourceApplication
2) RequesCheckApplication

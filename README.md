Приложение, имитирующее работу киоска (ТОЧКА, ПУНКТ) по продаже валют.
Открытие рабочего дня:
1)	Каждый день сотрудник, приходя на работу, выполняет операцию «Открытие рабочего дня».
В рамках этой операции, приложение должно вызвать API https://api.privatbank.ua/#p24/exchange, получить текущий курс валют, например для американских долларов (USD) и евро (EUR) по отношению к гривне (UAH), определить курс продажи(КПР) и курс покупки (КПО) киоска на текущий момент времени (дата и время) и сохранить их в БД.
Вызов операции через URL REST сервиса. 
2)	После операции 1, сотрудник может начинать работу по продаже/покупке валюты. 
3)	Процесс обмена валюты осуществляется по сценарию “заявка-подтверждение”
Создание заявки:
Входными данными для заявки будет следующая информация, полученная от клиента:
- валюта продажи;
- валюта покупки;
- сумма продажи;
- ввод контактных данных покупателя, как минимум номер телефона;
Затем сотрудник сохраняет заявку, она добавляется в БД со статусом «Новая».
Вызов операции заявки - через URL REST сервиса. 
Сервис должен вернуть:
-  сумму в валюте покупки с учетом курса валют, которая вычисляется как 
     	сумма продажи * курс валюты продажи к валюте покупке 
-  номер телефона клиента
-  и ОТП пароль (который передается клиенту).

Подтверждение заявки:   
- клиент сообщает свой номер телефона и ОТП пароль, который он получил;
- сотрудник вводит ОТП пароль и программа проверяет соответствует ли этот ОТП пароль тому, который отправил предыдущий сервис и если они одинаковые - операция обмена валюты считается успешной, заявка переводиться в статус «Выполнена»;
- если ОТП пароль ошибочен – заявка переводиться в статус «Отменена».
Вызов операции подтверждения заявки - через URL REST сервиса. 
Сервис должен вернуть ответ о правильности или ошибочности переданного ОТП пароля.

Удаление завки: 
По необходимости, заявку на обмен валюты можно удалить, если она находиться в статусе «Новая» и вы знаете номер телефона клиента.   
Вызов операции удаления - через URL REST сервиса.

Закрытие рабочего дня, отчеты:

4)	По окончанию рабочего дня, продавец должен выполнить операцию «Закрытие рабочего дня». Вызов операции - через URL REST сервиса.
•	После выполнения, сервис должен вернуть отчет о  проделанной работе за день, в который должно быть включено количество сделок обмена валюты, суммы ПОКУПКИ и ПРОДАЖИ по каждому виду валюты за день.
•	Так же нужен отчет по списку операций, когда пользователь задает самостоятельно 
выбранный интервал времени и тип валюты (например, от 01.02.2021 до 16.03.2021 г. по валюте USD). Вызов операции через URL REST сервиса. 

 Тестирование:
            
5)	В проекте обязательно должны присутствовать примеры UNIT тестов на основе Mokito, примеры интеграционных тестов для проверки: 
- взаимодействия между Spring бинами системы;
- работы с БД;
- для проверки внешнего сервиса получения данных Bank.gov.ua;
- для проверки ваших внутренних REST сервисов.

 Документация:
 
6)	Задокументируйте информацию о ваших Rest API с помощью Swagger.


Доп информация:
Используйте in-memory БД, что-то типа Н2 или Derby. Нужно будет написать скрипт, который при старте приложения создаст необходимые таблицы и заполнить их первоначальными данными. 
Приложение должно быть написано с использованием стека технологий Spring (крайне желательно SpringBoot). 
По возможности хорошо было бы предусмотреть механизм развертывания в docker. 


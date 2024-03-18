# Автономный менеджер задач ComoDiary

## Инструкция по развертыванию веб-приложения

Для развёртывания приложения на хостинге, необходимо скопировать содержимое репозитория на выбранный хостинг.

После чего изменить настройки в файлах Dockerfile, docker-compose.yml и application.yaml. 
Первые два файла находятся в корневой директории проекта, а файл настроек application.yaml 
в директории ComoDiary/tree/main/src/main/resources.

В файле Dockerfile можно изменить логин и пароль пользователя приложения.
В application.yaml указаны пользователь и пароль базы данных, которые должны совпадать 
с такими же настройками файла docker-compose.yml.
Также в docker-compose.yml можно изменить требуемые порты.

Если ничего не менять, то будут применены настройки по умолчанию:
- логин и пароль пользователя: user, password
- имя, пользователь и пароль базы данных: postgres, user, password
- приложение запустится на порту 8080 (http://localhost:8080)


## Руководство пользователя

### Представление “месяц”
![]()
При первом запуске приложения выводится представление по-умолчанию - “месяц”, с демо-задачей, назначенной на текущий день. Текущий день выделен зелёным цветом.
Кнопки <<< и >>> позволяют переходить к просмотру соседних месяцев.
Просроченные задачи будут отображаться красным цветом, а выполненные - серым перечёркнутым шрифтом.
Нажатие на кнопку с номером дня - переход в представление “день”.
Нажатие на заголовок задачи - переход в представление “задача”.

### Меню
![]()
При выборе даты будет выведено представление “месяц”, соответствующее указанной дате.
Кнопка с пиктограммой + ведёт на страницу создания новой записи.
Выпадающее меню “Вид” позволяет переключать представления.
При наличии просроченных задач, в меню будет отображена красная круглая кнопка с количеством таких задач. При нажатии - будет отображён список этих задач.
Строка поиска позволяет находить все задачи, в заголовках или описаниях которых встречается поисковый запрос. Задачи отображаются в представлении “список”.
При пустой строке поиска, нажатие по кнопке”Поиск” выводит все имеющиеся задачи в представлении “список”.

### Представление “список”
![]()
В списке задач все задачи отображаются в порядке возрастания дат, нумерованным списком.
Напротив каждой задачи есть кнопка изменения статуса.
Нажатие на заголовке задачи ведёт в представление “задача”.

### Представление “день”
![]()
Это представление в целом похоже на “список”, с тем отличием, что составлено в виде таблицы с одним столбцом. В шапке таблицы указывается дата и день недели. Кнопка + позволяет создавать задачи для этого дня - дата будет уже проставлена.

### Представление “3 дня”
![]()
По сути, это представление - это “день”, отображённый трижды. Стартовой датой считается дата левого столбца, и при выборе даты подразумевается именно она.

### Представление “задача”
![]()
Только в этом представлении видно описание задачи. Здесь можно изменить любые данные задачи, её статус, и удалить задачу.

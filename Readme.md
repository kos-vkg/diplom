# процедура запуска тестов
   **Для запуска тестов необходимы следующие инструменты**
1. JDK11
1. IntellijIDEA
1. Docker
1. Node.js

  **Последовательность запуска тестов**
1. Открыть проект в IDEA.
2.  В окне терминала запустить контейнер MySQL .
   
    ' docker-compose up -d '
3. В окне терминала IDEA запустить эмулятор сервера:
   
   ' npm start app.js --port=9999 '
4. В новом окне терминала запустить файл SUT:
   
   ' java -jar aqa-shop.jar --spring.credit-gate.url=http://localhost:9999/credit --spring.payment-gate.url=http://localhost:99
   99/payment --spring.datasource.url=jdbc:mysql://localhost:3306/app --spring.datasource.username=app --spring.datasource.password=pass '
 
  Далее возможно как автоматическое, так и ручное тестирование в браузере (localhost:8080).
  



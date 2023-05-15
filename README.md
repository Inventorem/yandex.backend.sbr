# Вступительное задание Java
# Структура репозитория:
## controllers: 
### два RestController' а для  заказов и курьеров:
#### CourierController
#### OrderController 
Они оперируют с DTO, которые находятся в папке model

### При получении запроса, controller вызывает метод у соответствующего 
## Service
##### их по аналогии также два:
#### OrderService
#### CourierService
### К сервисам прикреплены репозитории, которые хранятся в /repos:
#### CourierRepo
#### OrderRepo
репозитории оперируют сущностями Courier и Order, которые находятся в
директории /domain

### RateLimiter реализован через Resilience4j


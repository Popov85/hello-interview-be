package com.hello.interview.tasks.streams.orders;

import java.util.List;
import java.util.Set;

interface OrderService {

    // Рассчитать полную стоимость заказа
    Double calculateOrderTotal(Order order);

    // Получить список наименований товаров из заказов
    Set<String> getUniqueProductNames(List<Order> orders);

    // Display names of N popular goods in order of reducing popularity
    // Popular item is the one that is being sold most of all.
    List<String> findMostPopularProducts(List<Order> orders, int topN);

}

package com.hello.interview.tasks.streams.orders;

import java.util.*;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    @Override
    public Double calculateOrderTotal(Order order) {
        return order.products.stream()
                .map(p -> p.getPrice() * p.getQuantity())
                .reduce(0.0d, (a, b) -> a + b);
    }

    /*@Override
    public BigDecimal calculateOrderTotal(Order order) {
        return order.products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, (a, b)->a.add(b))); // BigDecimal::add
    }*/

    @Override
    public Set<String> getUniqueProductNames(List<Order> orders) {
        return orders.stream()
                .flatMap(o -> o.getProducts().stream())
                .map(p -> p.getName())
                .collect(Collectors.toSet());
    }

    @Override
    public List<String> findMostPopularProducts(List<Order> orders, int topN) {
        return orders.stream()
                .flatMap(o -> o.getProducts().stream())
                .filter(p -> p.getName() != null)
                .collect(Collectors.groupingBy(p->p.getName())) // Map<String, List<Product>>
                .entrySet()
                .stream()
                .map(e -> Map.entry(
                        e.getKey(),
                        e.getValue().stream()
                                .map(p->p.getQuantity()!=null ? p.getQuantity() : 0)
                                .reduce(0, (a, b) -> a + b)
                )) // Map<String, Integer>
                .sorted((e1,e2)->e2.getValue().compareTo(e1.getValue()))//Reverse sorting by quantity
                .limit(topN)// Reducing to N elements
                .map(e->e.getKey())//Returning only names
                .collect(Collectors.toList());
    }

    /*@Override
    public List<String> findMostPopularProducts(List<Order> orders, int topN) {
        Map<String, Integer> lookUp = new HashMap<>();
        orders.stream().flatMap(o -> o.getProducts().stream()).forEach(p -> {
            String name = p.getName();
            Integer quantity = p.getQuantity();
            lookUp.compute(name, (k, v) -> v == null ? quantity : v + quantity);
        });
        return lookUp.entrySet().stream()
                .sorted((e1,e2)->e2.getValue().compareTo(e1.getValue()))
                .map((e)->e.getKey())
                .limit(topN)
                .collect(Collectors.toList());
    }*/

    public static void main(String[] args) {
        OrderService orderService = new OrderServiceImpl();

        Order o1 = new Order();
        Order o2 = new Order();
        Order o3 = new Order();

        Product p1 = new Product();
        p1.setName("Cheese");
        p1.setQuantity(1);

        Product p2 = new Product();
        p1.setName("Meat");
        p1.setQuantity(2);

        Product p3 = new Product();
        p1.setName("Milk");
        p1.setQuantity(3);

        o1.setProducts(Arrays.asList(p1, p2));
        o2.setProducts(Arrays.asList(p1, p2, p3));
        o3.setProducts(Arrays.asList(p2, p3));

        List<String> mostPopularProducts = orderService.findMostPopularProducts(List.of(o1, o2, o3), 2);

        System.out.println("Result = "+ mostPopularProducts);
    }
}

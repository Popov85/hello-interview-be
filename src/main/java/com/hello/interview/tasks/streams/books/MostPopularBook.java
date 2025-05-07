package com.hello.interview.tasks.streams.books;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MostPopularBook {

    private static class Book {

        private String title;

        public Book(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return Objects.equals(title, book.title);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(title);
        }

        @Override
        public String toString() {
            return "Book{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    private static class Person {
        private String name;

        private List<Book> books;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Book> getBooks() {
            return books;
        }

        public void setBooks(List<Book> books) {
            this.books = books;
        }
    }

    public static void main(String[] args) {
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();

        p1.setBooks(List.of(new Book("Java"), new Book("Java"), new Book("Java")));
        p2.setBooks(List.of(new Book("C#"), new Book("C#"), new Book("C#1")));
        p3.setBooks(List.of(new Book("Go"), new Book("Go"), new Book("Go1")));

        System.out.println(new MostPopularBook().getMostPopularBook(List.of(p1, p2, p3)));
    }

    public Book getMostPopularBook(List<Person> persons) {
        return persons.stream()
                .flatMap(person -> person.getBooks().stream()) // Flatten all books into a single stream
                .collect(Collectors.groupingBy(b->b, Collectors.counting())) // Count occurrences
                .entrySet()
                .stream()
                .reduce((e1, e2) -> e1.getValue() > e2.getValue() ? e1 : e2) // Step 4: Find max
                .map(e->e.getKey()) // Step 5: Extract the book
                .orElse(null);
    }
}

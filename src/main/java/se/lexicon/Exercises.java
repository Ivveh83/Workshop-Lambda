package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	TODO: Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> filter = person -> person.getFirstName().equals("Erik");

        for (Person person : storage.findMany(filter))
            System.out.println(person);

        System.out.println("----------------------");
    }

    /*
        2.	TODO: Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        //Write your code here
        System.out.println(message);

        List<Person> females = storage.findMany(person -> person.getGender() == Gender.FEMALE);

        for (Person person : females)
            System.out.println(person);

        System.out.println("----------------------");
    }

    /*
        3.	TODO: Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> afterBirths = storage.findMany(
                person -> person.getBirthDate().isAfter(LocalDate.of(1999, 12, 31)));

        for (Person person : afterBirths)
            System.out.println(person);

        System.out.println("----------------------");
    }

    /*
        4.	TODO: Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        //Write your code here
        System.out.println(storage.findOne(person1 -> person1.getId() == 123));

        System.out.println("----------------------");
    }

    /*
        5.	TODO: Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        //Write your code here
        String result = storage.findOneAndMapToString(
                person1 -> person1.getId() == 456,
                person1 -> "Name: " +person1.getFirstName() + " " +person1.getLastName() +
                 " born " + person1.getBirthDate());
        System.out.println(result);




        System.out.println("----------------------");
    }

    /*
        6.	TODO: Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        //Write your code here
        List<String> personsAsString = storage.findManyAndMapEachToString(
                person -> person.getGender() == Gender.MALE && person.getFirstName().startsWith("E"),
                person -> person.toString()
                );
        for (String string : personsAsString)
            System.out.println(string);

        System.out.println("----------------------");
    }

    /*
        7.	TODO: Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> filterByAgeBelow10 = person ->
                person.getBirthDate().isAfter(LocalDate.of(2015, 04, 24));

        int thisYear = LocalDate.now().getYear();
        Function<Person, String> convertPersonToString = person ->
                person.getFirstName() + " " + person.getLastName() +
                " " + (thisYear - person.getBirthDate().getYear() < 10 ?
                thisYear - person.getBirthDate().getYear() : 9) + " years";

        for (String string : storage.findManyAndMapEachToString(filterByAgeBelow10, convertPersonToString))
            System.out.println(string);

        System.out.println("----------------------");
    }

    /*
        8.	TODO: Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(person -> person.getFirstName().equals("Ulf"),
                          person -> System.out.println(person));

        System.out.println("----------------------");
    }

    /*
        9.	TODO: Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(person -> person.getLastName().toLowerCase().contains(person.getFirstName().toLowerCase()),
                          person -> System.out.println(person));

        System.out.println("----------------------");
    }

    /*
        10.	TODO: Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> filter = person -> person.getFirstName().toLowerCase()
                .equals(new StringBuilder(person.getFirstName().toLowerCase()).reverse().toString());
        storage.findAndDo(filter,
                person -> System.out.println(person.getFirstName() + " " + person.getLastName()));

        System.out.println("----------------------");
    }

    /*
        11.	TODO: Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> sortedList = new ArrayList<>();
        Predicate<Person> filter = person -> person.getFirstName().startsWith("A");
        Comparator<Person> comparator = Comparator.comparing(person -> person.getBirthDate());

        for (Person person :storage.findAndSort(filter, comparator))
            System.out.println(person);


        System.out.println("----------------------");
    }

    /*
        12.	TODO: Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> filter = person -> person.getBirthDate().isBefore(LocalDate.of(1950, 01, 01));
        Comparator<Person> comparator = Comparator.comparing(person -> person.getBirthDate(), Comparator.reverseOrder());
        for (Person person : storage.findAndSort(filter, comparator))
            System.out.println(person);


        System.out.println("----------------------");
    }

    /*
        13.	TODO: Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        //Write your code here
        Comparator<Person> comparator = Comparator.comparing(Person::getLastName)
                .thenComparing(Person::getFirstName)
                .thenComparing(Person::getBirthDate);
        for (Person person : storage.findAndSort(comparator))
            System.out.println(person);

        System.out.println("----------------------");
    }

}
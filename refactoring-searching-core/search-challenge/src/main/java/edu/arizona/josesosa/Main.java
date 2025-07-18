package edu.arizona.josesosa;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        PersonSearch personSearch = new PersonSearch();
        personSearch.setOrderBy("FIRSTNAME");
        personSearch.getPerson().setLastName("%Bob%");
        personSearch.search();
        System.out.println(System.lineSeparator());

        CarSearch carSearch = new CarSearch();
        carSearch.setOrderBy("YEAR");
        carSearch.getCar().setBrand("%Ford%");
        carSearch.search();
    }
}
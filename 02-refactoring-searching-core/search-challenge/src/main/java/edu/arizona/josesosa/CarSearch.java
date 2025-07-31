package edu.arizona.josesosa;

import java.util.Arrays;
import java.util.List;


public class CarSearch extends GenericSearch<Car> {


    // Restriction for Search
    private static final String[] RESTRICTIONS = {
            "lower(" + CarSortCriteria.BRAND.getProperty() + ") like concat(lower(#{carSearch.car.brand}),'%')",
            "lower(" + CarSortCriteria.YEAR.getProperty() + ") like concat(lower(#{carSearch.car.year}),'%')",
    };

    // instance of a person for search criteria data
    private Car car = new Car();

    private enum CarSortCriteria implements SortCriteria{
        BRAND("car.brand"),
        YEAR("car.year");

        private final String property;

        CarSortCriteria(String prop) {
            this.property = prop;
        }

        @Override
        public String getProperty() {
            return property;
        }
    }

    public CarSearch() {
        super(Car.class);
    }

    @Override
    protected SortCriteria[] getSortCriteria() {
        return CarSortCriteria.values();
    }

    @Override
    // Restriction for Search
    protected List<String> getCriteria() {
        return Arrays.asList(RESTRICTIONS);
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }


}

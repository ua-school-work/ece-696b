package edu.arizona.josesosa;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericSearch<T> implements ISearch {

    private String orderBy = null;

    protected Class<T> clazz;

    public GenericSearch(Class<T> clazz) {
        this.clazz = clazz;
    }

    // naive search
    public List<T> search() {
        List<String> criteriaArray = getCriteria();
        if (criteriaArray != null) {
            System.out.println("---Translated to---");
            System.out.println(" select * from "
                    + clazz.getSimpleName() + " where ");


            for (String criteria : criteriaArray) {
                if (criteriaArray.getLast().equals(criteria)) {
                    System.out.println(criteria + " ");
                } else {
                    System.out.println(criteria + ", ");
                }
            }
            System.out.println(" order by " + orderBy);
            System.out.println("---comments---");
            System.out
                    .println("--Our framework would supply the real values for #{params}");

            // here would be database search
            return new ArrayList<>();
        } else {
            return null;
        }
    }

    // I need this function for criteria
    protected abstract List<String> getCriteria();

    // I need this function for translation or the order
    protected  BiMap<String, String> getAllowedOrder() {
        BiMap<String, String> allowOrder = new BiMap<>();
        for (SortCriteria criteria : getSortCriteria()) {
            allowOrder.put(criteria.name(), criteria.getProperty());
        }
        return allowOrder;
    }

    protected abstract SortCriteria[] getSortCriteria();

    // this data goes out of the system, so we do translation
    public String getOrderBy() {
        return getAllowedOrder().getKey(orderBy);
    }

    // this data goes back to the system so we do translation
    public void setOrderBy(String orderBy) {
        this.orderBy = getAllowedOrder().getValue(orderBy);
    }

}

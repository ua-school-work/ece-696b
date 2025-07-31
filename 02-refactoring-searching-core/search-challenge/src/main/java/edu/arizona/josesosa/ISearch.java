package edu.arizona.josesosa;

import java.util.List;

public interface ISearch {

    // naive search
    List<?> search();

    // this data goes out of the system, so we do translation
    String getOrderBy();

    // this data goes back to the system so we do translation
    void setOrderBy(String orderBy);

}
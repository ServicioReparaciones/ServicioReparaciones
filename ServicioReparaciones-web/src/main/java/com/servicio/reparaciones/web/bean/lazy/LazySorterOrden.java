/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean.lazy;

import com.servicio.reparaciones.modelo.nosql.Orden;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author luis
 */
public class LazySorterOrden implements Comparator<Orden> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorterOrden(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(Orden ord1, Orden ord2) {
        try {
            Object value1 = Orden.class.getField(this.sortField).get(ord1);
            Object value2 = Orden.class.getField(this.sortField).get(ord2);
            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

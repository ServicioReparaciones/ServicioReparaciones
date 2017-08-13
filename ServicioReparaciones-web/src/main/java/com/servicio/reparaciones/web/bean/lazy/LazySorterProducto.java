/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean.lazy;

import com.servicio.reparaciones.modelo.nosql.Producto;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author luis
 */
public class LazySorterProducto implements Comparator<Producto> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorterProducto(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(Producto ord1, Producto ord2) {
        try {
            Object value1 = Producto.class.getField(this.sortField).get(ord1);
            Object value2 = Producto.class.getField(this.sortField).get(ord2);
            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean.lazy;

import com.servicio.reparaciones.modelo.nosql.Cliente;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author luis
 */
public class LazySorterCliente implements Comparator<Cliente> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorterCliente(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(Cliente ord1, Cliente ord2) {
        try {
            Object value1 = Cliente.class.getField(this.sortField).get(ord1);
            Object value2 = Cliente.class.getField(this.sortField).get(ord2);
            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

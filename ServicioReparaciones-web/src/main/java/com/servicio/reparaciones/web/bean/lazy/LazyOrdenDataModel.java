/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean.lazy;

import com.servicio.reparaciones.modelo.nosql.Orden;
import com.servicio.reparaciones.servicio.OrdenServicio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author luis
 */
public class LazyOrdenDataModel extends LazyDataModel<Orden> {
    
    private static final long serialVersionUID = 4757257950045668804L;
    
    @Inject
    private OrdenServicio ordenService;
    
    private List<Orden> datasource;
    private Boolean parameter;
    
    public LazyOrdenDataModel() {
        this.datasource = new ArrayList<>();
        this.ordenService = new OrdenServicio();
        this.parameter = Boolean.FALSE;
    }
    
    public LazyOrdenDataModel(List<Orden> datasource) {
        this.datasource = datasource;
        this.parameter = Boolean.TRUE;
    }
    
    @Override
    public Orden getRowData(String rowKey) {
        for (Orden orden : datasource) {
            if (orden.getId().toString().equals(rowKey)) {
                return orden;
            }
        }
        return null;
    }
    
    @Override
    public Object getRowKey(Orden orden) {
        return orden.getId();
    }
    
    @Override
    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Orden> data = new ArrayList<Orden>();
        
        if (!parameter) {
            this.datasource = this.ordenService.lazy(first, first + pageSize, 1);
        }
        
        for (Orden ord : datasource) {
            boolean match = true;
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(ord.getClass().getField(filterProperty).get(ord));
                        
                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }
            if (match) {
                data.add(ord);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorterOrden(sortField, sortOrder));
        }

        //rowCount
        int dataSize = 0;
        if (parameter) {
            dataSize = data.size();
        } else {
            dataSize = this.ordenService.count(1);
        }
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                if (parameter) {
                    return data.subList(first, first + pageSize);
                } else {
                    return data;
                }
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }
    
}

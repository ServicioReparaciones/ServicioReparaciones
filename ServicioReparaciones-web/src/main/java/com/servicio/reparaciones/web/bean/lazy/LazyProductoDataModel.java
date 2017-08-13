/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean.lazy;

import com.servicio.reparaciones.modelo.nosql.Producto;
import com.servicio.reparaciones.servicio.ProductoServicio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author luis
 */
public class LazyProductoDataModel extends LazyDataModel<Producto> {

    private static final long serialVersionUID = 5427088892475957199L;

    @Inject
    private ProductoServicio productoService;

    private List<Producto> datasource;
    private Boolean parameter;
    private Boolean filter;
    private Integer flag;

    public LazyProductoDataModel(Integer flag) {
        this.datasource = new ArrayList<>();
        this.productoService = new ProductoServicio();
        this.parameter = Boolean.FALSE;
        this.filter = Boolean.FALSE;
        this.flag = flag;
    }

    public LazyProductoDataModel(List<Producto> datasource) {
        this.datasource = datasource;
        this.parameter = Boolean.TRUE;
        this.filter = Boolean.FALSE;
    }

    @Override
    public Producto getRowData(String rowKey) {
        for (Producto producto : datasource) {
            if (producto.getId().toString().equals(rowKey)) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Producto producto) {
        return producto.getId();
    }

    @Override
    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Producto> data = new ArrayList<Producto>();

        if (!parameter) {
            this.datasource = this.productoService.lazy(first, first + pageSize, this.flag);
        }

        for (Producto prod : datasource) {
            boolean match = true;

            if (filters != null && !filters.isEmpty()) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        List<Producto> objectsfilter = this.productoService.filterd(filterProperty, filterValue.toString(), flag);
                        Boolean equal = objectsfilter.isEmpty();
                        if (!equal) {
                            match = false;
                            this.filter = Boolean.TRUE;
                            data.clear();
                            data.addAll(objectsfilter);
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            } else {
                this.filter = Boolean.FALSE;
            }

            if (match) {
                data.add(prod);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorterProducto(sortField, sortOrder));
        }

        //rowCount
        int dataSize = 0;
        if (parameter) {
            dataSize = data.size();
        } else if (filter) {
            dataSize = data.size();
            this.datasource.addAll(data);
        } else {
            dataSize = this.productoService.count(this.flag);
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

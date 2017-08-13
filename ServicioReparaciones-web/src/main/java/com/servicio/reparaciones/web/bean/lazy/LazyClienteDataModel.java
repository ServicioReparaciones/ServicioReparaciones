/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean.lazy;

import com.servicio.reparaciones.modelo.nosql.Cliente;
import com.servicio.reparaciones.servicio.ClienteServicio;
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
public class LazyClienteDataModel extends LazyDataModel<Cliente> {

    @Inject
    private ClienteServicio clienteService;

    private List<Cliente> datasource;
    private Boolean parameter;
    private Boolean filter;
    private Integer flag;

    public LazyClienteDataModel(Integer flag) {
        this.datasource = new ArrayList<>();
        this.clienteService = new ClienteServicio();
        this.parameter = Boolean.FALSE;
        this.filter = Boolean.FALSE;
        this.flag = flag;
    }

    public LazyClienteDataModel(List<Cliente> datasource) {
        this.datasource = datasource;
        this.parameter = Boolean.TRUE;
        this.filter = Boolean.FALSE;
    }

    @Override
    public Cliente getRowData(String rowKey) {
        for (Cliente cliente : datasource) {
            if (cliente.getId().toString().equals(rowKey)) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Cliente cliente) {
        return cliente.getId();
    }

    @Override
    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Cliente> data = new ArrayList<Cliente>();

        if (!parameter) {
            this.datasource = this.clienteService.lazy(first, first + pageSize, this.flag);
        }

        for (Cliente cli : datasource) {
            boolean match = true;

            if (filters != null && !filters.isEmpty()) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        List<Cliente> objectsfilter = this.clienteService.filterd(filterProperty, filterValue.toString(), flag);
                        Boolean equal = objectsfilter.isEmpty();
                        if (!equal) {
                            match = false;
                            this.filter = Boolean.TRUE;
                            data.clear();
                            data.addAll(objectsfilter);
                            this.datasource.addAll(objectsfilter);
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
                data.add(cli);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorterCliente(sortField, sortOrder));
        }

        //rowCount
        int dataSize = 0;
        if (parameter) {
            dataSize = data.size();
        } else if (filter) {
            dataSize = data.size();
        } else {
            dataSize = this.clienteService.count(this.flag);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.util.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author luis
 */
@Named(value = "registerBean")
@ViewScoped
public class RegisterBean implements Serializable {

    private static final long serialVersionUID = 4423347562517509762L;

    private Usuario nuevo;
    private String comfirmPassword;

    @Inject
    private UsuarioServicio usuarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Usuario();
        this.comfirmPassword = "";
    }

    public void add(ActionEvent evt) {
        if (this.nuevo.getPassword().equals(this.comfirmPassword)) {
            Usuario username = this.usuarioService.findByUsername(this.nuevo);
            if (username.getId() == null) {
                Usuario useremail = this.usuarioService.findByEmail(this.nuevo);
                if (useremail.getId() == null) {
                    Boolean exito = this.usuarioService.insert(this.nuevo);
                    if (exito) {
                        FacesUtil.addMessageInfo("El usuario a sido registrado con exito.");
                        this.init();
                        RequestContext.getCurrentInstance().execute("PF('dlgRegister').show();");
                    } else {
                        FacesUtil.addMessageError(null, "El usuario no sea registrado.");
                        this.init();
                    }
                } else {
                    FacesUtil.addMessageInfo("Ya existe un usuario registrado con ese e-mail.");
                }
            } else {
                FacesUtil.addMessageInfo("Ya existe un usuario registrado con ese username.");
            }
        } else {
            FacesUtil.addMessageInfo("El password no coincide.");
        }
    }

    public Usuario getNuevo() {
        return nuevo;
    }

    public void setNuevo(Usuario nuevo) {
        this.nuevo = nuevo;
    }

    public String getComfirmPassword() {
        return comfirmPassword;
    }

    public void setComfirmPassword(String comfirmPassword) {
        this.comfirmPassword = comfirmPassword;
    }

}

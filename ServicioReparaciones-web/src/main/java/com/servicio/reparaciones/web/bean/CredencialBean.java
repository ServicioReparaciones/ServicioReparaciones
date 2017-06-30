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
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author luis
 */
@Named(value = "credencialBean")
@SessionScoped
public class CredencialBean implements Serializable {

    private static final long serialVersionUID = -3799042563216409371L;

    private Usuario userSession = new Usuario();
    private String confirmationPassword;
    private String password;

    @Inject
    private UsuarioServicio usuarioService;

    private void init() {
        this.userSession = new Usuario();
    }

    public void startSession(Usuario usuario) {
        this.userSession = usuario;
        this.password = this.userSession.getPassword();
        this.loadDataSession(this.userSession);
    }

    private void loadDataSession(Usuario usuario) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("codigo", usuario.getCodigo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("username", usuario.getUsername());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("admin", usuario.getAdmin());
    }
    
    public void modifyInfo(ActionEvent evt) {
        this.userSession.setPassword(this.password);
        Boolean exito = this.usuarioService.update(this.userSession);
        if (exito) {
            FacesUtil.addMessageInfo("Se actualizo exitosamente.");
        } else {
            FacesUtil.addMessageError(null, "No actualizo.");
        }
    }
    
    public void changePassword(ActionEvent evt) {
        if (this.userSession.getPassword().equals(this.confirmationPassword)) {
            Boolean exito = this.usuarioService.updatePassword(this.userSession);
            if (exito) {
                FacesUtil.addMessageInfo("Se modifico el password con exito.");
            } else {
                FacesUtil.addMessageError(null, "No se modifico el password con exito.");
            }
        } else {
            FacesUtil.addMessageInfo("El password no coincide.");
        }
    }

    public Usuario getUserSession() {
        return userSession;
    }

    public void setUserSession(Usuario userSession) {
        this.userSession = userSession;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public void logout(ActionEvent event) {
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/index.xhtml";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.removeAttribute(this.userSession.getUsername());
        session.invalidate();
        this.init();
        context.addCallbackParam("loggerOut", true);
        context.addCallbackParam("ruta", url);
    }

}

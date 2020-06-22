/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import services.AdminFacade;
import entities.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zbakh
 */
@Named(value = "adminController")
@SessionScoped
public class AdminController implements Serializable {
    @EJB
    private AdminFacade adminFacade;

    private Admin admin = new Admin();
    /**
     * Creates a new instance of AdminController
     */
    
    public AdminController() {
    }
    
    public List<Admin> findAll()
    {
        return this.adminFacade.findAll();
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    public String login(){
        Admin authAdmin = adminFacade.authAdmin(admin);
        if(authAdmin != null){
            admin = authAdmin;
            return "/admin/index.xhtml";
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "incorrect username and password",
                            "Please enter correct username and password"));
            return "/admin/index.xhtml";
        }
    }
}

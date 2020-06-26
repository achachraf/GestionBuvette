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
    
    private String ancien;
    private String nouveau;
    
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

    public String getAncien() {
        return ancien;
    }

    public void setAncien(String ancien) {
        this.ancien = ancien;
    }

    public String getNouveau() {
        return nouveau;
    }

    public void setNouveau(String nouveau) {
        this.nouveau = nouveau;
    }
    
    
    
    public String login(String st){
        Admin authAdmin = adminFacade.authAdmin(admin);
        if(authAdmin != null){
            admin = authAdmin;
            return st;
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "incorrect username and password",
                            "Please enter correct username and password"));
            return st;
        }
    }
    
    public String changePassword()
    {
        if(admin.getPassword().equals(ancien))
        {
            admin.setPassword(this.nouveau);
            this.adminFacade.edit(admin);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Mot de passe changed",
                            ""));
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "incorrect password",
                            "Please enter correct password"));
        }
        
        return "/admin/profile.xhtml";
    }
    
    public String logout()
    {
        admin = new Admin();
        return "/admin/index.xhtml";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.emptyType;
import entities.Plat;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import services.PlatFacade;

/**
 *
 * @author achra
 */
@Named(value = "platController")
@SessionScoped
public class PlatController implements Serializable {

    /**
     * Creates a new instance of PlatController
     */
    
    @EJB
    PlatFacade platFacade;
    
    public PlatController() {
    }
    
    public Plat getRandomPlat(){
        Plat plat = new Plat();
        plat.setNomPlat("Btata");
        plat.setPrix((float)50.0);
        return plat;
    }
    
    public List<Plat> getPlats(){
        List<Plat> plats = platFacade.findAll();
        return plats;
    }
    
    public List<Plat> getBestPlats(){
        try {
            List<Plat> plats = platFacade.getPlatsLimit(6);
            return plats;
        } catch (Exception e) {
            System.out.println("error in db : "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Error 500","A server error has occured"));
            return null;
        }
    }
    
}

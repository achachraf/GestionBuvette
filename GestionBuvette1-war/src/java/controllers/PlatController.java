/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Utils.FileUpload;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.emptyType;
import entities.Commande;
import entities.Plat;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
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
    
    private Plat plat = new Plat();
    private Plat selectedPlat;
    private boolean redirection = false;
    private Part uploadedFile;
    FileUpload fileUpload = new FileUpload();
    
    public PlatController() {
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Plat getSelectedPlat() {
        return selectedPlat;
    }

    public void setSelectedPlat(Plat selectedPlat) {
        this.selectedPlat = selectedPlat;
    }

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public boolean isRedirection() {
        return redirection;
    }

    public void setRedirection(boolean redirection) {
        this.redirection = redirection;
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
    
//    public String setSelectedPlat(Plat plat){
//        this.plat = plat;
//        return "plat.xhtml";
//    }
    
    public String logPlat(Plat plat){
        System.out.println("okkk");
        System.out.println(plat);
        //return "plat.xhtml?id="+plat.getIdPlat().toString();
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                           "Plat ajouté au panier",null));
        return "index.xhtml";
    }
    
    public String indexRedirect(){
        redirection = true;
        return "index.xhtml?faces-redirect=true&includeViewParams=true";
    }
    
    public Plat getPlatById(int id){
        try {
            Plat plat = platFacade.find(id);
            return plat;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Error 500","A server error has occured"));
            return null;
        }
    }
    
    public List<Plat> getPlatsByCategory(int idCategory){
        List<Plat> plats = platFacade.getPlatsByCategory(idCategory);
        if(plats.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                           "Cette category est vide","null"));
        }
        return plats;
    }
    
    public List<Commande> getTopPlats(){
        try {
            List<Commande> plats = platFacade.getTopPlats();
            return plats;
        } catch (Exception e) {
            System.out.println("error in db : "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Error 500","A server error has occured"));
            return null;
        }
    }
    
    public List<Plat> getAllPlats(){
        try {
            List<Plat> plats = platFacade.getAllPlats();
            return plats;
        } catch (Exception e) {
            System.out.println("error in db : "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Error 500","A server error has occured"));
            return null;
        }
    }
    
    public String delete(Plat a){
        this.platFacade.remove(a);
        return "/admin/plats.xhtml";
    }
    
    public String update()
    {
        if(uploadedFile != null){
            selectedPlat.setImage(fileUpload.saveFile(uploadedFile));
        }
        this.platFacade.edit(this.selectedPlat);
        return "/admin/plats.xhtml";
    }
}

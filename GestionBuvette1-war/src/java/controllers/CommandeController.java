/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Commande;
import entities.Consomation;
import entities.Plat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import services.CommandeFacade;
import services.ConsomationFacade;

/**
 *
 * @author achra
 */
@Named(value = "commandeController")
@SessionScoped
public class CommandeController implements Serializable{

    /**
     * Creates a new instance of CommandeController
     */
    @EJB
    CommandeFacade commandeFacade;

    private List<Commande> commandes = new ArrayList<>();

    private Commande commande = new Commande();

    private static int counter = 0;
    
    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public CommandeController() {
    }

    public String addToCommandes(Plat plat) {
        Commande cmd = new Commande();
        cmd.setIdPlat(plat);
        cmd.setQuantite(commande.getQuantite());
        cmd.setIdComd(counter++);
        commandes.add(cmd);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Plat ajouté au panier", null));
        System.out.println(commandes);
        System.out.println("there is : "+commandes.size()+" commande");
        commande = new Commande();
        return "index.xhtml";
    }
    
    public String removeFromCommandes(Commande commande){
        System.out.println("before delete :");
        System.out.println(commandes);
        if(commandes.remove(commande)){
            System.out.println("commande "+commande.getIdPlat().getNomPlat()+" removed");
            System.out.println("after delete :");
            System.out.println(commandes);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Plat "+commande.getIdPlat().getNomPlat()+" retiré de votre panier", null));
       
        }
        else{
            System.out.println("removing comd failed");
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Operation a echoué", null));
            
        }
        return "cart.xhtml";
    }
    
    public float getTotal(float prix,String qte){
        return prix*Integer.parseInt(qte);
    }
    
    public String clearCart(){
        commandes.clear();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Vous avez vidé votre panier ", null));
        return "cart.xhtml";
    }
    


}

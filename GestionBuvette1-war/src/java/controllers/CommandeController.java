/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Commande;
import entities.Plat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import services.CommandeFacade;

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
        commandes.add(cmd);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Plat ajouté au panier", null));
        System.out.println(commandes);
        System.out.println("there is : "+commandes.size()+" commande");
        commande = new Commande();
        return "index.xhtml";
    }
    
    public void removeFromCommandes(Commande commande){
        commandes.remove(commande);
    }
    
    public float getTotal(float prix,String qte){
        return prix*Integer.parseInt(qte);
    }

}

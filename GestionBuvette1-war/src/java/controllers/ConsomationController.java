/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static entities.Category_.dateCreation;
import entities.Client;
import entities.Commande;
import entities.Consomation;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import services.ConsomationFacade;

/**
 *
 * @author achra
 */
@Named(value = "consomationController")
@SessionScoped
public class ConsomationController implements Serializable {

    /**
     * Creates a new instance of ConsomationController
     */
    @EJB
    ConsomationFacade consomationFacade;

    Consomation consomation = new Consomation();

    public ConsomationController() {
    }

    public ConsomationFacade getConsomationFacade() {
        return consomationFacade;
    }

    public void setConsomationFacade(ConsomationFacade consomationFacade) {
        this.consomationFacade = consomationFacade;
    }

    public Consomation getConsomation() {
        return consomation;
    }

    public void setConsomation(Consomation consomation) {
        this.consomation = consomation;
    }

    public List<Consomation> getNonPaidClientConsomations(Integer idClient) {
        try {
            List<Consomation> consomations = consomationFacade.findNonPaidConsomationsByClientId(idClient);
            return consomations;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Server Error",
                            "Server Error"));
            return null;
        }
    }

    public List<Consomation> getLastClientConsomations(Integer idClient, int limit) {
        try {
            List<Consomation> consomations = consomationFacade.findLimitedConsomationsByClientId(idClient, limit);
            return consomations;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Server Error",
                            "Server Error"));
            return null;
        }
    }

    public float getTotal(List<Commande> commandes) {
        float montant = 0;
        for (Commande commande : commandes) {
            montant += commande.getIntQuantite() * commande.getIdPlat().getPrix();
        }
        consomation.setMontant(montant);
        return montant;
    }

    public String validConsomation(List<Commande> commandes, Client client) {
        if (commandes.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erreur de validation",
                            "Votre panier est vide! remplissez le SVP!"));
            return "cart.xhtml";
        } else {
            for (Commande commande : commandes) {
                commande.setIdConsom(consomation);
            }
            consomation.setCommandeList(commandes);
            consomation.setClient(client);
            consomationFacade.create(consomation);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Votre consomation est validée",
                            "Verifiez votre panel pour suivre sa préparation"));
            return "index.html";
        }

        
    }

}

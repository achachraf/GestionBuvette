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
import java.util.stream.Collectors;
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

    public List<Consomation> getNonPaidClientConsomations(Client client) {
        List<Consomation> consomations = client.getConsomationList().stream().
                filter(c->c.getDatePayement()==null)
                .collect(Collectors.toList());
        return consomations;
       
    }

    public List<Consomation> getLastClientConsomations(Client client, int limit) {
        if(client.getConsomationList().size()>limit){
            return client.getConsomationList().subList(0, limit);
        }
        else{
            return client.getConsomationList();
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
            commandes.clear();
            if(!client.getConsomationList().contains(consomation)){
                client.getConsomationList().add(consomation);
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Votre consomation est validée",
                            "Verifiez votre panel pour suivre sa préparation"));
            return "index.html";
        }

        
    }
    
    public List<Consomation> getInProgressConsomations(Client client){
        List<Consomation> consamations = client.getConsomationList().stream()
                .filter(consom->consom.getDatePrepa() == null)
                .collect(Collectors.toList());
        System.out.println("consoms size : "+consamations.size());
        return consamations;
    }
    
    public List<Consomation> getNonPaidClientConsomationsLimit(Client client,int limit){
         List<Consomation> consomations = client.getConsomationList().stream()
                 .sorted((c1,c2)->c1.getDateCreation().compareTo(c2.getDateCreation()))
                 .filter(c->c.getDatePayement() == null)
                 .collect(Collectors.toList());
         if(consomations.size()>limit){
             return consomations.subList(0, limit);
         }
         else
            return consomations;
    }
                 
    public List<Consomation> getPaidConsomations(Client client){
        List<Consomation> consomations = client.getConsomationList().stream()
                .filter(c->c.getDatePayement() != null)
                .collect(Collectors.toList());
        return consomations;
    }

}

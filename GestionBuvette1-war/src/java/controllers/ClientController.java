/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Client;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import services.ClientFacade;

/**
 *
 * @author achra
 */
@Named(value = "clientController")
@SessionScoped
public class ClientController implements Serializable {

    /**
     * Creates a new instance of ClientController
     */
    
    @EJB
    private ClientFacade clientFacade;
    
    private Client client = new Client();
    
    
    //private String username;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

      
    public ClientController() {
    }
    
    public String register(){
        client.setActive(1);
        client.setFidelite((float) 0.0);
        System.out.println(client); 
        Integer idClient = clientFacade.createAndReturn(client);
        System.out.println("ID: "+idClient);
        client.setIdClient(idClient);
        return "index?faces-redirect=true";
    }
    
    public String login(){
        Client authClient = clientFacade.authClient(client);
        if(authClient != null){
            client = authClient;
            return "index.xhtml";
        }
        else{
            client = new Client();
            System.out.println("Login failed");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "incorrect username and password",
                            "Please enter correct username and password"));
            return "index.xhtml";
        }
    }
  
    public String logout(){
       client = new Client();
       return "index?faces-redirect=true";
    }
    
    public String getFullName(){
        return client.getPrenom()+" "+client.getNom();
    }
    
    
}

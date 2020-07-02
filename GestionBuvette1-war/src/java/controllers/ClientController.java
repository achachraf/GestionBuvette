/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Client;
import entities.Commande;
import entities.Consomation;
import entities.Plat;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import services.ClientFacade;
import services.ConsomationFacade;

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
    
    @EJB
    private ConsomationFacade consomationFacade;
    
    private Client client = new Client();
    
    private String username;
    private String password;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

      
    public ClientController() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    public String register(){
        client.setActive(1);
        client.setFidelite((float) 0.0);
        System.out.println(client); 
        Integer idClient = clientFacade.createAndReturn(client);
        System.out.println("ID: "+idClient);
        client.setIdClient(idClient);
        username = client.getUsername();
        return "/index?faces-redirect=true";
    }
    
    public String login(){
        Client authClient = clientFacade.authClient(client);
        if(authClient != null){
            client = authClient;
            username = authClient.getUsername();
            return "/index.xhtml";
        }
        else{
            client = new Client();
            System.out.println("Login failed");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "incorrect username and password",
                            "Please enter correct username and password"));
            return "/index.xhtml";
        }
    }
  
    public String logout(){
       client = new Client();
       return "/index.xhtml";
    }
    
    public String getFullName(){
        return client.getPrenom()+" "+client.getNom();
    }
    
    public String updateClient(){
        client.setUsername(username); //this trick to avoid hacking :) from frontend !
        clientFacade.edit(client);
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Votre profil est mis à jours",null));
        return "profile.xhtml";
    }
    
    public String changePassword(){
        client.setPassword(password);// sercurity features !
        clientFacade.edit(client);
         FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Votre mot de passe est mis à jours",null));
        password = "";
        return "profile.xhtml";
    }
    
    public Plat getBestPlat(List<Plat> plats){
       
        HashMap<Integer,Integer> freq = new HashMap<>();
        List<Consomation> consomations = client.getConsomationList();
        List<Plat> clientPlats = new ArrayList<>();
        for(Consomation consomation:consomations){
            for(Commande commande:consomation.getCommandeList()){
                Integer id = commande.getIdPlat().getIdPlat();
                Integer qte = commande.getIntQuantite();
                if(freq.containsKey(id)){
                    freq.put(id, freq.get(id)+qte);
                }
                else{
                    freq.put(id, qte);
                }
            }
        }
        
        Integer idPlat = freq.entrySet().stream()
                .max((e1,e2)->e1.getValue()>e2.getValue()?1:-1)
                .get().getKey();
        
        Plat plat = plats.stream()
                .filter(p->Objects.equals(p.getIdPlat(), idPlat))
                .findFirst().get();
        return plat;
    }
    
    public String consomAvg() {
        double sum = 0;
        double avg = 0;
        for(Consomation consomation:client.getConsomationList()){
            sum += consomation.getMontant();
        }
        List<Consomation> sortedConsomations = client.getConsomationList()
                .stream()
                .sorted((c1,c2)->c1.getDateCreation().compareTo(c2.getDateCreation()))
                .collect(Collectors.toList());
        
        Date last = sortedConsomations.get(sortedConsomations.size()-1).getDateCreation();
        Date first = sortedConsomations.get(0).getDateCreation();
        
        int diff = (int) Math.floor(((last.getTime()-first.getTime())/(1000*60*60*24)));
        
        avg = sum/diff;
        DecimalFormat df = new DecimalFormat("###.###");
        return df.format(avg);
    }
    
    public float getMinConsom(){
        Consomation consomation = client.getConsomationList().stream()
                .min((e1,e2)->e1.getMontant()>e2.getMontant()?1:-1)
                .get();
        
        return consomation.getMontant();
    }
     public float getMaxConsom(){
        Consomation consomation = client.getConsomationList().stream()
                .max((e1,e2)->e1.getMontant()>e2.getMontant()?1:-1)
                .get();
        
        return consomation.getMontant();
    }
     
    public List<Client> getTopClients(){
        try {
            List<Client> clients = clientFacade.getTopClients();
            return clients;
        } catch (Exception e) {
            System.out.println("error in db : "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Error 500","A server error has occured"));
            return null;
        }
    }
    
    public List<Client> getTopClientsNonActive(){
        try {
            List<Client> clients = clientFacade.getTopClientsNonActive();
            return clients;
        } catch (Exception e) {
            System.out.println("error in db : "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Error 500","A server error has occured"));
            return null;
        }
    }
    
    public List<Client> getTopClientstous(){
        try {
            List<Client> clients = clientFacade.getTopClientstous();
            return clients;
        } catch (Exception e) {
            System.out.println("error in db : "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "Error 500","A server error has occured"));
            return null;
        }
    }
     
    public String signin(){
        Date date=new Date();
        this.client.setActive(1);
        this.client.setDateCreation(date);
        this.client.setFidelite((float)0);
        this.clientFacade.create(this.client);
        return "/admin/consommateurs.xhtml";
    }

}

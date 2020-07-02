/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Category;
import entities.Plat;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import services.CategoryFacade;

/**
 *
 * @author achra
 */
@Named(value = "categoriesController")
@SessionScoped
public class CategoriesController implements Serializable {

    /**
     * Creates a new instance of CategoriesController
     */
    @EJB
    CategoryFacade categoryFacade;
    
    public CategoriesController() {
    }
    
    public List<Category> getCategories(){
        List<Category> categories = categoryFacade.findAll();
        return categories;
    }
    
    public Category getCategoryById(int idCategory){
        Category category = categoryFacade.find(idCategory);
        System.out.println(category);
//        if(category == null){
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_WARN,
//                           "Cette category n'existe pas","null"));
//        }
        return category;
    }
    
    public List<Category> getLimitedRandomPlats(int limit){
        List<Category> categories = categoryFacade.findAll();
        if(categories.size()<limit){
            return categories;
        }
        List<Category> randCateg = new ArrayList<>();
        Random rand = new Random();
        for(int i=0;i<limit;i++){
            Category category = categories.get(rand.nextInt(categories.size()-1));
            randCateg.add(category);
            categories.remove(category);
        }
        return randCateg;
    }
}

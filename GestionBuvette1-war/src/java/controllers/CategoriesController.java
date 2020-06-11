/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Category;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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
}

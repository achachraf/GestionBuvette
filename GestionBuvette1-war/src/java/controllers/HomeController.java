/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author achra
 */
@Named(value = "homeController")
@Dependent
public class HomeController {

    public class Dish{
       
        private String name;
        
         public Dish(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
         
        
    }
   
    private Dish dish;
    
    public HomeController() {
    }
    
    public Dish getRandomDish(){
        return new Dish("btata");
    }
}

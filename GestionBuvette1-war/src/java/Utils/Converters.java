/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 *
 * @author achra
 */

@Named(value = "converters")
@Dependent

public class Converters {
    

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    
    public Converters() {
    }
    
     public String getFormattedDate(Date date){
        String formattedDate = formatter.format(date);
        return formattedDate;
    }
}

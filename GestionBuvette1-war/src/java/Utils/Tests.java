/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;

/**
 *
 * @author achra
 */
@Named(value = "tests")
@ConversationScoped
public class Tests implements Serializable {

    /**
     * Creates a new instance of Tests
     */
    public Tests() {
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ritvik.invoicei;

import java.security.Permission;

/**
 *
 * @author ritvikc
 */

//this will prevent system.exit used by PDFBOX app
public class MySecurityManager extends SecurityManager{
    
    //by default security manager
    private SecurityManager baseSecurityManager;

    public MySecurityManager(SecurityManager baseSecurityManager) {
        //paasing arg to base
        this.baseSecurityManager = baseSecurityManager;
    }

    //Need to Override permission checker
    @Override
    public void checkPermission(Permission permission) {
        //exitVM is generated from System.exit 
        if (permission.getName().startsWith("exitVM")) {
            //we will ignore this exception 
            throw new SecurityException("Hey, I am disabling System.exit from PDFBOX app. Please Ignore");
        }
        //Default permission to others
        if (baseSecurityManager != null) {
            baseSecurityManager.checkPermission(permission);
        } else {
            return;
        }
    }
}

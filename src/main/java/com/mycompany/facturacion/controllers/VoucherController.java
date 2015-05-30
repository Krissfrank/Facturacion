package com.mycompany.facturacion.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author Cris
 */
public class VoucherController {
    
    public static ModelAndView form(Request rq, Response rs){
        return new ModelAndView(new HashMap<>(), "form");
    }
    
    public static Object saveForm(Request rq, Response rs){
        String user = rq.queryParams("user");
        
        return null;
    }
}

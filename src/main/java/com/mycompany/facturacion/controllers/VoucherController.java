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
        String Tnombre = rq.queryParams("Tnombre");
        String Trfc= rq.queryParams("Trfc"); 
        String Ttaxation= rq.queryParams("Ttaxation"); 
        String Tcolonia= rq.queryParams("Tcolonia"); 
        String Tcalle= rq.queryParams("Tcalle"); 
        String Tinterior= rq.queryParams("Tinterior"); 
        String Texterior= rq.queryParams("Texterior"); 
        String Tlocalidad= rq.queryParams("Tlocalidad"); 
        String Treferencia= rq.queryParams("Treferencia"); 
        String Tpais= rq.queryParams("Tpais"); 
        String Testado= rq.queryParams("Testado"); 
        String Tciudad= rq.queryParams("Tciudad"); 
        String Tcp= rq.queryParams("Tcp");
        String Rnombre= rq.queryParams("Rnombre");
        String Rrfc= rq.queryParams("Rrfc");
        String Rtaxation= rq.queryParams("Rtaxation");
        String Rcolonia= rq.queryParams("Rcolonia");
        String Rcalle= rq.queryParams("Rcalle");
        String Rinterior= rq.queryParams("Rinterior");
        String Rexterior= rq.queryParams("Rexterior");
        String Rlocalidad= rq.queryParams("Rlocalidad");
        String Rreferencia= rq.queryParams("Rreferencia");
        String Rpais= rq.queryParams("Rpais");
        String Restado= rq.queryParams("Restado");
        String Rciudad= rq.queryParams("Rciudad");
        String Rcp= rq.queryParams("Rcp");
        String Rmail= rq.queryParams("Rmail");        
        String Vserie= rq.queryParams("Vserie");
        String Vfolio= rq.queryParams("Vfolio");
        String Vdivisa= rq.queryParams("Vdivisa");
        String Vtipodiv= rq.queryParams("Vtipodiv");
        String Vmetpago= rq.queryParams("Vmetpago");
        String Vtipopago= rq.queryParams("Vtipopago");
        String Vcondition= rq.queryParams("Vcondition");
        String Vplaceex= rq.queryParams("Vplaceex");
        String Vkindvouch= rq.queryParams("Vkindvouch");
        
        return null;
    }
    
}

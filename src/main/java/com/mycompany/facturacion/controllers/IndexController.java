
package com.mycompany.facturacion.controllers;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author Cris
 */
public class IndexController {
    
    public static ModelAndView index(Request rq, Response rs){
        Object user = rq.session().attribute("user");
              Map<String, Object> map = new HashMap<>();
        map.put("user", user);
    return new ModelAndView(map, "index");
    }
    
}

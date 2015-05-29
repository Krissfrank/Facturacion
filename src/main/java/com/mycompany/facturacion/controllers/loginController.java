package com.mycompany.facturacion.controllers;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.link.Row;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author Abelardo
 */

public class loginController {
    
    public static ModelAndView login(Request request, Response response) {
        Database database = Database.use("mysql");
        database.open();
        Row transmitter = database.table("transmitters").first();
        String name = transmitter.string("name");
        database.close();
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        return new ModelAndView(values, "index");
    }
}

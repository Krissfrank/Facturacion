package com.mycompany.facturacion.controllers;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;
import com.mycompany.facturacion.Register;
import com.sun.org.apache.regexp.internal.recompile;
import java.util.ArrayList;
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

    /**
     *
     * @param rq 
     * @param rs
     * @return
     */
    public static ModelAndView index(Request rq, Response rs) {

        Map<String, Object> map = new HashMap<>();
        Database database = Database.use("mysql");
        database.open();

        if (rq.session().attribute("user") != null) {
            Row user = (Row) rq.session().attribute("user");
            RowList regis = database.table("regis re")
                    .where("user_id", "=", user.number("user_id"))
                    .get();
            ArrayList<Register> registers = new ArrayList<>();
            for (Row regi : regis) {
                registers.add(new Register(regi));
            }
            map.put("user", user);
            map.put("regis", registers);
        }
        database.close();
        return new ModelAndView(map, "index");
    }

}

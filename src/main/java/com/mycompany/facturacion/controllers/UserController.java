package com.mycompany.facturacion.controllers;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.link.Row;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author Cris
 */
public class UserController {

    /**
     * Regresa vista login.jade
     * @param rq
     * @param rs
     * @return 
     */
    public static ModelAndView login(Request rq, Response rs) {
        return new ModelAndView(new HashMap<>(), "login");
    }

    /**
     * String user solicita usuario del input 
     * String pass solicita contraseña del input 
     * Row checkuser = database.table("users")... compara input con base de datos  
     * @param rq Solicita user y pass para compararlo en la base de datos 'users'
     * @param rs En caso de true dirige al index, false para regresar al login
     * @return
     */
    public static Object doLogin(Request rq, Response rs) {
        String user = rq.queryParams("user");
        String pass = rq.queryParams("pass");
        Database database = Database.use("mysql");
        database.open();
        Row checkuser = database.table("users").where("user", "=", user).where("pass", "=", pass).first();
        database.close();
        if (null == checkuser) {
            rs.redirect("/login");
        } else {
            rq.session().attribute("user", checkuser);
            rs.redirect("/index");
        }
        return null;
    }

    /**
     * 
     * @param rq elimina el usuario
     * @param rs regresa a index
     * @return 
     */
    public static Object logout(Request rq, Response rs) {
        rq.session().removeAttribute("user");
        rs.redirect("/index");
        return null;
    }
}

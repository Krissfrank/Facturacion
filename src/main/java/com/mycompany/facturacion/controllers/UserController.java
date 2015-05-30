
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
    public static ModelAndView login(Request rq, Response rs){
	return new ModelAndView(new HashMap<>(), "login");
}
 
public static Object doLogin(Request rq, Response rs){
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
 
public static Object logout(Request rq, Response rs){
	rq.session().removeAttribute("user");
		rs.redirect("/index");
	return null;
}
}

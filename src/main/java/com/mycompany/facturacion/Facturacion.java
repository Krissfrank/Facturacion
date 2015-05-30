package com.mycompany.facturacion;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.configuration.ProjectSource;
import com.github.luischavez.database.configuration.XMLBuilder;
import com.mycompany.facturacion.controllers.IndexController;
import com.mycompany.facturacion.controllers.UserController;
import com.mycompany.facturacion.controllers.VoucherController;
import static spark.Spark.*;
import spark.template.jade.JadeTemplateEngine;

/**
 *
 * @author Cris
 */
public class Facturacion {

    public static void main(String[] args) {
        Database.load(new XMLBuilder(), new ProjectSource("/database.xml"));
        port(80);

        // The hello.jade template file is in the resources/templates directory
        get("/index", IndexController::index, new JadeTemplateEngine());
        get("/login", UserController::login, new JadeTemplateEngine());
        post("/login", UserController::doLogin);
        get("/logout", UserController::logout);
        get("/form", VoucherController::form, new JadeTemplateEngine());
        post("/saveForm",VoucherController::saveForm);
        Database database = Database.use("mysql");
        database.open();
        boolean empty = database.table("users").get().empty();
        if (empty == true) {
            database.insert("users", "user, pass", "manuel", "a123");
        }
        
    }
}

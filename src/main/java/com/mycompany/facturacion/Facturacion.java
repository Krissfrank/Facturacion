package com.mycompany.facturacion;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.configuration.ProjectSource;
import com.github.luischavez.database.configuration.XMLBuilder;
import com.mycompany.facturacion.controllers.IndexController;
import com.mycompany.facturacion.controllers.UserController;
import com.mycompany.facturacion.controllers.VoucherController;
import com.mycompany.facturacion.controllers.outputController;
import static spark.Spark.*;
import spark.template.jade.JadeTemplateEngine;

/**
 *
 * @author Cris
 */
public class Facturacion {

    /**
     * Crea base de datos Inserta usuario y contraseña para users Crea
     * direcciones para los templates
     *
     * @param args
     */
    public static void main(String[] args) {
        Database.load(new XMLBuilder(), new ProjectSource("/database.xml"));
        Database database = Database.use("mysql");
        database.open();
        database.migrate();
        //si la tabla "users" esta vacia crea usuario y pass, si no esta vacia no hace nada.
        boolean empty = database.table("users").get().empty();
        if (empty == true) {
            database.insert("users", "user, pass", "manuel", "a123");
        }
        
        //localhost
        port(80);

        // The hello.jade template file is in the resources/templates directory
        get("/", (rq, rs) -> {
            rs.redirect("/index");
            return null;
        });
        get("/index", IndexController::index, new JadeTemplateEngine());
        get("/login", UserController::login, new JadeTemplateEngine());
        post("/login", UserController::doLogin);
        get("/logout", UserController::logout);
        get("/form", VoucherController::form, new JadeTemplateEngine());
        post("/saveForm", VoucherController::saveForm);
        get("/pdf/:id", outputController::pdf);
        get("/xml/:id", outputController::xml, new JadeTemplateEngine());
        get("/delete/:id", VoucherController::delete);
    }
}

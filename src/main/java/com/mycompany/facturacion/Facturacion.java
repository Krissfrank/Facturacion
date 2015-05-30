package com.mycompany.facturacion;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.configuration.ProjectSource;
import com.github.luischavez.database.configuration.XMLBuilder;
import com.github.luischavez.database.link.Row;
import com.github.luischavez.database.link.RowList;
import com.mycompany.facturacion.controllers.IndexController;
import com.mycompany.facturacion.controllers.UserController;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
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
        get("/index", IndexController::index , new JadeTemplateEngine());
        get("/login",UserController::login , new JadeTemplateEngine());
        post("/login", UserController::doLogin );
        get("/logout" ,UserController::logout);
         Database database = Database.use("mysql");
         database.open();
        // database.reset();
         database.migrate();
         database.insert("receptors", "rfc, name,taxation,street,noEx,noIn,hood,location,reference,city,state,postalCode,country,email", 
         "GAGA940429SZ0", "Abelardo","Persona fisica","Rinconadas de la sierra del rosario","4470",""
         ,"rinconadas de la sierra","Chihuahua","","Chihuahua","Chihuahua","31124","Mexico","Abelardogy@gmail.com");
         RowList receptors = database.table("receptors").get();
         for (Row row : receptors) {
         System.out.println(row.string("name"));
         }
        
         database.insert("transmitters","rfc, name, taxation,street,noEx,noIn,hood,location,reference,city,state,postalCode,country", 
         "FADC931106995", "Cristian Manuel","Persona fisica","San ambroggio","3710","" ,"Privanza san angel","Chihuahua","","Chihuahua","Chihuahua","31107","Mexico");
         RowList transmitters = database.table("transmitters").get();
         for (Row transmitter : transmitters) {
         System.out.println(transmitter.string("name")); 
         }
         database.close();
         
    }
}

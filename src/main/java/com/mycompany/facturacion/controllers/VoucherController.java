package com.mycompany.facturacion.controllers;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.link.Affecting;
import com.github.luischavez.database.link.Row;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
         if(rq.session().attribute("user") ==null ){
            rs.redirect("/login");
      //      return null;
        }
        return new ModelAndView(new HashMap<>(), "form");
    }
    
    public static Object saveForm(Request rq, Response rs){
        if(rq.session().attribute("user") ==null ){
            rs.redirect("/login");
            return null;
        }
        /**
         * Obtiene las cadenas de los input
         */    
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
        
        String taxesTrans=rq.queryParams("taxesTrans");
        String TWrate=rq.queryParams("TWrate");
        
        String Ccantidad=rq.queryParams("Ccantidad");
        String Cunidad=rq.queryParams("Cunidad");
        String Cnoid=rq.queryParams("Cnoid");
        String Cprice=rq.queryParams("Cprice");
        String Cdesc=rq.queryParams("Cdesc");
        /**
         * abre la abse de datos
         */
        Database database = Database.use("mysql");
        database.open();
        /**
         * Permite dejar en blanco los input de numero interior y unidad
         */
        if(Tinterior==null)
            Tinterior="";
        if(Rinterior==null)
            Rinterior="";
        if(Cunidad==null)
           Cunidad="0";
        
        /**
         * Inserta los datos en las tablas
         */
        Affecting transInsert = database.insert("Transmitters","name, rfc, taxation, hood, street, noIn, noEx, location, reference, country, state, city, postalCode" , 
                Tnombre, Trfc, Ttaxation,Tcolonia, Tcalle, Tinterior, Texterior, Tlocalidad, Treferencia, Tpais, Testado, Tciudad, Tcp  );
        Object transID = transInsert.getGeneratedKeys()[0];
        
        Affecting receptInsert = database.insert("receptors","name,rfc,taxation,hood, street, noIn, noEx, location, reference,country, state, city, postalCode, email"
                ,Rnombre, Rrfc, Rtaxation, Rcolonia,Rcalle, Rinterior,Rexterior, Rlocalidad, Rreferencia, Rpais, Restado, Rciudad,Rcp,Rmail );
        Object recepID = receptInsert.getGeneratedKeys()[0];
        
        Affecting voucherInsert = database.insert("vouchers", "created_at, serie, folio, currency,kindofchange,methodpay,kindofpay,conditionpay,placeofexpedition,kindofvoucher"
                ,LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), Vserie,Vfolio,Vdivisa,Vtipodiv,Vmetpago,Vtipopago,Vcondition,Vplaceex,Vkindvouch);
        Object voucherID = voucherInsert.getGeneratedKeys()[0];
        
        database.insert("taxestrans", "voucher_id, taxes, rate", voucherID, taxesTrans, TWrate);
        
        database.insert("concepts", "voucher_id, quantity, unit, price, noId, description ", voucherID, Ccantidad, Cunidad, Cprice, Cnoid,Cdesc);
        Row user =(Row) rq.session().attribute("user");
        database.insert("regis", "receptor_id,voucher_id,transmitter_id,user_id",recepID,voucherID,transID, user.number("user_id") );
        database.close();
        rs.redirect("/index");
        return null;
    }
    
    public static Object delete(Request rq, Response rs) {
        if(rq.session().attribute("user") == null){
            rs.redirect("/login");
            return null;
        }
        Object id = rq.params(":id");
        Database database = Database.use("mysql");
        database.open();
        Row regis = database.table("regis").where("regis_id", "=", id).first();
        if (null != regis) {
            long userId = regis.number("user_id");
            Row user = (Row) rq.session().attribute("user");
            if (userId == user.number("user_id")) {
                database.where("regis_id", "=", id).delete("regis");
                database.where("receptor_id", "=", regis.number("receptor_id")).delete("receptors");
                database.where("transmitter_id", "=", regis.number("transmitter_id")).delete("transmitters");
                database.where("voucher_id", "=", regis.number("voucher_id")).delete("vouchers");
            }
        }
        database.close();
        rs.redirect("/index");
        return null;
    }
}

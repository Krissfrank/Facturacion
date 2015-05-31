
package com.mycompany.facturacion;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.link.Row;

/**
 *
 * @author Cris
 */
public class Register {
    
    private Row regis;

    public Register(Row regis) {
        this.regis = regis;
    }
    
    public Row receptor(){
    Database database = Database.use("mysql");
        database.open();
        Row first = database.table("receptors").where("receptor_id", "=", this.regis.number("receptor_id")).first();
        database.close();
        return first;
    }
    
    public Row transmitter(){
    Database database = Database.use("mysql");
        database.open();
        Row first = database.table("transmitters").where("transmitter_id", "=", this.regis.number("transmitter_id")).first();
        database.close();
        return first;
        
    }
    
    public Row voucher(){
    Database database = Database.use("mysql");
        database.open();
        Row first = database.table("vouchers").where("voucher_id", "=", this.regis.number("voucher_id")).first();
        database.close();
        return first;
    }
    
    
}


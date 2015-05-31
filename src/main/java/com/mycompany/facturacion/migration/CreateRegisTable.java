
package com.mycompany.facturacion.migration;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.Migration;

/**
 *
 * @author Cris
 */
public class CreateRegisTable implements Migration {

    @Override
    public void up(Database database) {
       database.create("regis", table ->{
            table.integer("regis_id").incremented();
            table.integer("user_id");
            table.integer("receptor_id");
            table.integer("voucher_id");
            table.integer("transmitter_id");
            table.primary("regis_id");
            table.foreign("receptor_id", "receptors", "receptor_id", "CASCADE", "CASCADE");
            table.foreign("voucher_id", "vouchers", "voucher_id", "CASCADE", "CASCADE");
            table.foreign("transmitter_id", "transmitters", "transmitter_id", "CASCADE", "CASCADE");
            table.foreign("user_id", "users", "user_id", "CASCADE", "CASCADE");
           
         
            
        });
    }

    @Override
    public void down(Database database) {
       database.drop("regis");
    }
    
}

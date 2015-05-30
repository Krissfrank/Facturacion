
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
           
            table.integer("receptor_id");
            table.integer("voucher_id");
            table.integer("transmitter_id");
            
           
         
            
        });
    }

    @Override
    public void down(Database database) {
       database.drop("regis");
    }
    
}

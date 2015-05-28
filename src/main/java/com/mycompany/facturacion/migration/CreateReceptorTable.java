package com.mycompany.facturacion.migration;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.Migration;

/**
 *
 * @author Cris
 */
public class CreateReceptorTable implements Migration{

    @Override
    public void up(Database database) {
        database.create("receptors", table ->{
            table.integer("receptor_id").incremented();
            table.string("rfc", 13);
            table.text("name");
            table.text("taxation");
            table.text("street");
            table.string("noEx",20);
            table.string("noIn",20);
            table.text("hood");
            table.text("location");
            table.text("reference");
            table.text("city");
            table.text("state");
            table.string("postalCode",20);
            table.text("country");
            table.string("email",100);
            table.primary("receptor_id");

            
        });
    }

    @Override
    public void down(Database database) {
       database.drop("receptors");
       
    }
    
    
    
    
}

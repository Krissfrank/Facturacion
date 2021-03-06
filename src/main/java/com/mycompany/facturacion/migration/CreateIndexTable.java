package com.mycompany.facturacion.migration;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.Migration;

/**
 *
 * @author Cris
 */
public class CreateIndexTable implements Migration {

    @Override
    public void up(Database database) {
         database.create("users", table ->{
            table.integer("user_id").incremented();
            table.string("user", 20);
            table.string("pass", 20); 
            table.primary("user_id");
            table.unique("user");
            
        });
    }

    @Override
    public void down(Database database) {
        database.drop("users");
    }
    
}

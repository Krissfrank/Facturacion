package com.mycompany.facturacion.migration;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.Migration;

/**
 *
 * @author Abelardo
 */
public class CreateConceptsTable implements Migration {

    @Override
    public void up(Database database) {
        database.create("concepts", table -> {
            //table.integer("receptor_id").incremented();
            table.integer("voucher_id");
            table.integer("quantity");
            table.string("unit",100);
            table.decimal("price", 10, 2);
            table.integer("noId");
            table.text("description");
            table.foreign("voucher_id", "vouchers", "voucher_id", "cascade","cascade");
        });
    }

    @Override
    public void down(Database database) {
        database.drop("concepts");
    }
}

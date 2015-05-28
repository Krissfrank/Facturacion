package com.mycompany.facturacion.migration;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.Migration;

/**
 *
 * @author Abelardo
 */
public class CreateTaxesTransTable implements Migration {

    @Override
    public void up(Database database) {

        database.create("taxestrans", table -> {
            table.integer("voucher_id");
            table.string("taxes", 100);
            //table.decimal("rate", 5, 2);
            table.decimal("import", 10, 2);
            table.foreign("voucher_id", "vouchers", "voucher_id", "cascade", "cascade");
        });
    }

    @Override
    public void down(Database database) {
        database.drop("taxestrans");
    }
}

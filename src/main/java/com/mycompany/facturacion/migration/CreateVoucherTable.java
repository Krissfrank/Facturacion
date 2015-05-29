package com.mycompany.facturacion.migration;
import com.github.luischavez.database.Database;
import com.github.luischavez.database.Migration;

/**
 *
 * @author Cris
 */
public class CreateVoucherTable implements Migration {

    @Override
    public void up(Database database) {
       database.create("vouchers", table ->{
            table.integer("voucher_id").incremented();
            table.dateTime("created_at");
            table.string("serie",2);
            table.integer("folio");
            table.text("currency");
            table.text("kindofchange");
            table.text("methodpay");
            table.text("conditionpay");
            table.text("placeofexpedition");
            table.string("kindofvoucher",20);
            table.text("kindofpay");
            table.primary("voucher_id");
        });
    }

    @Override
    public void down(Database database) {
        database.drop("vouchers");
        
    }
    
}

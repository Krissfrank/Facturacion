package com.mycompany.facturacion.migration;

import com.github.luischavez.database.Migrator;

/**
 *
 * @author Cris
 */
public class MyMigrator extends Migrator {

    @Override
    public void setup() {
        this.register(new CreateReceptorTable());
        this.register(new CreateTransmitterTable());
        this.register(new CreateVoucherTable());
        this.register(new CreateConceptsTable());
        this.register(new CreateTaxesTransTable());
        this.register(new CreateTaxesWithheldTable());
        this.register(new CreateIndexTable());
        this.register(new CreateRegisTable());
    }
}

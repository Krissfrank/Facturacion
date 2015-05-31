package com.mycompany.facturacion.controllers;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.link.Row;
import com.mycompany.facturacion.Register;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 * @author Abelardo
 */
public class outputController {

    public static ModelAndView xml(Request rq, Response rs) {
        String id = rq.params(":id");
        Database database = Database.use("mysql");
        database.open();
        Row regis = database.table("regis").where("regis_id", "=", id).first();
        Register register = new Register(regis);
        database.close();

        HashMap<Object, Object> values = new HashMap<>();
        values.put("regis", register);
        rs.header("Content-Type", "application/xml");
        rs.type("text/xml");
        return new ModelAndView(values, "xml");
    }

    public static Object pdf(Request rq, Response rs) throws IOException, COSVisitorException {
        String id = rq.params(":id");
        Database database = Database.use("mysql");
        database.open();
        Row regis = database.table("regis").where("regis_id", "=", id).first();
        Register register = new Register(regis);
        database.close();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

// Create a new font object selecting one of the PDF base fonts
        PDFont font = PDType1Font.HELVETICA_BOLD;

// Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
        //Titulo 
        contentStream.beginText();
        contentStream.setFont(font, 20);
        contentStream.moveTextPositionByAmount(170, 750);
        contentStream.drawString("Factura electronica - compiladores");
        contentStream.endText();

        /**
         * Datos emisor
         */
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 700);
        contentStream.drawString("Emisor: " + register.transmitter().string("name"));
        contentStream.endText();

        //Fecha emisor
        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(380, 720);
        contentStream.drawString("Fecha emision: " + register.voucher().dateTime("created_at"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 680);
        contentStream.drawString("RFC:" + register.transmitter().string("rfc"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 650);
        contentStream.drawString("Direccion: ");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(65, 630);
        contentStream.drawString("Colonia: " + register.transmitter().string("hood") + "  Calle: " + register.transmitter().string("street") + " #" + register.transmitter().string("noex"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(65, 610);
        contentStream.drawString(register.transmitter().string("state") + ", " + register.transmitter().string("city") + " CP: " + register.transmitter().string("postalcode"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(65, 590);
        contentStream.drawString(register.transmitter().string("country"));
        contentStream.endText();

        /**
         * Datos receptor
         */
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 550);
        contentStream.drawString("Receptor: " + register.receptor().string("name"));
        contentStream.endText();

        //folio
        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(380, 550);
        contentStream.drawString("Folio: " + register.voucher().number("folio"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(380, 530);
        contentStream.drawString("Serie: " + register.voucher().string("serie"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 530);
        contentStream.drawString("RFC:" + register.receptor().string("rfc"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 500);
        contentStream.drawString("Direccion: ");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(65, 480);
        contentStream.drawString("Colonia: " + register.receptor().string("hood") + "  Calle: " + register.receptor().string("street") + " #" + register.receptor().string("noex"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(65, 460);
        contentStream.drawString(register.receptor().string("state") + ", " + register.receptor().string("city") + ", CP: " + register.receptor().string("postalcode"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(65, 440);
        contentStream.drawString(register.receptor().string("country"));
        contentStream.endText();

        /**
         * Concepto titulo
         */
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 410);
        contentStream.drawString("Cantidad:");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(170, 410);
        contentStream.drawString("Unidad:");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(280, 410);
        contentStream.drawString("Descripcion:");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(450, 410);
        contentStream.drawString("importe:");
        contentStream.endText();

        /**
         * Concepto Datos
         */
        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(65, 390);
        contentStream.drawString("" + register.concepts().number("quantity"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(170, 390);
        contentStream.drawString(register.concepts().string("unit"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(280, 390);
        contentStream.drawString(register.concepts().string("description"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(450, 390);
        contentStream.drawString("$" + register.taxestrans().decimal("import"));
        contentStream.endText();

        /**
         * Cosas de pago lol
         */
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 340);
        contentStream.drawString("Forma de pago: " + register.voucher().string("kindofpay"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 320);
        contentStream.drawString("Metodo de pago: " + register.voucher().string("methodpay"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 300);
        contentStream.drawString("Condiciones de pago: " + register.voucher().string("conditionpay"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 280);
        contentStream.drawString("Referencia: " + register.receptor().string("reference"));
        contentStream.endText();

        /**
         * Divisas y lugar expedicion
         */
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(450, 340);
        contentStream.drawString("Divisa: "+register.voucher().string("currency"));
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(450, 320);
        contentStream.drawString("Tipo de impuesto: ");
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(450, 300);
        contentStream.drawString(""+register.taxestrans().string("taxes"));
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 260);
        contentStream.drawString("Lugar de expedicion: ");
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(65, 240);
        contentStream.drawString(register.transmitter().string("city")+", "+register.transmitter().string("state"));
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(450, 260);
        contentStream.drawString("Total: ");
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(450, 240);
        contentStream.drawString("$"+register.taxestrans().decimal("import"));
        contentStream.endText();
        
        

        // Make sure that the content stream is closed:
        contentStream.close();
        HttpServletResponse raw = rs.raw();
// Save the results and ensure that the document is properly closed:
        document.save(raw.getOutputStream());
        raw.getOutputStream().flush();
        raw.getOutputStream().close();
        document.close();
        return rs.raw();
    }

}

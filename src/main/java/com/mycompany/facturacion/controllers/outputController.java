package com.mycompany.facturacion.controllers;

import com.github.luischavez.database.Database;
import com.github.luischavez.database.link.Row;
import com.mycompany.facturacion.Register;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import spark.Request;
import spark.Response;

/**
 *
 * @author Abelardo
 */
public class outputController {

    public static Object pdf(Request rq, Response rs) throws IOException, COSVisitorException {
        String id = rq.params(":id");
        Database database = Database.use("mysql");
        database.open();
        Row regis = database.table("regis").where("regis_id","=", id).first();
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
        
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(100, 700);
        contentStream.drawString("RFC:"+register.receptor().string("rfc"));
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(100, 600);
        contentStream.drawString("Nombre:"+register.receptor().string("name"));
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

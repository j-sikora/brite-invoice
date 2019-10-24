package pl.com.britenet.briteinvoice.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.springframework.stereotype.Service;
import pl.com.britenet.briteinvoice.PdfException;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class PdfTemplate {

    @SneakyThrows
    public String pdfToString(InputStream inputStream) {
        PDDocument document = null;
        try {
            document = PDDocument.load(inputStream);    //it's not Autoclosable ...
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                return text;
            }
            else {
                throw new PdfException("File can't be encrypted");
            }
        }
        catch (IOException e) {
            log.error("Could not read from pdf");
            throw new PdfException("Could't read pdf file", e);
        }
        finally {
            if(document != null) {
                document.close();
            }
        }
    }

}

package pl.com.britenet.briteinvoice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class InvoiceReaderService {

    private PdfTemplate pdfTemplate;

    public InvoiceReaderService(PdfTemplate pdfTemplate) {
        this.pdfTemplate = pdfTemplate;
    }

    public String getAmount(String path) {
        File file = new File(path);
        try(InputStream inputStream = new FileInputStream(file)) {
            String pdfText = pdfTemplate.pdfToString(inputStream);
            System.out.println(pdfText);
            // Create a Pattern object
            Pattern r = Pattern.compile("(ZAPŁACONO DO ZAPŁATY\\r?\\n)(\\d\\s+\\d*,?\\d\\d)");
            // Now create matcher object.
            Matcher m = r.matcher(pdfText);
            while(m.find()) {
                System.out.println("group:"+m.group(2));
            }
            return m.matches() ? m.group(1) : null;

        }
        catch (IOException e) {
            log.error("Could not open file: " + path, e);
            return null;
        }
    }

    @PostConstruct
    private void test() {
        String path = "D:/IdeaProjects/brite-invoice/src/test/resources/elisoft-faktura-2.pdf";

        System.out.println(getAmount(path));
    }
}

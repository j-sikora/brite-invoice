package pl.com.britenet.briteinvoice.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class InvoiceReaderService {

    private PdfTemplate pdfTemplate;

    public InvoiceReaderService(PdfTemplate pdfTemplate) {
        this.pdfTemplate = pdfTemplate;
    }

    @SneakyThrows
    public String getAmount(String path) {
        File file = new File(path);
        try(InputStream inputStream = new FileInputStream(file)) {
            String pdfText = pdfTemplate.pdfToString(inputStream);
            System.out.println(pdfText);
            // Create a Pattern object
            Pattern r = Pattern.compile("(ZAPŁACONO DO ZAPŁATY\\r?\\n)(\\d\\s+\\d*,?\\d\\d)");
            // Now create matcher object.
            Matcher m = r.matcher(pdfText);
            String amount = null;
            while(m.find()) {
                System.out.println("group:"+m.group(2));
                amount = m.group(2);
            }
            return amount;
        }
    }

}

package pl.com.britenet.briteinvoice.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class InvoiceReaderServiceTest extends Specification {

    @Autowired
    InvoiceReaderService invoiceReaderService

    def 'check elisoft invoice amount'() {
        given:
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource('elisoft-invoice.pdf');
            def file = resource.getFile()
        when:
            def amount = invoiceReaderService.getAmount(file)
        then:
            amount == '1 230,00'

    }
}

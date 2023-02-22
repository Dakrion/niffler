package niffler.jupiter;

import niffler.api.NifflerSpendClient;
import niffler.model.CurrencyValues;
import niffler.model.SpendJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class BeforeSuiteExtension implements AroundAllTestsExtension {
    private final NifflerSpendClient nsc = new NifflerSpendClient();

    private String spendId;
    @Override
    public void beforeAllTests(ExtensionContext context) throws Exception {
        SpendJson spend = new SpendJson();
        spend.setAmount(30.0);
        spend.setCategory("Рестораны");
        spend.setCurrency(CurrencyValues.RUB);
        spend.setUsername("Roman");
        spend.setDescription("");
        spend.setSpendDate(String.valueOf(LocalDateTime.now()));
        SpendJson created = nsc.createSpend("Roman", spend);
        Assertions.assertNotNull(created.getId());
        spendId = String.valueOf(created.getId());
        System.out.println("Spend created");
    }

    @Override
    public void afterAllTests() throws Exception {
        nsc.deleteSpends("Roman", List.of(spendId));
    }
}

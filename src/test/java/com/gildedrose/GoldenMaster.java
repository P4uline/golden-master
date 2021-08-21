package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import static com.gildedrose.TexttestFixture.items;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

/*
    The golden master file was created from the result of <code>TexttestFixture</code>.
    It contains all the possible cases of quality and sell in updates and provide a 100% coverage.
 */
public class GoldenMaster {

    public static final String ERROR = "Error";

    private URL resource = getClass().getClassLoader().getResource("golden-master.txt");

    @Test
    public void should_golden_master_be_true() throws IOException, URISyntaxException {
        var app = new GildedRose(items);

        var it = Files.lines(Paths.get(resource.toURI())).iterator();
        assertThat(nextLine(it)).isEqualTo("OMGHAI!");
        int days = 100;
        for (int i = 0; i < days; i++) {
            assertThat(nextLine(it)).isEqualTo("-------- day " + i + " --------");
            assertThat(nextLine(it)).isEqualTo("name, sellIn, quality");

            // Assert golden master items are the same as under test items for the day
            stream(items).forEach(item -> assertThat(nextLine(it)).isEqualTo(item.toString()));

            assertThat(nextLine(it)).isEqualTo("");

            // Daily update under test items
            app.updateQuality();
        }
    }

    private String nextLine(Iterator<String> iterator) {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return ERROR;
    }
}

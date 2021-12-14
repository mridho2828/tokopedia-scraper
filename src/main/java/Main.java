import model.Model;
import scraper.Scraper;
import writer.Writer;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("start program....");
        Scraper scraper = new Scraper();
        List<Model> models = scraper.getModels();
        Writer writer = new Writer();
        writer.toCsv(models);
    }

    private List<Model> mockScraper() {
        Model model1 = Model.builder()
                .name("hp1")
                .description("nice")
                .imageLink("link1")
                .price(3700000)
                .rating("4.5")
                .merchant("merchant1")
                .build();
        Model model2 = Model.builder()
                .name("hp2")
                .description("cheap")
                .imageLink("link2")
                .price(2000000)
                .rating("4.6")
                .merchant("merchant2")
                .build();

        return Arrays.asList(model1, model2);
    }
}

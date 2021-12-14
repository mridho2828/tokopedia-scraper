package writer;

import model.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {
    private static final String FILENAME = "handphones.csv";
    private FileWriter fileWriter;

    public void toCsv(List<Model> models) {
        System.out.println("start writing models to csv...");
        if (models == null) {
            System.out.println("list of models is null");
            return;
        }
        try {
            fileWriter = new FileWriter(FILENAME);
            fileWriter.write("no;name;description;image_link;price;rating;merchant\n");
            for (int i = 0; i < models.size(); ++i) {
                Model model = models.get(i);
                fileWriter.write(String.valueOf(i+1));
                fileWriter.write(";" + model.getName());
                fileWriter.write(
                        ";\"" + model.getDescription().replace("\"", "\"\"") + "\"");
                fileWriter.write(";" + model.getImageLink());
                fileWriter.write(";" + model.getPrice());
                fileWriter.write(";" + model.getRating());
                fileWriter.write(";" + model.getMerchant() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("error while writing to csv");
            e.printStackTrace();
        }
    }
}

package org.example.crud;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.example.objects.Table;

import static org.codehaus.jackson.annotate.JsonMethod.FIELD;

public class Saver {
    public String saveFile(String path, Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            mapper.writeValue(file,obj);
            System.out.println("Файл успешно обновлён!");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "done";
    }


    public String deleteFile(String path, Table table) throws IOException {
        Path p = Path.of(path);
        String res = null;
        try {
            if (Files.deleteIfExists(p)){
                res = "Table dropped";
            };
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", p);
            res = "Failed";
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", p);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
            res = "Failed";

        }
        return res;
    }
}

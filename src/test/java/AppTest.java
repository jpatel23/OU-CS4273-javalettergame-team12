import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class AppTest{
    @Test
    public void test() {
        String str1 = "test";
        assertEquals("test", str1);
    }


    @Test
    public void EnglishTest() throws IOException {

        FileReader filereader = new FileReader("src/main/resources/lang_en.csv");
        CSVReader csvReader = new CSVReader(filereader);
    
        String res = "";
    
        for(String[] s : csvReader.readAll())
        {
          res = res.concat(Stream.of(s).collect(Collectors.joining(", "))+"\n");
        }
        String actual = App.myParser("lang_en.csv");

        assertEquals(res, actual);
    }

    @Test
    public void SpanishTest() throws IOException {

        FileReader filereader = new FileReader("src/main/resources/lang_es.csv");
        CSVReader csvReader = new CSVReader(filereader);
    
        String res = "";
    
        for(String[] s : csvReader.readAll())
        {
          res = res.concat(Stream.of(s).collect(Collectors.joining(", "))+"\n");
        }
        String actual = App.myParser("lang_es.csv");

        assertEquals(res, actual);
    }
}


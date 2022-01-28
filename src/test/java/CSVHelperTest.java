import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.appengine.springboot.CSVHelper;
import com.example.appengine.springboot.CSVHelper.CSVFile;
import com.opencsv.CSVReader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVHelperTest {

  @Test
  public void EnglishTest() {
    CSVHelper helper = new CSVHelper(CSVFile.EN);

    String capA = helper.getCell(0, 0);
    assertEquals("A", capA);

    String lowZ = helper.getCell(25, 1);
    assertEquals("z", lowZ);
  }

  @Test
  public void SpanishTest() {

    CSVHelper helper = new CSVHelper(CSVFile.ES);

    String capA = helper.getCell(0, 0);
    assertEquals("A", capA);

    String lowZ = helper.getCell(26, 1);
    assertEquals("z", lowZ);
  }

  @Test
  public void NullTest() {

    CSVHelper helper = new CSVHelper(CSVFile.EN);

    String shouldBeNull = helper.getCell(100, 100);
    assertEquals(null, shouldBeNull);
  }
}

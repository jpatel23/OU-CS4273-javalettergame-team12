/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.sun.net.httpserver.HttpServer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVReader;

public class App {

  public static void toCSV(CSVReader reader, HttpServer server, String context)
  {
    server.createContext(context, (HttpExchange r) -> {
      String fileContent = "";
      for(String [] s : reader.readAll())
      {
        fileContent = fileContent.concat(Stream.of(s).collect(Collectors.joining(", "))+"\n");
      }
      byte[] fileBytes = fileContent.getBytes();
      r.sendResponseHeaders(200, fileBytes.length);
      try(OutputStream ostream = r.getResponseBody())
      {
        ostream.write(fileBytes);
      } 
    });
  }

  public static byte[] fileReader(CSVReader reader) throws IOException
  {
      String fileContent = "";
      for(String [] s : reader.readAll())
      {
        fileContent = fileContent.concat(Stream.of(s).collect(Collectors.joining(", "))+"\n");
      }
      byte[] fileBytes = fileContent.getBytes();
      return fileBytes;
  }

  public static void main(String[] args) throws IOException {
    // Create an instance of HttpServer bound to port defined by the
    // PORT environment variable when present, otherwise on 8080.
    int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

    FileReader filereader = new FileReader("src/main/resources/lang_en.csv");
    CSVReader csvReader = new CSVReader(filereader);

    Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
    cfg.setDirectoryForTemplateLoading(new File("src/main/resources/"));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
    cfg.setLogTemplateExceptions(false);
    cfg.setWrapUncheckedExceptions(true);
    cfg.setFallbackOnNullLoopVariable(false);
    Template temp = cfg.getTemplate("index.html");

    // Set root URI path.
    server.createContext("/", (HttpExchange t) -> {
      byte[] response = "Hello World!".getBytes();
      t.sendResponseHeaders(200, response.length);
      try (OutputStream os = t.getResponseBody()) {
        os.write(response);
      }
    });

    /*server.createContext("/test", (HttpExchange t) -> {
      String res = "";

      for(String[] s : csvReader.readAll())
      {
        res = res.concat(Stream.of(s).collect(Collectors.joining(", "))+"\n");
      }

      byte[] response = res.getBytes();
      t.sendResponseHeaders(200, response.length);
      try (OutputStream os = t.getResponseBody()) {
        os.write(response);
      }
    });

    // Create a second URI path.
    server.createContext("/foo", (HttpExchange t) -> {
      byte[] response = "Foo!".getBytes();
      t.sendResponseHeaders(200, response.length);
      try (OutputStream os = t.getResponseBody()) {
        os.write(response);
      }
    });*/

    toCSV(csvReader, server, "/english");
    byte[] english = fileReader(csvReader);

    FileReader sreader = new FileReader("src/main/resources/lang_es.csv");
    CSVReader csvspanReader = new CSVReader(sreader);
    toCSV(csvspanReader, server, "/spanish");
    byte[] spanish = fileReader(csvspanReader);

    System.out.println(Arrays.toString(english));
    System.out.println(Arrays.toString(spanish));

    server.start();
  }
}

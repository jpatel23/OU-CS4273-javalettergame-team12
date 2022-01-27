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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVReader;

public class App {

  public static String myParser(String fileName) throws IOException{

    String folder = "src/main/resources/";
    String filePath = folder.concat(fileName);
    System.out.println(filePath);
    

    FileReader filereader = new FileReader(filePath);
    CSVReader csvReader = new CSVReader(filereader);

    String res = "";

    for(String[] s : csvReader.readAll())
    {
      res = res.concat(Stream.of(s).collect(Collectors.joining(", "))+"\n");
    }

    return res;

  }

  public static void main(String[] args) throws IOException {
    // Create an instance of HttpServer bound to port defined by the
    // PORT environment variable when present, otherwise on 8080.
    int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);



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

    server.createContext("/en", (HttpExchange t) -> {

      byte[] response = myParser("lang_en.csv").getBytes();
      t.sendResponseHeaders(200, response.length);
      try (OutputStream os = t.getResponseBody()) {
        os.write(response);
      }
    });

    server.createContext("/es", (HttpExchange t) -> {

      byte[] response = myParser("lang_es.csv").getBytes();
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
    });

    server.start();
  }



}

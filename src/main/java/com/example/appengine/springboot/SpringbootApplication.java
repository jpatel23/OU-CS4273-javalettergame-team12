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

package com.example.appengine.springboot;

// [START gae_java11_helloworld]
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import java.util.stream.Stream;
import java.io.File;
import java.io.FileReader;

import com.opencsv.CSVReader;

@SpringBootApplication
@RestController
public class SpringbootApplication {
  
  private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

  public static void main(String[] args) throws IOException {

    cfg.setDirectoryForTemplateLoading(new File("src/main/resources/"));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
    cfg.setLogTemplateExceptions(false);
    cfg.setWrapUncheckedExceptions(true);
    cfg.setFallbackOnNullLoopVariable(false);

    SpringApplication.run(SpringbootApplication.class, args);
  }

  @GetMapping("/")
  public String hello() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
    //Template temp = cfg.getTemplate("index.html");
    
    return "<h1>Hello world!</h1>";
  }

  private static List<String[]> parseCSVFile(String fileName) throws IOException {

    String folder = "src/main/resources/";
    String filePath = folder.concat(fileName);

    FileReader filereader = new FileReader(filePath);
    CSVReader csvReader = new CSVReader(filereader);

    List<String[]> result = csvReader.readAll();

    csvReader.close();

    return result;
  }

}
// [END gae_java11_helloworld]

package com.example.ankieter.utilities;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;

public class Headers extends HttpHeaders {

  private List<String> exposedHeaders = Arrays.asList("Accept", "Access-Control-Allow-Headers",
      "Access-Control-Allow-Methods",
      "Access-Control-Allow-Origin", "Content-Type");

  public Headers() {
    this.add("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
    this.add("Access-Control-Allow-Methods", "*");
    this.add("Access-Control-Allow-Origin", "*");
    this.add("Content-Type", "application/json");
    this.add("Access-Control-Allow-Credentials", "true");
    this.setAccessControlExposeHeaders(this.exposedHeaders);
  }

  public Headers(String origin) {
    this.add("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
    this.add("Access-Control-Allow-Methods", "*");
    this.add("Content-Type", "application/json");
    this.add("Access-Control-Allow-Origin", origin);
    this.add("Access-Control-Expose-Headers",
        "Access-Control-Allow-Headers, Access-Control-Allow-Methods, Access-Control-Allow-Origin, Content-Type");
    this.add("Access-Control-Allow-Credentials", "true");
    this.setAccessControlExposeHeaders(this.exposedHeaders);
  }
}

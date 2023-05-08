package com.wasupstudio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

  @GetMapping("/form")
  public String showForm() {
    System.out.println("form in");
    return "form";
  }
}

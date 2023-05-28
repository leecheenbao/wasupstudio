package com.wasupstudio.controller;

import com.wasupstudio.model.CashFlowReturnData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FormController {

  @GetMapping("/form2")
  public String showForm(Model model) {
    System.out.println("form2 in");
    model.addAttribute("urlStr", "http://localhost:8080/wasupstudio/api/cash/order");
    model.addAttribute("tokenValue", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGV4YW1wbGUuY29tIiwibWVtYmVySW5mbyI6eyJpZCI6MSwiZW1haWwiOiJleGFtcGxlQGV4YW1wbGUuY29tIiwicHdkIjoiMTdGMTQ3MDNGRkE4NTI2MTZERDRCMjZFRTAzMzQ5M0IiLCJyb2xlIjoiUk9MRV9BRE1JTiIsIm5hbWUiOiJKb2huIERvZSIsInBob25lIjoiMTIzNDU2Nzg5MCIsImJpcnRoZGF5Ijo5NDY2NTYwMDAwMDAsIm9yZ2FuaXphdGlvbiI6IkFDTUUgSW5jLiIsImdyYWRlIjo5NSwicmVnaXN0aW9uVGltZSI6MTY1MDY0MzIwMDAwMCwic3RhdHVzIjoxLCJsYXN0SXAiOiIxOTIuMTY4LjEuMTIzIiwibGFzdExvZ2luIjoxNjg0MzAxODQyMTEzfSwiaXNzIjoic2VjdXJpdHkiLCJpYXQiOjE2ODQzMDE4NDIsImF1ZCI6InNlY3VyaXR5LWFsbCIsImV4cCI6MTY4NDkwNjY0Mn0.ZJDYKnbcmmVijf01WDURpKtbNkj34QNNXLX2qNRI30s");
    model.addAttribute("jsonData", "{\n" +
        "  \"products\": [\n" +
        "    {\n" +
        "      \"productId\": \"1\",\n" +
        "      \"quantity\": 1\n" +
        "    }\n" +
        "  ],\n" +
        "  \"recipient\": \"John Doe\",\n" +
        "  \"phone\": \"1234567890\",\n" +
        "  \"address\": \"123 Main Street\"\n" +
        "}");
    return "form2";
  }

  @GetMapping("/form")
  public String showForm2(Model model) {
    System.out.println("form in");
    return "form";
  }

  @PostMapping("/form-success")
  public String formSuccess(@RequestBody CashFlowReturnData cashFlowReturnData) {
    System.out.println("form success");
    System.out.println("cashFlowReturnData = " + cashFlowReturnData);
    return "form";
  }
}

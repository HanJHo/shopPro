package com.example.shoppro.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ItemController {
    // 이전 boardController 라고 생각하면 됨

    @GetMapping("/admin/item/new")
    public String itemForm(){

        return "/item/itemForm";
    }
}

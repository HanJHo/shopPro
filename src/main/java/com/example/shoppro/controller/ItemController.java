package com.example.shoppro.controller;

import com.example.shoppro.dto.ItemDTO;
import com.example.shoppro.dto.PageRequestDTO;
import com.example.shoppro.dto.PageResponseDTO;
import com.example.shoppro.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2

public class ItemController {
    // 이전 boardController 라고 생각하면 됨

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model, Principal principal){
        if(principal == null) {
            // 로그인이 안되어있다. 그래서 못들어온다
//            return "redirect:/";
        }
        if(principal != null){
            log.info("현재 로그인 한 사람");
            log.info(principal.getName());
        }
        model.addAttribute("itemDTO", new ItemDTO());

        return "/item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String itemFormPost(@Valid ItemDTO itemDTO, BindingResult bindingResult, List<MultipartFile> multipartFile, Model model){
        // 들어오는값 확인
        log.info("들어오는값 확인" + itemDTO);

        if(multipartFile != null){

            for(MultipartFile img : multipartFile){
                if(!img.getOriginalFilename().equals("")){
                    log.info(img.getOriginalFilename());
                }
            }
        }

        if(bindingResult.hasErrors()){
            log.info("유효성검사 에러");
            log.info(bindingResult.getAllErrors()); // 확인된 모든 에러 콘솔창 출력

            return "/item/itemForm";    // 다시 이전 페이지
        }


        try {
            Long saveItemid =
            itemService.saveItem(itemDTO, multipartFile);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("파일등록간 문제가 발생했습니다.");
            model.addAttribute("msg" , "파일등록을 잘해주세요");
            return "/item/itemForm";        //다시 이전 페이지
        }

        log.info("상품등록됨!!!");
        log.info("상품등록됨!!!");
        log.info("상품등록됨!!!");

        return "redirect:/admin/item/new";
    }

    @GetMapping("/admin/item/read")
    public String adminread(Long id, Model model, RedirectAttributes redirectAttributes){

        try {
            log.info("잘 불러오는가?" + id);
            ItemDTO itemDTO =
                    itemService.read(id);

            log.info("잘 불러오는가?" + itemDTO);
            model.addAttribute("itemDTO",itemDTO);

            return "item/read";

        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("msg", "존재하지 않는 상품입니다");
            return "redirect:/admin/item/list";
        }


    }

    @GetMapping("/admin/item/list")
    public String adminlist(PageRequestDTO pageRequestDTO, Model model, Principal principal){

//        model.addAttribute("list", itemService.list());

        PageResponseDTO<ItemDTO> pageResponseDTO =
        itemService.list(pageRequestDTO, principal.getName());

        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "item/list";

    }


}

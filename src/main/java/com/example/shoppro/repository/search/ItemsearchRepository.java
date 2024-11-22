package com.example.shoppro.repository.search;

import com.example.shoppro.dto.PageRequestDTO;
import com.example.shoppro.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemsearchRepository {
    //검색과 페이징처리를 하는 목록기능

    //Pageable a = PageRequest.of(0, 10, Sort.by(" 정렬값"));


    Page<Item> getAdminItemPage(PageRequestDTO pageRequestDTO, Pageable pageable, String email);




}

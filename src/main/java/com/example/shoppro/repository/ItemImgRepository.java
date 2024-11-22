package com.example.shoppro.repository;

import com.example.shoppro.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    public List<ItemImg> findByItemId (Long id);

}

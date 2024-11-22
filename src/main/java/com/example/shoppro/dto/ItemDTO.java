package com.example.shoppro.dto;

import com.example.shoppro.constant.ItemSellStatus;
import com.example.shoppro.entity.ItemImg;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private Long id;            // 상품 코드

    @NotBlank
    @Size(max = 50, min = 2, message = "상품명은 2~50자 입니다")
    private String itemNm;      // 상품명

    @NotNull
    @PositiveOrZero
    private int price;          // 가격

    @NotNull
    @PositiveOrZero
    private int stockNumber;    // 재고수량

    @NotBlank
    private String itemDetail;  // 상품 상세 설명

    private ItemSellStatus itemSellStatus;  // 상품 판매 상태

    private List<ItemImgDTO> itemImgDTOList;

    public ItemDTO setItemImgDTOList(List<ItemImg> itemImgList) {

        ModelMapper modelMapper = new ModelMapper();

        List<ItemImgDTO> itemImgDTOS =
        itemImgList.stream().map(
                itemImg -> modelMapper.map(itemImg, ItemImgDTO.class)
        ).collect(Collectors.toList());

        this.itemImgDTOList = itemImgDTOList;

        return this;
    }
}

package com.example.shoppro.entity;

import com.example.shoppro.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "item_id")   // 테이블에서 매핑될 컬럼
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm;      // 상품명

    @Column(name = "price", nullable = false)
    private int price;          // 가격

    @Column(nullable = false)
    private int stockNumber;    // 재고수량

    @Lob    // 텍스트 많이
    @Column(nullable = false)
    private String itemDetail;  // 상품 상세 설명

    @Enumerated(EnumType.STRING)        // enum 가지고 만듬 YES/NO, SELL/SOLD_OUT
    private ItemSellStatus itemSellStatus;  // 상품 판매 상태

    private LocalDateTime regTime;      // 상품 등록시간

    private LocalDateTime updateTime;   // 상품 수정시간


}

package com.example.shoppro.repository;

import com.example.shoppro.constant.ItemSellStatus;
import com.example.shoppro.entity.Item;
import com.example.shoppro.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Log4j2
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("양방향 테스트")
    @Transactional
    public void selectItem(){

        // 필요한값 부모 id 401L
        // 실행내용 부모를 item 을 검색한다. 특정 pk 값을 가지고
        Item item =
        itemRepository.findById(415L).get();

        // 결과 예상 부모를 검색하면 부모와 + 자식의 모든 데이터를 받는다.

        log.info(item);
        log.info("아이템명" + item.getItemNm());
        log.info("아이템 이미지" + item.getItemImgList().get(0).getImgUrl());
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){

        for(int i = 0; i < 200; i++){
            Item item =
                    Item.builder()
                            .itemNm("테스트상품")
                            .price(10000)
                            .itemDetail("테스트상품 상세설명")
                            .itemSellStatus(ItemSellStatus.SELL)
                            .stockNumber(100)
//                            .regTime(LocalDateTime.now())
//                            .updateTime(LocalDateTime.now())
                            .build();

            item.setItemNm(item.getItemNm() + i);
            item.setItemDetail(item.getItemDetail() + i);
            item.setPrice(item.getPrice() + i);

            Item item1 =
            itemRepository.save(item);
            log.info(item1);
        }

    }

    @Test
    @DisplayName("제품명으로 검색 2가지에서 다시 2가지 검색")
    public void findByItemNmTest(){

        List<Item> itemListA =
                itemRepository.findByItemNm("테스트상품1");

        itemListA.forEach(item -> log.info(item));
        System.out.println("-------------------");

        itemListA =
                itemRepository.selectwhereItemNm("테스트상품2");
        itemListA.forEach(item -> log.info(item));
        System.out.println("-------------------");

        itemListA =
                itemRepository.findByItemNmContaining("테스트");
        itemListA.forEach(item -> log.info(item));
        System.out.println("-------------------");

        itemListA =
                itemRepository.selectWItemNmLike("1");
        itemListA.forEach(item -> log.info(item));
        System.out.println("-------------------");

    }

    @Test
    public void priceSearchtest(){
        // 가격으로 검색 테스트
        // 사용자가 검색창에 혹은 검색이 가능하도록 만들어놓은 곳에 값을 입력한다.
        // 이 조건에 부합하는 아이템 리스트 검색
        Integer price = 10020;

        List<Item> itemList =
        itemRepository.findByPriceLessThan(price);
        for(Item item : itemList) {
            log.info(item);
            log.info("상품명 : " + item.getItemNm());
            log.info("상품가격 : " + item.getPrice());
            log.info("상품 상세설명 : " + item.getItemDetail());
        }

        List<Item> itemListA =
                itemRepository.findByPriceGreaterThan(10020);
        for(Item item : itemListA) {
            log.info(item);
            log.info("상품명 : " + item.getItemNm());
            log.info("상품가격 : " + item.getPrice());
            log.info("상품 상세설명 : " + item.getItemDetail());
        }

        List<Item> itemListB =
                itemRepository.findByPriceGreaterThanOrderByPriceAsc(10020);
        for(Item item : itemListB) {
            log.info(item);
            log.info("상품명 : " + item.getItemNm());
            log.info("상품가격 : " + item.getPrice());
            log.info("상품 상세설명 : " + item.getItemDetail());
        }

    }

    @Test
    @DisplayName("페이징 추가까지")
    public void findbypricegreaterThanEqualTest(){
        Pageable pageable = PageRequest
                .of(0, 5, Sort.by("id").ascending());
                                    // sort.by 의 정렬조건은 entity 의 변수명이다. (item_id 로 쓰면 오류)

        List<Item> itemList =
        itemRepository.findByPriceGreaterThanEqual(10020, pageable);

        itemList.forEach(item -> log.info(item));

    }

    @Test
    public void nativeQueryTest(){

        Pageable pageable = PageRequest
                .of(0, 5, Sort.by("price").descending());
        String itemNm = "테스트상품1";
        List<Item> itemList =
        itemRepository.nativeQuerySelectWhereNamelike(itemNm, pageable);
        itemList.forEach(item -> log.info(item));

    }

    @Test
    public void queryDslTest(){

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QItem qItem = QItem.item;
        // select * from item
        String keyword = null;
        ItemSellStatus itemSellStatus = ItemSellStatus.SELL;

        JPAQuery<Item> query =
                queryFactory.selectFrom(qItem)
                        .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                        .where(qItem.itemDetail.like("%" + keyword + "%"))
                        .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for(Item item : itemList){
            System.out.println(item.getItemNm());
        }

    }

    @Test
    public void queryDslTestB1(){

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QItem qItem = QItem.item;
        // select * from item
        // 상품 검색 조건 입력 값
        String keyword = "1";
        ItemSellStatus itemSellStatus = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(keyword != null){
            booleanBuilder.or(qItem.itemDetail.like("%" + keyword + "%"));
        }
        if(itemSellStatus != null){
            if(itemSellStatus == ItemSellStatus.SELL) {
                booleanBuilder.or(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
            } else {
                booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SOLD_OUT));
            }

        }

        JPAQuery<Item> query =
                queryFactory.selectFrom(qItem)
                        .where(booleanBuilder)
                        .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for(Item item : itemList){
            System.out.println(item.getItemNm());
        }
    }


}
package com.example.shoppro.repository.search;

import com.example.shoppro.dto.PageRequestDTO;
import com.example.shoppro.entity.Item;
import com.example.shoppro.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

public class ItemsearchRepositoryImpl extends QuerydslRepositorySupport implements ItemsearchRepository {
    public ItemsearchRepositoryImpl(){
        super(Item.class);
    }

    @Override
    public Page<Item> getAdminItemPage(PageRequestDTO pageRequestDTO, Pageable pageable, String email) {

        // 만들 기능들: 판매중 품절여부, 날짜에 따른 검색, 만든이(중요), 아이템 이름
        // select * from item where reg = ww;




        QItem item = QItem.item;        // q 도메인 객체 entity를 QItem로 바꾼것

        JPQLQuery<Item> query = from(item);  //select * from item

        System.out.println(query); //select * from item
        System.out.println("-------------------------");
        // types에 있는 값을 검색하는데 있을때 없을때에 따라 동적으로 쿼리문을
        // 작성하고 싶다.
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        String[] types = pageRequestDTO.getTypes();     // 들어온 검색타입
        String keyword =pageRequestDTO.getKeyword();    // 들어온 키워드
        String searchDateType = pageRequestDTO.getSearchDateType(); // 들어온 날짜분류
        LocalDateTime localDateTime = LocalDateTime.now();  // 현재시간


        if(pageRequestDTO.getSearchDateType() == null || pageRequestDTO.getSearchDateType().equals("all") || pageRequestDTO.getSearchDateType().equals("")){

        } else if(searchDateType.equals("1d")){
            booleanBuilder.and(item.regTime.after(localDateTime.minusDays(1)));
        } else if(searchDateType.equals("1w")){
            booleanBuilder.and(item.regTime.after(localDateTime.minusWeeks(1)));
        } else if(searchDateType.equals("1m")){
            booleanBuilder.and(item.regTime.after(localDateTime.minusMonths(1)));
        } else if(searchDateType.equals("6m")){
            booleanBuilder.and(item.regTime.after(localDateTime.minusMonths(6)));
        }

        System.out.println("===========================");
        System.out.println(query);

        if( types != null && types.length > 0 && keyword != null){
            for(String str  : types){
//                if(str.equals("t"))
                switch (str){
                    case "n" :          // 상품명
                        booleanBuilder.or(item.itemNm.contains(keyword));
                        break;
                    case "d" :          // 상품상세설명
                        booleanBuilder.or(item.itemDetail.contains(keyword));
                        break;
//                    case "c" :          // createBy
//                        booleanBuilder.or(item.createBy.contains(keyword));
//                        break;

                }// swich
                //만약에 tc가 들어왔다면 where문 이후  title like %키워드% or content like %키워드%

            }//for문

        }// it문
        //검색조건까지
        query.where(booleanBuilder); //검색조건완료
        System.out.println(query);
        System.out.println("----------------------------");

        query.where(item.id.gt(0L));   // select * from board //   // board.bno > 0
        query.where(item.createBy.eq(email));   // select * from board // 현재 판매자

        System.out.println(query);
        System.out.println("----------------------------");

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);
        //데이터 List<T>
        List<Item> boardList = query.fetch();

        // 총게시물수 row수
        long count =
                query.fetchCount();

        //return
        return new PageImpl<>(boardList, pageable, count);

    }
}

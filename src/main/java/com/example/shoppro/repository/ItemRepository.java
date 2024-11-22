package com.example.shoppro.repository;

import com.example.shoppro.entity.Item;
import com.example.shoppro.repository.search.ItemsearchRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemsearchRepository {

    // 제품명으로 검색 제품명은 동일한 이름이 있을 수 있으니
    // 여러 개 출력가능 List 사용

    public List<Item> findByItemNm (String itemNm);

    @Query("select i from Item i where i.itemNm = :itemNm")
    public List<Item> selectwhereItemNm (String itemNm);

    // 제품명으로 검색, 비슷하면 맞게
    public List<Item> findByItemNmContaining (String itemNm);

    @Query("select i from Item i where i.itemNm like concat('%', :itemNm, '%')")
    public List<Item> selectWItemNmLike (String itemNm);

    // 상세설명으로 검색
    public List<Item> findByItemDetailContaining (String itemDetail);

    // 가격으로 검색
    public List<Item> findByPriceLessThan (Integer price);  // 미만

    public List<Item> findByPriceLessThanEqual (Integer price); // 이하

    public List<Item> findByPriceGreaterThan (Integer price);   // 초과

    public List<Item> findByPriceGreaterThanOrderByPriceAsc (Integer price);   // 초과인데 오름차순정렬

    public List<Item> findByPriceGreaterThanEqual (Integer price, Pageable pageable);   // 이상이면서 페이징처리

    // 정렬까지 추가
    List<Item> findByPriceLessThanOrderByPriceDesc (Integer price);

    // nativeQuery 사용
    @Query(value = "select * from Item i where i.item_nm = :itemNm", nativeQuery = true)
    List<Item> nativeQuerySelectWhereNamelike(String itemNm, Pageable pageable);
}

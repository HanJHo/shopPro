package com.example.shoppro.service;

import com.example.shoppro.dto.MemberDTO;
import com.example.shoppro.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){

        MemberDTO memberDTO = MemberDTO.builder()
                .address("경남 사이타마현")
                .email("sin@a.a")
                .password("1234")
                .name("신짱구")
                .build();

        try {
            Member member =
                    memberService.saveMember(memberDTO);
            log.info(member);

        } catch (IllegalStateException e){
            log.info(e.getMessage());
        }

    }




}
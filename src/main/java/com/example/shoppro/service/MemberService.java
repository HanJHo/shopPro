package com.example.shoppro.service;

import com.example.shoppro.dto.MemberDTO;
import com.example.shoppro.entity.Member;
import com.example.shoppro.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service        // 비즈니스 로직을 담당하는 서비스 계층 클래스에
@Transactional  // 이 어노테이션을 선언합니다. 로직을 처리하다가
                // 에러가 발생하였다면 변경된 데이터로직을 수행하기
                // 이전 상태로 롤백 시켜줍니다.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    // 빈 주입하는 방법으로 @Autowired 어노테이션을 사용하거나 필드주입
    // setter 를 사용하거나 생성자를 새로 만들어서 주입하는 방법 등이 있다.
    // @RequiredArgsConstructor 어노테이션은 final 이나
    // @NonNull 이 붙은 필드에 생성자를 생성해줌 빈이라서 new~~ 이거 생성자
    // 빈이 생성자가 1개이고 생성자의 파라미터 타입이 빈등록이 가능하다면
    // 어노테이션 없이 의존성 주입이 가능

    // 회원가입
    public Member saveMember(MemberDTO memberDTO){

        return null;
    }

    // 회원가입시 회원 가입여부 확인하는 메소드

}

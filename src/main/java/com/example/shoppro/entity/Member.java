package com.example.shoppro.entity;

import com.example.shoppro.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String address;

    // 권한 2개
    @Enumerated(EnumType.STRING)
    private Role role;

}

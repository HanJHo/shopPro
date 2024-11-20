package com.example.shoppro.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    // 만든이
    @Column(updatable = false)
    @CreatedBy
    private String createBy;

    // 수정한이
    @LastModifiedBy
    private String modifiedBy;

    // 만든날짜
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    // 수정날짜
    @LastModifiedDate
    private LocalDateTime updateTime;

}

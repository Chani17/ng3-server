package com.nggg.ng3.repository;

import com.nggg.ng3.entity.Wearing;
import com.nggg.ng3.entity.WearingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WearingRepository extends JpaRepository<Wearing, WearingId> {

    /** [박혁진] : User 엔티티의 email을 기준으로 Wearing 엔티티를 찾는 쿼리*/
    List<Wearing> findByUserId_Email(String email);

    /** [박혁진] : User 엔티티의 email 필드를 기준으로 삭제 쿼리 작성*/
    void deleteByUserId_Email(String email);
}
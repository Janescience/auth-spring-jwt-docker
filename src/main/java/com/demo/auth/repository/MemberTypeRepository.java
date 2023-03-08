package com.demo.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.auth.entity.app.MemberType;
import com.demo.auth.entity.enums.EMemberType;

@Repository
public interface MemberTypeRepository extends JpaRepository<MemberType,Long>{
    MemberType findByName(EMemberType name);
}

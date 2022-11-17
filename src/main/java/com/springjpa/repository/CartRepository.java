package com.springjpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springjpa.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

        Cart findByid(Long id);

        @Query("FROM cart WHERE id = :id AND custid = :custid")
        Cart findByIdAndCustid(@Param("id") Long id, @Param("custid") Long custid);

}

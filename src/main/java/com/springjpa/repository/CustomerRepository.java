package com.springjpa.repository;
import com.springjpa.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByid(Long id);

    //MVP #2 filter number Cust Id and Cart Number
    //@Query("FROM customer ca, cart ca WHERE cu.id = :id AND cartnumber = :cartnumber")
    //Customer findByIdAndCartnumber(@Param("id") Long id, @Param("cartnumber") Long cartnumber);



}


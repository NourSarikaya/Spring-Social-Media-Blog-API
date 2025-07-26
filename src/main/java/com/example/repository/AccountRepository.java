package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.example.entity.Account;

//Replaces AccountDAO
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{

    //Custom method(Named QUERY)
    //If a user with the given username does not exist will return Optional.empty()
    //Account findByUsername(String username);//Spring Boot will automatically implement this method
    //Can only include one Custom Method in this Repository otherwise I get an error

    @Query("FROM Account WHERE username= :username")
    Account findByUsername(@Param("username") String username);


}


/** REFERENCE(Named QUERY)
 * public interface GroceryRepository extends JpaRepository<Grocery, Long> {
 */

/** REFERENCE
 * USING SPRING QUERY
 * As you can see here, JPQL follows the same structure as you've already seen in SQL clauses, including where
 * and/or, order by, group by, limit, as well as subqueries.
 *
 * @param name the name of the pet.
 * @return All pets with the name provided as the parameter.
 *
 * @Query("FROM Pet WHERE name = :nameVar AND species = :speciesVar")
 *List<Pet> example2(@Param("nameVar") String name, @Param("speciesVar") String species);
 */
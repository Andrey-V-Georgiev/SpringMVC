package org.softuni.residentevil.repositories;

import org.softuni.residentevil.domain.entities.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQueries;
import javax.transaction.Transactional;
import java.util.List;
import java.util.StringJoiner;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, String> {

    @Query("SELECT c FROM Capital c ORDER BY c.name")
    List<Capital> findAllOrderByName();

    @Modifying
    @Query(value = "INSERT into capitals (id, name, longitude, latitude) VALUES (51,'Havana',23.13,-82.38), " +
            "(200,'Pretoria',-25.74,28.19),(227,'Kiev',50.45,30.52),(99,'Dublin',53.33,-6.25)," +
            "(77,'Berlin',52.52,13.41),(96,'Jakarta',-6.21,106.85),(139,'Monaco',43.73,7.42)", nativeQuery = true)
    @Transactional
    void seedCapitals();

}

package com.usta_backend.repository;

import com.usta_backend.model.Ad;
import com.usta_backend.model.Ad.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.usta_backend.model.Ad;
import com.usta_backend.model.Ad.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAllByStatus(Ad.Status status);

    @Query(
            value = """
              SELECT *
              FROM ads a
              WHERE a.status = :status
                AND (:title    IS NULL OR a.title ILIKE ('%' || :title || '%'))
                AND (:category IS NULL OR a.category = :category)
                AND (:city     IS NULL OR a.city = :city)
                AND (:priceMin IS NULL OR a.price >= :priceMin)
                AND (:priceMax IS NULL OR a.price <= :priceMax)
              """,
            countQuery = """
              SELECT COUNT(*)
              FROM ads a
              WHERE a.status = :status
                AND (:title    IS NULL OR a.title ILIKE ('%' || :title || '%'))
                AND (:category IS NULL OR a.category = :category)
                AND (:city     IS NULL OR a.city     = :city)
                AND (:priceMin IS NULL OR a.price   >= :priceMin)
                AND (:priceMax IS NULL OR a.price   <= :priceMax)
              """,
            nativeQuery = true
    )
    Page<Ad> searchNative(
            @Param("status")   String status,      // <-- строка!
            @Param("title")    String title,
            @Param("category") String category,
            @Param("city")     String city,
            @Param("priceMin") java.math.BigDecimal priceMin,
            @Param("priceMax") java.math.BigDecimal priceMax,
            Pageable pageable
    );
}



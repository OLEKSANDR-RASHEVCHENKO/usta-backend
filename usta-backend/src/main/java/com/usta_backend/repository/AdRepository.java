package com.usta_backend.repository;

import com.usta_backend.model.Ad;
import com.usta_backend.model.Ad.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllByStatus(Status status);
}

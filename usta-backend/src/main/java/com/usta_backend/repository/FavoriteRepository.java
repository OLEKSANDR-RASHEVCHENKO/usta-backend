package com.usta_backend.repository;

import com.usta_backend.model.Favorite;
import com.usta_backend.model.User;
import com.usta_backend.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserAndAd(User user, Ad ad);
    List<Favorite> findAllByUser_Email(String email);
}

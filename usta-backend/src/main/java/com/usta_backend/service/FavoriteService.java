package com.usta_backend.service;

import com.usta_backend.dto.AdDto;
import com.usta_backend.mapper.AdMapper;
import com.usta_backend.model.*;
import com.usta_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    public void add(Long adId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Ad ad = adRepository.findById(adId).orElseThrow();
        favoriteRepository.findByUserAndAd(user, ad)
                .orElseGet(() -> favoriteRepository.save(Favorite.builder().user(user).ad(ad).build()));
    }

    public void remove(Long adId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Ad ad = adRepository.findById(adId).orElseThrow();
        favoriteRepository.findByUserAndAd(user, ad)
                .ifPresent(favoriteRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<AdDto> list(String userEmail) {
        return favoriteRepository.findAllByUser_Email(userEmail)
                .stream().map(Favorite::getAd)
                .map(adMapper::toDto)
                .toList();
    }
}


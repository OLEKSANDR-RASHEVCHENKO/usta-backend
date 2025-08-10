package com.usta_backend.service;

import com.usta_backend.dto.AdDto;
import com.usta_backend.dto.CreateAdRequest;
import com.usta_backend.mapper.AdMapper;
import com.usta_backend.model.Ad;
import com.usta_backend.model.User;
import com.usta_backend.repository.AdRepository;
import com.usta_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;

    public AdService(AdRepository adRepository, UserRepository userRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.adMapper = adMapper;
    }

    // Публичный список активных объявлений
    @Transactional(readOnly = true)
    public List<AdDto> listActive() {
        return adRepository.findAllByStatus(Ad.Status.ACTIVE)
                .stream().map(adMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AdDto getById(Long id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return adMapper.toDto(ad);
    }


    public AdDto create(CreateAdRequest req, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail).orElseThrow();
        Ad ad = Ad.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .price(req.getPrice())
                .category(req.getCategory())
                .city(req.getCity())
                .status(Ad.Status.ACTIVE)
                .owner(owner)
                .build();
        return adMapper.toDto(adRepository.save(ad));
    }
}


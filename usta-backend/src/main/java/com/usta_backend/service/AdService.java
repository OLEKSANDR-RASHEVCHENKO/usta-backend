package com.usta_backend.service;

import com.usta_backend.dto.AdDto;
import com.usta_backend.dto.CreateAdRequest;
import com.usta_backend.dto.UpdateAdRequest;
import com.usta_backend.mapper.AdMapper;
import com.usta_backend.model.Ad;
import com.usta_backend.model.Role;
import com.usta_backend.model.User;
import com.usta_backend.repository.AdRepository;
import com.usta_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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

    @Transactional
    public AdDto update(Long id, UpdateAdRequest req, String editorEmail) throws AccessDeniedException {
        Ad ad = adRepository.findById(id).orElseThrow();
        User editor = userRepository.findByEmail(editorEmail).orElseThrow();

        boolean isOwner = ad.getOwner().getEmail().equals(editorEmail);
        boolean isAdmin = editor.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Only owner or admin can update this ad");
        }

        // Патч-обновление: применяем только непустые поля
        if (req.getTitle() != null) ad.setTitle(req.getTitle());
        if (req.getDescription() != null) ad.setDescription(req.getDescription());
        if (req.getPrice() != null) ad.setPrice(req.getPrice());
        if (req.getCategory() != null) ad.setCategory(req.getCategory());
        if (req.getCity() != null) ad.setCity(req.getCity());

        if (req.getStatus() != null) {
            if (!isAdmin) throw new AccessDeniedException("Only admin can change status");
            ad.setStatus(Ad.Status.valueOf(req.getStatus())); // следи, чтобы строка была валидной
        }

        adRepository.save(ad);
        return adMapper.toDto(ad);
    }

    @Transactional
    public void delete(Long id, String actorEmail) throws AccessDeniedException {
        Ad ad = adRepository.findById(id).orElseThrow();
        User actor = userRepository.findByEmail(actorEmail).orElseThrow();

        boolean isOwner = ad.getOwner().getEmail().equals(actorEmail);
        boolean isAdmin = actor.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Only owner or admin can delete this ad");
        }

        adRepository.delete(ad);
    }
}


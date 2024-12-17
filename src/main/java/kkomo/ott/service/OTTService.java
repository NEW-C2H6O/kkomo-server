package kkomo.ott.service;

import kkomo.ott.domain.OTT;
import kkomo.ott.repository.OTTRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OTTService {

    private final OTTRepository ottRepository;

    public List<OTT> readAll() {
        return ottRepository.findAll();
    }
}

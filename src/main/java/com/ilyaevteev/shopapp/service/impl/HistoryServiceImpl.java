package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.History;
import com.ilyaevteev.shopapp.repository.HistoryRepository;
import com.ilyaevteev.shopapp.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryRepository historyRepository;

    @Override
    @Transactional
    public void saveHistory(History history) {
        historyRepository.saveHistory(history);
    }

    @Override
    @Transactional
    public History findById(Long id) {
        return historyRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        historyRepository.deleteById(id);
    }

}

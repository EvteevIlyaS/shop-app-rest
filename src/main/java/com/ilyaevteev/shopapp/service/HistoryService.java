package com.ilyaevteev.shopapp.service;


import com.ilyaevteev.shopapp.model.History;

public interface HistoryService {
    void saveHistory(History history);

    History findById(Long id);

    void delete(Long id);


}

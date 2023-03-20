package com.ilyaevteev.shopapp.repository;


import com.ilyaevteev.shopapp.model.History;

public interface HistoryRepository {
    public void saveHistory(History history);

    public History findById(Long id);

    void deleteById(Long id);


}

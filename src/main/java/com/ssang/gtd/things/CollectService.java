package com.ssang.gtd.things;

import java.util.List;

public interface CollectService {
    List<CollectionDto> list();
    CollectionDto get(int id);
    int post(String content);
    int put(CollectionDto dto);
    int delete(int id);
}

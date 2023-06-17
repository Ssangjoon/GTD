package com.ssang.gtd.domain.things.dao;

import com.ssang.gtd.domain.things.domain.Collect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectRepository  extends JpaRepository<Collect, Long> {
}

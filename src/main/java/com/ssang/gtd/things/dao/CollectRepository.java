package com.ssang.gtd.things.dao;

import com.ssang.gtd.things.domain.Collect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectRepository  extends JpaRepository<Collect, Long> {
}

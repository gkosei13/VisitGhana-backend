package com.visitghana.api.repository;

import com.visitghana.api.model.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FestivalRepository
        extends JpaRepository<Festival, Long> {
}
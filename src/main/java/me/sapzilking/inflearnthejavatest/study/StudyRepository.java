package me.sapzilking.inflearnthejavatest.study;

import me.sapzilking.inflearnthejavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}

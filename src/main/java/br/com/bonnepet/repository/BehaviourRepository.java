package br.com.bonnepet.repository;

import br.com.bonnepet.domain.Behaviour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BehaviourRepository extends JpaRepository<Behaviour, Integer> {

    List<Behaviour> findAllByNameIn(List<String> behaviourList);

}

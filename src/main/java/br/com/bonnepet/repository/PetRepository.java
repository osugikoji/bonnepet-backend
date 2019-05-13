package br.com.bonnepet.repository;

import br.com.bonnepet.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findAllByIdIn(List<Integer> petIds);

}

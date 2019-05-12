package br.com.bonnepet.repository;

import br.com.bonnepet.domain.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

    List<Size> findAllByNameIn(List<String> sizeList);

}

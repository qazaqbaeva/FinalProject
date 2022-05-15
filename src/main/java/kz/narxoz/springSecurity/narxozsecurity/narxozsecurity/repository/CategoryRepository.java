package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.repository;


import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.Brand;
import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

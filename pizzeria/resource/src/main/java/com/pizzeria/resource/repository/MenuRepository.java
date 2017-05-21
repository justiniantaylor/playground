package com.pizzeria.resource.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pizzeria.resource.domain.Menu;

/**
 * JPA Repository for the Menu domain entity.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @see Menu
 */
public interface MenuRepository extends CrudRepository<Menu, Long> {

	/**
     * Finds the existing menus for a date exlcuding a specific menu id.
     *
     * @param menuDate  the date used to find the existing menus as at requested date
     * @param excludeId the menu id to exclude from the find
     * @return          the list of existing menus for the requested date
     * @see				Menu
     */
	@Query("SELECT menu FROM Menu menu WHERE menu.id != :excludeId AND (menu.startDate <= :menuDate AND menu.endDate >= :menuDate OR menu.endDate is null)")
	Optional<List<Menu>> findExistingMenuForDateExcludingId(@Param("menuDate") LocalDate menuDate, @Param("excludeId") Long excludeId);
	
	/**
     * Finds the existing menus for a date
     *
     * @param menuDate  the date used to find the existing menus as at requested date
     * @return          the list of existing menus for the requested date
     * @see				Menu
     */
	@Query("SELECT menu FROM Menu menu WHERE menu.startDate <= :menuDate AND menu.endDate >= :menuDate OR menu.endDate is null")
	Optional<List<Menu>> findExistingMenuForDate(@Param("menuDate") LocalDate menuDate);
}

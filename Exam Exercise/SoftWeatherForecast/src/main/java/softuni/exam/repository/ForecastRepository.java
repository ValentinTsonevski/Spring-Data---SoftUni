package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.DaysOfWeek;
import softuni.exam.models.entity.Forecast;

import java.util.List;
import java.util.Optional;
import java.util.Set;

// TODO:
@Repository
public interface ForecastRepository extends JpaRepository<Forecast,Integer> {
Optional<Set<Forecast>> findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DaysOfWeek dayOfWeek, Long population);
}

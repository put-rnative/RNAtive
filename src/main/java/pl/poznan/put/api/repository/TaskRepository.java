package pl.poznan.put.api.repository;

import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.poznan.put.api.model.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
  @Transactional
  @Modifying
  @Query("DELETE FROM Task t WHERE t.createdAt < :cutoff")
  void deleteTasksOlderThan(Instant cutoff);

  @Transactional
  @Modifying
  @Query(
      value =
          "DELETE FROM model_svgs WHERE task_id IN (SELECT id FROM task WHERE created_at < :cutoff)",
      nativeQuery = true)
  void deleteModelSvgsOlderThan(Instant cutoff);

  @Transactional
  @Modifying
  @Query(
      value =
          "DELETE FROM removal_reasons WHERE task_id IN (SELECT id FROM task WHERE created_at < :cutoff)",
      nativeQuery = true)
  void deleteRemovalReasonsOlderThan(Instant cutoff);

  @Transactional
  @Modifying
  @Query(
      value =
          "DELETE FROM molprobity_responses WHERE task_id IN (SELECT id FROM task WHERE created_at < :cutoff)",
      nativeQuery = true)
  void deleteMolprobityResponsesOlderThan(Instant cutoff);
}

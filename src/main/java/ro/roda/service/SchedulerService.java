package ro.roda.service;

import java.util.Date;
import java.util.List;

import ro.roda.domain.SchedExecution;
import ro.roda.domain.SchedTask;

public interface SchedulerService {

	public abstract long countTasks();

	public abstract SchedTask findTask(Integer id);

	public abstract List<SchedTask> findTasksAll();

	public abstract List<SchedTask> findTasks(int firstResult, int maxResults);

	public abstract void saveTask(SchedTask schedTask);

	public abstract SchedTask updateTask(SchedTask schedTask);

	public abstract void deleteTask(SchedTask schedTask);

	public abstract long countExecutions();

	public abstract SchedExecution findExecution(Integer id);

	public abstract List<SchedExecution> findExecutionsAll();

	public abstract List<SchedExecution> findExecutions(int firstResult, int maxResults);

	public abstract List<SchedExecution> findExecutionsFiltered(Integer taskId, Date date, Integer result);

	public abstract void saveExecution(SchedExecution schedExecution);

	public abstract SchedExecution updateExecution(SchedExecution schedExecution);

	public abstract void deleteExecution(SchedExecution schedExecution);

}

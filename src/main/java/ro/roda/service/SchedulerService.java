package ro.roda.service;

import java.util.Date;
import java.util.List;

import ro.roda.scheduler.Execution;
import ro.roda.scheduler.Task;

public interface SchedulerService {

	public abstract long countTasks();

	public abstract Task findTask(Integer id);

	public abstract List<Task> findTasksAll();

	public abstract List<Task> findTasks(int firstResult, int maxResults);

	public abstract void saveTask(Task task);

	public abstract Task updateTask(Task task);

	public abstract void deleteTask(Task task);

	public abstract long countExecutions();

	public abstract Execution findExecution(Integer id);

	public abstract List<Execution> findExecutionsAll();

	public abstract List<Execution> findExecutions(int firstResult, int maxResults);

	public abstract List<Execution> findExecutionsFiltered(Integer taskId, Date date, Integer result);

	public abstract void saveExecution(Execution execution);

	public abstract Execution updateExecution(Execution execution);

	public abstract void deleteExecution(Execution execution);

}

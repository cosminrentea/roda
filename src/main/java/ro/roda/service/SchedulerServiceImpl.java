package ro.roda.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.scheduler.Execution;
import ro.roda.scheduler.Task;

@Service
@Transactional
public class SchedulerServiceImpl implements SchedulerService {

	@Override
	public long countTasks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Task findTask(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> findTasksAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> findTasks(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTask(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task updateTask(Task task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTask(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long countExecutions() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Execution findExecution(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Execution> findExecutionsAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Execution> findExecutions(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Execution> findExecutionsFiltered(Integer taskId, Date date, Integer result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveExecution(Execution execution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Execution updateExecution(Execution execution) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteExecution(Execution execution) {
		// TODO Auto-generated method stub
		
	}

}

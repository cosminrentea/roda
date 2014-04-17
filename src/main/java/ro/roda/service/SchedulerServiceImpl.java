package ro.roda.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.SchedExecution;
import ro.roda.domain.SchedTask;

@Service
@Transactional
public class SchedulerServiceImpl implements SchedulerService {

	@Override
	public long countTasks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SchedTask findTask(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchedTask> findTasksAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchedTask> findTasks(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTask(SchedTask schedTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SchedTask updateTask(SchedTask schedTask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTask(SchedTask schedTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long countExecutions() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SchedExecution findExecution(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchedExecution> findExecutionsAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchedExecution> findExecutions(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchedExecution> findExecutionsFiltered(Integer taskId, Date date, Integer result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveExecution(SchedExecution schedExecution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SchedExecution updateExecution(SchedExecution schedExecution) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteExecution(SchedExecution schedExecution) {
		// TODO Auto-generated method stub
		
	}

}

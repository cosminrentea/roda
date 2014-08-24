package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.DdiEditorStudyProposal;

@Service
@Transactional
public class DdiEditorStudyProposalServiceImpl implements DdiEditorStudyProposalService {

	public List<DdiEditorStudyProposal> findAllDdiEditorStudyProposals() {
		return DdiEditorStudyProposal.findAllDdiEditorStudyProposals();
	}

	public DdiEditorStudyProposal findDdiEditorStudyProposal(Integer id) {
		return DdiEditorStudyProposal.findDdiEditorStudyProposal(id);
	}
}

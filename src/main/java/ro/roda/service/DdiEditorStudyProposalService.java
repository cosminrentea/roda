package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.DdiEditorStudyProposal;

public interface DdiEditorStudyProposalService {

	public abstract List<DdiEditorStudyProposal> findAllDdiEditorStudyProposals();

	public abstract DdiEditorStudyProposal findDdiEditorStudyProposal(Integer id);

}

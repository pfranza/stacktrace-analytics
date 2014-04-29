package com.peterfranza.stackserver.ui.server;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.peterfranza.stackserver.data.ApplicationDataManager;
import com.peterfranza.stackserver.ui.shared.FetchStackTraceList;
import com.peterfranza.stackserver.ui.shared.StackTraceEntryCollectionResult;
import com.peterfranza.stackserver.ui.shared.StackTraceEntryModel;
import com.peterfranza.stackserver.ui.shared.model.StackTraceEntry;

public class FetchStackTraceListHandler implements ActionHandler<FetchStackTraceList, StackTraceEntryCollectionResult>{

	@Inject
	Provider<ApplicationDataManager> dataManager;
	
	@Override
	public StackTraceEntryCollectionResult execute(FetchStackTraceList arg0,
			ExecutionContext arg1) throws DispatchException {
		StackTraceEntryCollectionResult list = new StackTraceEntryCollectionResult();
		list.setCount(dataManager.get().getStackCount());
		list.setStart(arg0.getStart());
		list.setList(new ArrayList<StackTraceEntryModel>(transform(dataManager.get().getStackEntries(arg0.getStart(), arg0.getLength()))));
		
		return list;
	}

	private Collection<StackTraceEntryModel> transform(
			Collection<StackTraceEntry> stackEntries) {
		ArrayList<StackTraceEntryModel> list = new ArrayList<StackTraceEntryModel>();
		for(StackTraceEntry s: stackEntries) {
			StackTraceEntryModel m = new StackTraceEntryModel();
				m.setApplicationId(s.getApplicationId());
				m.getFingerprints().addAll(s.getFingerprints());
				m.setRaw(s.getRaw());
				m.setTimeOccured(s.getTimeOccured());
				m.setVersion(s.getVersion());
			list.add(m);
		}
		return list;
	}

	@Override
	public Class<FetchStackTraceList> getActionType() {
		return FetchStackTraceList.class;
	}

	@Override
	public void rollback(FetchStackTraceList arg0,
			StackTraceEntryCollectionResult arg1, ExecutionContext arg2)
			throws DispatchException {}

}

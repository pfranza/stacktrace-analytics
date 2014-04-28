package com.peterfranza.stackserver.ui.client.viewtraces;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.peterfranza.stackserver.ui.shared.model.StackTraceEntry;

public class ViewTraces extends Composite {

	private static ViewTracesUiBinder uiBinder = GWT
			.create(ViewTracesUiBinder.class);

	interface ViewTracesUiBinder extends UiBinder<Widget, ViewTraces> {
	}
	
	@UiField(provided=true) CellTable<StackTraceEntry> table = new CellTable<StackTraceEntry>();
	@UiField(provided=true) SimplePager pager = new SimplePager(SimplePager.TextLocation.CENTER, true, true);

	private AsyncDataProvider<StackTraceEntry> dataProvider = new AsyncDataProvider<StackTraceEntry>() {
	      @Override
	      protected void onRangeChanged(HasData<StackTraceEntry> display) {
	        final Range range = display.getVisibleRange();
	        updateRows(range);
	      }
	};
	
	public ViewTraces() {
		initWidget(uiBinder.createAndBindUi(this));
		
		Column<StackTraceEntry, SafeHtml> nameColumn = new Column<StackTraceEntry, SafeHtml>(new SafeHtmlCell()) {			
			@Override
			public SafeHtml getValue(StackTraceEntry object) {
				return SafeHtmlUtils.fromString(object.getTimeOccured().toString());
			}
		};
		
	    table.addColumn(nameColumn, "Timestamp");
	    
	    
	    dataProvider.addDataDisplay(table);
		pager.setDisplay(table);
	}
	
	private void updateRows(final Range range) {
//		dispatcher.execute(new FetchLicenseInfoData(range.getStart(), range.getLength(), filterBox.getText().trim(), 
//				stateFilter.getValue(stateFilter.getSelectedIndex())), 
//        		new AsyncCallback<LicenseInfoResult>() {
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert(caught.getMessage());
//				throw new RuntimeException(caught);
//			}
//
//			@Override
//			public void onSuccess(LicenseInfoResult result) {
//				table.setRowCount(result.getTotalLicenseCount(), true);
//				table.setRowData(result.getStartingRow(), result.getLicense());
//			}
//		});
	}

}

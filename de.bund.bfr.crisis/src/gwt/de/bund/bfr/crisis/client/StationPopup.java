package de.bund.bfr.crisis.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class StationPopup extends Window {
	private final DynamicForm form = new DynamicForm();

	private final IButton saveButton = new IButton();

	private final IButton resetButton = new IButton();

	private final ProductGrid productGrid = new ProductGrid();

	public StationPopup() {
		// init myself
		initMySelf();
		// form for editing
		initEditForm();
	}

	public void updateStation(String id) {
		StationDS.getInstance().fetchData(new Criteria("id", id), new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				updateStation(response.getData()[0]);
			}
		});
	}

	public void updateStation(Record stationRecord) {
		setTitle(stationRecord.getAttribute("name") == null ? "New Station" : stationRecord.getAttribute("name"));
		form.editRecord(stationRecord);
		productGrid.setVisible(stationRecord.getAttribute("id") != null);
		productGrid.updateStation(stationRecord);
	}
	
	public Record getStationRecord() {
		return form.getValuesAsRecord();
	}

	private void initMySelf() {
		setWidth(850);
		setHeight(500);
		setMembersMargin(5);
		setMargin(5);
		setPadding(10);
		setOpacity(95);
		setCanDragResize(true);
		centerInPage();
		// form dialog
		this.setShowShadow(true);
		// this.setIsModal(true);
		this.setPadding(20);
		this.setWidth(900);
		this.setHeight(600);
		this.setShowMinimizeButton(false);
		this.setShowMaximizeButton(true);
		this.setShowCloseButton(true);
		this.setShowModalMask(true);
		this.centerInPage();
	}

	private void initEditForm() {
		// the form
		form.setIsGroup(false);
		form.setDataSource(StationDS.getInstance());
		form.setCellPadding(5);
		form.setWidth("100%");
		form.setColWidths(100, 300);
		form.setNumCols(4);
		String[] fieldNames = StationDS.getInstance().getFieldNames(true);
		ComboBoxItem[] fields = new ComboBoxItem[fieldNames.length];
		for (int index = 0; index < fields.length; index++) {
			fields[index] = new ComboBoxItem(fieldNames[index]);
			fields[index].setOptionDataSource(StationDS.getInstance());
			fields[index].setAlwaysFetchMissingValues(true);
			fields[index].setOptionOperationId("suggest");
			fields[index].setAllowEmptyValue(true);
			fields[index].setAddUnknownValues(true);
		}
		form.setFields(fields);

		saveButton.setTitle("SAVE");
		saveButton.setTooltip("Save this station");
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				form.saveData();
				StationPopup.this.close();
			}
		});
		resetButton.setTitle("RESET");
		resetButton.setTooltip("Reset");
		resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.reset();
			}
		});
		HLayout buttons = new HLayout(10);
		buttons.setAlign(Alignment.CENTER);
		buttons.addMember(resetButton);
		buttons.addMember(saveButton);
		buttons.setHeight(30);
		VLayout dialog = new VLayout(10);
		dialog.setPadding(10);
		dialog.addMember(form);
		dialog.addMember(buttons);
		dialog.addMember(productGrid.wrapWithActionButtons());
		dialog.setWidth100();
		this.addItem(dialog);
	}
}
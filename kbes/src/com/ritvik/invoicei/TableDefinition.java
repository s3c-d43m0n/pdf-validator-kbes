package com.ritvik.invoicei;

import java.util.ArrayList;

public class TableDefinition {
	
	private String id = "";
	private String start = "";
	private ArrayList<String> thingsToIgnore = new ArrayList<String>();
	private String checkSumItem = "";
	private Integer cyclic = 0;
	private String  cycleEnd = "";
	private String  ignoreStart = "";
	private String  ignoreEnd = "";
        private ArrayList<String> tags = new ArrayList<String>();
	private ArrayList<String> reverseEnds = new ArrayList<String>();
        private boolean isReverse = false;
        
	public TableDefinition() {}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public ArrayList<String> getThingsToIgnore() {
		return thingsToIgnore;
	}

	public void setThingsToIgnore(ArrayList<String> thingsToIgnore) {
		this.thingsToIgnore = thingsToIgnore;
	}

	public String getCheckSumItem() {
		return checkSumItem;
	}

	public void setCheckSumItem(String checkSumItem) {
		this.checkSumItem = checkSumItem;
	}

	public Integer getCyclic() {
		return cyclic;
	}

	public void setCyclic(Integer cyclic) {
		this.cyclic = cyclic;
	}

	public String getCycleEnd() {
		return cycleEnd;
	}

	public void setCycleEnd(String cycleEnd) {
		this.cycleEnd = cycleEnd;
	}

	public String getIgnoreStart() {
		return ignoreStart;
	}

	public void setIgnoreStart(String ignoreStart) {
		this.ignoreStart = ignoreStart;
	}

	public String getIgnoreEnd() {
		return ignoreEnd;
	}

	public void setIgnoreEnd(String ignoreEnd) {
		this.ignoreEnd = ignoreEnd;
	}

        public ArrayList<String> getTags() {
		return tags;
	}


	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}


	public ArrayList<String> getReverseEnds() {
		return reverseEnds;
	}


	public void setReverseEnds(ArrayList<String> reverseEnds) {
		this.reverseEnds = reverseEnds;
                isReverse = true;
	}	
        
        public boolean isReversible(){
            return isReverse;
        }
}

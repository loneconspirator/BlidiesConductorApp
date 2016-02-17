package enums;

public enum SendType {
	CHANGES ("On Change"),
	SLIDER_ONLY ("Slider Change"),
	CONTINUOUS ("Continuous"),
	CLICK ("Button Click");
	
	private String label;
	public String getLabel() { return label; }
	
	public String toString() { return label; }
	
	SendType(String label){
		this.label = label;
	}
}

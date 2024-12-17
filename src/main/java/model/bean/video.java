package model.bean;

public class video {
	private String name;
	private Boolean isDone;
	private String fileLocation;
	private String desc;
	private long filesize;
	public video() {}
	public video(String name, Boolean isDone, String fileLocation, String desc, long filesize) {
		super();
		this.name = name;
		this.isDone = isDone;
		this.fileLocation = fileLocation;
		this.desc = desc;
		this.filesize = filesize;
	}
	public Boolean getIsDone() {
		return isDone;
	}
	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
}

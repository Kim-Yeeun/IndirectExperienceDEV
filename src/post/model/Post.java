package post.model;

import java.util.Date;

public class Post {

	private Integer number;
	private WriterEnt writerEnt;
	private String title;
	private Date regDate;
	private Date modifiedDate;
	private int readCount;
	
	public Post(Integer number, WriterEnt writerEnt, String title,
			Date regDate, Date modifiedDate, int readCount) {
		this.number = number;
		this.writerEnt = writerEnt;
		this.title = title;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
		this.readCount = readCount;
	}

	public Integer getNumber() {
		return number;
	}

	public WriterEnt getWriterEnt() {
		return writerEnt;
	}

	public String getTitle() {
		return title;
	}

	public Date getRegDate() {
		return regDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public int getReadCount() {
		return readCount;
	}
	
}

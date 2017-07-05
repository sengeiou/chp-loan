/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.utilsVideo.java
 * @Create By 王彬彬
 * @Create In 2015年12月23日 下午8:07:41
 */
package com.creditharmony.loan.test.utils;

import java.util.Date;

/**
 * @Class Name Video
 * @author 王彬彬
 * @Create In 2015年12月23日
 */
public class TestBean {
	public TestBean(int id, String title, int hits, Date now) {
		super();
		this.id = id;
		this.title = title;
		this.hits = hits;
		this.now = now;
	}

	private int id;
	private String title;
	private int hits;
	private Date now;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}
}

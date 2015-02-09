package uk.ac.cam.kilo.backend;

import java.util.Vector;

public class ItemInfo {

	private String title;
    private Vector<String> authors;
    private Vector<String> descriptions;
    private Vector<String> reviews;
	
    /**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the authors
	 */
	public Vector<String> getAuthors() {
		return authors;
	}

	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(Vector<String> authors) {
		this.authors = authors;
	}

	/**
	 * @param author the author to add
	 */
	public void addAuthor(String author) {
		
		this.authors.add(author);
	}
	
	/**
	 * @return the description
	 */
	public Vector<String> getDescriptions() {
		return descriptions;
	}

	/**
	 * @param descriptions the description to set
	 */
	public void setDescriptions(Vector<String> descriptions) {
		this.descriptions = descriptions;
	}
	
	/**
	 * @param description the description to add
	 */
	public void addDescription(String description) {
		this.descriptions.add(description);
	}

	/**
	 * @return the reviews
	 */
	public Vector<String> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(Vector<String> reviews) {
		this.reviews = reviews;
	}
	
}

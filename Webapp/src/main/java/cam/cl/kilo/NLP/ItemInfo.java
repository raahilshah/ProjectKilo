/*
 * Copyright (C) 2015 Group Kilo (Cambridge Computer Lab)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cam.cl.kilo.NLP;

import java.io.Serializable;
import java.util.Vector;

/**
 * Class holding information about one product within Vectors, to allow for concurrent API calls
 *
 * @author groupKilo
 * @author dc561
 */
public class ItemInfo implements Serializable {

	private static final long serialVersionUID = 2900729725728472406L;
	private String title;
    private Vector<String> authors = new Vector<String>();
    private Vector<String> descriptions = new Vector<String>();
    private Vector<String> reviews = new Vector<String>();
	
    /**
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title to set
	 */
    public void setTitle(String title) {
        if (this.title == null) {
            if ( !title.matches("^\\s*$") ) {
                this.title = title;
            } else {
                System.out.println("Empty title");
            }
        } else {
            System.out.println("Title already set");
        }
    }

	/**
	 * @return The authors
	 */
	public Vector<String> getAuthors() {
		return authors;
	}

	/**
	 * @param authors The authors to set
	 */
	public void setAuthors(Vector<String> authors) {
		this.authors = (authors == null) ? this.authors : authors;
	}

	/**
	 * @param author The author to add
	 */
	public void addAuthor(String author) {
		
		this.authors.add(author);
	}
	
	/**
	 * @return The description
	 */
	public Vector<String> getDescriptions() {
		return descriptions;
	}

	/**
	 * @param descriptions The description to set
	 */
	public void setDescriptions(Vector<String> descriptions) {
		this.descriptions = (descriptions == null) ? this.descriptions : descriptions;
	}
	
	/**
	 * @param description The description to add
	 */
	public void addDescription(String description) {
		this.descriptions.add(description);
	}

	/**
	 * @return The reviews
	 */
	public Vector<String> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(Vector<String> reviews) {
        this.reviews = (reviews == null) ? this.reviews : reviews;
	}


    /**
     *
     * @return The descriptions' text as one String
     */
    public String getDescriptionsAsString() {
        StringBuilder sb = new StringBuilder();
        for (String s: descriptions) {
            sb.append(s + '\n');
        }
        return sb.toString();
    }

    /**
     *
     * @return The reviews' text as one String
     */
    public String getReviewsAsString() {
        StringBuilder sb = new StringBuilder();
        for (String s: reviews) {
            sb.append(s + '\n');
        }
        return sb.toString();
    }

}

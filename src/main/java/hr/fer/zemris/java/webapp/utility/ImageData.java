package hr.fer.zemris.java.webapp.utility;

import java.util.Arrays;
import java.util.List;

/**
 * Class which represents data of an image.<br>
 * 
 * Contains info about the image and tags which are connected with the image. Variables {@link #info} and {@link #tags}
 * can be gotten by getters and set by setters. Tags are saved in format "{tag}, {tag], ..." and can be gotten in a form
 * of a {@link List} by using {@link #getTagsAsList()} method.
 * 
 * @author Luka Lazanja
 * @version 1.0
 *
 */
public class ImageData {
	/** Info about the image. */
	private String info;
	/** Tags which are connected to the image */
	private String tags;

	/**
	 * Gets the info of this image.
	 * 
	 * @return image info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Sets the info of this image.
	 * 
	 * @param info
	 *            image info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Gets the tags connected to this image.
	 * 
	 * @return image tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * Sets the tags connected to this image.
	 * 
	 * @param tags
	 *            image tags
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * Gets the tags connected to this image in a form of a {@link List}.
	 * 
	 * @return {@link List} of tags
	 */
	public List<String> getTagsAsList() {
		String[] tagsArray = tags.split(", ");
		return Arrays.asList(tagsArray);
	}
}
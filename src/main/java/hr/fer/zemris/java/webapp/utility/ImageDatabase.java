package hr.fer.zemris.java.webapp.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Database class which contains image data shown by this web application.<br>
 * 
 * Data is stored in the map {@link #imgMap} whose key is name of the image and value is {@link ImageData}. Tags are
 * stored in an array {@link #tagsArray}.<br>
 * 
 * Database is initialized only once, when starting an application and opening page
 * {@literal http//localhost:8080/galerija/index.jsp} and it is done by method {@link #initialize(String)}. After
 * initialization, {@link #wasInitialized} is set to <code>true</code> and initialization cannot be done again.
 * 
 * @author Luka Lazanja
 *
 */
public class ImageDatabase {
	/** Map which connects {@link ImageData} to the name of an image. */
	private static Map<String, ImageData> imgMap;
	/** An array of image tags. */
	private static String[] tagsArray;
	/** Flag which tells if database has been initialized. */
	private static boolean wasInitialized;

	/**
	 * Gets the {@link #imgMap}.
	 * 
	 * @return map which connects {@link ImageData} to the name of an image.
	 */
	public static Map<String, ImageData> getImgMap() {
		return imgMap;
	}

	/**
	 * Gets the {@link #tagsArray}.
	 * 
	 * @return an array of image tags
	 */
	public static String[] getTagsArray() {
		return tagsArray;
	}

	/**
	 * Tells if database has already been initialized.
	 * 
	 * @return <code>true</code> if database has been initialized, <code>false</code> otherwise
	 */
	public static boolean wasInitialized() {
		return wasInitialized;
	}

	/**
	 * Initializes the database by loading the descriptor file, whose path is provided by the argument.
	 * 
	 * @param fileName
	 *            path to the descriptor file
	 * @throws IOException
	 *             if there has been an error while reading the descriptor file
	 */
	public static void initialize(String fileName) throws IOException {
		if(wasInitialized) return;
		
		List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		Set<String> tags = new TreeSet<>();
		imgMap = new HashMap<>();

		for (int i = 0, len = lines.size(); i < len; i += 3) {
			String imageName = lines.get(i);
			String imageInfo = lines.get(i + 1);
			String imageTags = lines.get(i + 2);

			ImageData data = new ImageData();
			data.setInfo(imageInfo);
			data.setTags(imageTags);

			imgMap.put(imageName, data);

			List<String> lineTags = data.getTagsAsList();
			for (String tag : lineTags) {
				tags.add(tag);
			}
		}

		tagsArray = new String[tags.size()];
		tagsArray = tags.toArray(tagsArray);

		wasInitialized = true;
	}
}
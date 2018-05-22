package hr.fer.zemris.java.webapp.web.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which shows a thumbnail of an image which is specified by the name gotten by the URL parameter
 * {@value #PARAMETER_NAME}. Thumbnail is gotten by resizing the full sized image located in folder
 * {@value #IMAGES_PATH}. Width of the thumbnail will be {@value #THUMBNAIL_WIDTH} and height will be
 * {@value #THUMBNAIL_HEIGHT}. Thumbnail is then stored in folder {@value #THUMBNAILS_PATH} so that it does not have to
 * be resized if needed again, but will only be gotten from the thumbnails folder.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/servlets/image"})
public class ImageServlet extends HttpServlet {
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	/** {@link String} constant which represents name of the parameter which is gotten by the URL. */
	public static final String PARAMETER_NAME = "name";
	/** Path to the folder with thumbnail images. */
	public static final String THUMBNAILS_PATH = "/WEB-INF/thumbnails";
	/** Path to the folder with full sized images. */
	public static final String IMAGES_PATH = "/WEB-INF/slike";
	/** Default thumbnail width. */
	public static final int THUMBNAIL_WIDTH = 150;
	/** Default thumbnail height. */
	public static final int THUMBNAIL_HEIGHT = 150;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imgName = req.getParameter(PARAMETER_NAME);

		String thumbsFolder = req.getSession().getServletContext().getRealPath(THUMBNAILS_PATH);
		Path thumbsPath = Paths.get(thumbsFolder + "/" + imgName);

		BufferedImage img = null;
		if (!Files.exists(thumbsPath)) {
			BufferedImage srcImg = null;
			String imagesPath = req.getSession().getServletContext().getRealPath(IMAGES_PATH);
			Path srcImgPath = Paths.get(imagesPath + "/" + imgName);
			
			srcImg = ImageIO.read(srcImgPath.toFile());

			BufferedImage bufferedThumb = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT,
					BufferedImage.TYPE_INT_RGB);

			Graphics2D graphics = bufferedThumb.createGraphics();
			graphics.drawImage(srcImg, 0, 0, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, null);
			graphics.dispose();

			ImageIO.write(bufferedThumb, "jpg", thumbsPath.toFile());
		}

		img = ImageIO.read(thumbsPath.toFile());

		resp.setContentType("image/jpg");
		ImageIO.write(img, "jpg", resp.getOutputStream());
	}
}
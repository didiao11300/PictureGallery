package hr.fer.zemris.java.webapp.web.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which shows the full sized image which is specified by its name that is gotten by the URL
 * parameter {@value #PARAMETER_NAME}.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/servlets/fullImage"})
public class FullImageServlet extends HttpServlet {
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	/** {@link String} constant which represents name of the parameter which is gotten by the URL. */
	public static final String PARAMETER_NAME = "name";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imgName = req.getParameter(PARAMETER_NAME);

		String imagesPath = req.getSession().getServletContext().getRealPath(ImageServlet.IMAGES_PATH);
		Path imgPath = Paths.get(imagesPath + "/" + imgName);

		BufferedImage img = ImageIO.read(imgPath.toFile());

		ImageIO.write(img, "jpg", resp.getOutputStream());
	}
}
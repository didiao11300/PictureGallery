package hr.fer.zemris.java.webapp.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.webapp.utility.ImageDatabase;

/**
 * {@link HttpServlet} which initializes the {@link ImageDatabase} if needed and shows all the image tags using the
 * {@link Gson}.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/servlets/tags"})
public class TagsServlet extends HttpServlet {
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	/** Path to the descriptor file. */
	public static final String DESCRIPTOR_PATH = "/WEB-INF/opisnik.txt";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!ImageDatabase.wasInitialized()) {
			String fileName = req.getSession().getServletContext().getRealPath(DESCRIPTOR_PATH);
			ImageDatabase.initialize(fileName);
		}

		String[] tagsArray = ImageDatabase.getTagsArray();

		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(tagsArray);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();
	}

}
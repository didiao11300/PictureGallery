package hr.fer.zemris.java.webapp.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.webapp.utility.ImageDatabase;

/**
 * {@link HttpServlet} which gets image names of all the images connected to the tag which is gotten as an URL parameter
 * {@value #PARAMETER_NAME}. Image names are processed with the {@link Gson}.
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/servlets/images"})
public class ImagesServlet extends HttpServlet {
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	/** {@link String} constant which represents name of the parameter which is gotten by the URL. */
	public static final String PARAMETER_NAME = "tag";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter(PARAMETER_NAME);

		List<String> images = new ArrayList<>();

		ImageDatabase.getImgMap().entrySet().forEach((entry) -> {
			int index = entry.getValue().getTagsAsList().indexOf(tag);
			if (index != -1) {
				images.add(entry.getKey());
			}
		});


		String[] imagesArray = new String[images.size()];
		imagesArray = images.toArray(imagesArray);

		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(imagesArray);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();
	}
}
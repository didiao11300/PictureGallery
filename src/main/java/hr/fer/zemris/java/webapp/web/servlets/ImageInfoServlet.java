package hr.fer.zemris.java.webapp.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.webapp.utility.ImageData;
import hr.fer.zemris.java.webapp.utility.ImageDatabase;

/**
 * {@link HttpServlet} which shows the info of an image that is processed by {@link Gson}. Image is specified by its
 * name which is gotten by the URL parameter {@value #PARAMETER_NAME}..
 * 
 * @author Luka Lazanja
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/servlets/imageInfo"})
public class ImageInfoServlet extends HttpServlet {
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	/** {@link String} constant which represents name of the parameter which is gotten by the URL. */
	public static final String PARAMETER_NAME = "name";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imgName = req.getParameter(PARAMETER_NAME);

		ImageData data = ImageDatabase.getImgMap().get(imgName);
		
		resp.setContentType("application/json;charset=UTF-8");

		ImageData[] dataArray = new ImageData[]{data};
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(dataArray);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();
	}
}
package com.egzaell.isi.lab3;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.egzaell.isi.lab3.pojo.User;

//klasa servletu obslugujaca mechanizm logowania
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// pole ze zmienna klasy User
	private User user;

	// konstruktor ktory tworzy servlet, ale rozwniez wywoluje metody setUpUser,
	// nulluje zmienna user, po czym wywoluje parseXml
	public LoginServlet() {
		super();
		setUpUser();
		user = null;
		parseXml();
	}

	// metoda post, tworzy 2 zmienne typu String, i przypisuje im parametrzy
	// podane przez uzytkownika
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// pobieramy sesje
		HttpSession session = request.getSession();

		// sprawdzenie czy podane dane sa poprawne, jesli tak to przypisuje
		// atrybuty uzytkownika do sessji, w przypadku blednych danych logowania
		// przerzuca na strone failed
		if (username.equals(user.getUserName()) && password.equals(user.getPassword())) {
			session.setAttribute("firstName", user.getFirstName());
			session.setAttribute("lastName", user.getLastName());
			session.setAttribute("username", user.getUserName());
			request.getRequestDispatcher("/logged.jsp").forward(request, response);
		} else {
			String failed = "You have failed";
			session.setAttribute("failed", failed);
			request.getRequestDispatcher("/unlogged.jsp").forward(request, response);
		}

	}

	//twozymy nowego usera i zapisujemy go do pliku xml
	private void setUpUser() {
		user = new User();
		user.setFirstName("Zbigniew");
		user.setLastName("Kryczka");
		user.setUserName("zbynio");
		user.setPassword("xxx");

		try {

			File file = new File("users.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(user, file);
			jaxbMarshaller.marshal(user, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	//pobieramy pola z pliku xml i tworzymy nowego usera
	public void parseXml() {

		try {

			File file = new File("users.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			user = (User) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}

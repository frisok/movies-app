package nl.movie.web.component.login;

import nl.movie.service.MoviesRestClient;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Optional;

/**
 *
 */
public class LoginPanel extends Panel {

    @SpringBean
    private MoviesRestClient moviesRestClient;

    private String username;
    private String password;

    public LoginPanel(final String id) {
        super(id);
        setDefaultModel(new CompoundPropertyModel(this));


        final TextField<String> usernameField = new TextField<>("username");
        final PasswordTextField passwordField = new PasswordTextField("password");
        final Form<String> loginForm = new Form<String>("loginForm") {

            @Override
            public void onSubmit() {
                final Optional<String> response = moviesRestClient.login(usernameField.getDefaultModelObjectAsString(), passwordField.getDefaultModelObjectAsString());
                if (response.isPresent()) {
                    WebSession.get().setAttribute("authenticationToken", response.get());
                } else {
                    //TODO handle unsuccesfull login
                }
                username = "";
                password = "";
            }
        };

        loginForm.add(usernameField);
        loginForm.add(passwordField);

        add(loginForm);
    }

}
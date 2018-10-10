package nl.movie.web.component.login;

import nl.movie.data.domain.Credentials;
import nl.movie.service.MoviesRestClient;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Optional;

/**
 *
 */
public class LoginPanel extends Panel {

    @SpringBean
    private MoviesRestClient moviesRestClient;

    public LoginPanel(final String id) {
        super(id, new CompoundPropertyModel<>(new Credentials()));


        final TextField<String> usernameField = new TextField<>("username");
        usernameField.add(new AttributeModifier("placeholder", new StringResourceModel("username")));
        final PasswordTextField passwordField = new PasswordTextField("password");
        passwordField.add(new AttributeModifier("placeholder", new StringResourceModel("password")));
        final Form<String> loginForm = new Form<String>("loginForm") {

            @Override
            public void onSubmit() {
                final Optional<String> response = moviesRestClient.login(usernameField.getDefaultModelObjectAsString(), passwordField.getDefaultModelObjectAsString());
                if (response.isPresent()) {
                    WebSession.get().setAttribute("authenticationToken", response.get());
                } else {
                    info("Oops!");
                }

            }
        };

        loginForm.add(usernameField);
        loginForm.add(passwordField);
        loginForm.add(new Button("login").add(new Label("loginLabel", new StringResourceModel("login")  )));

        add(loginForm);
    }

}
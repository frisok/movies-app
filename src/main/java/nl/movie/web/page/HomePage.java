package nl.movie.web.page;

import nl.movie.web.component.login.LoginPanel;
import nl.movie.web.component.movie.MoviesPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {

    private static final long serialVersionUID = -5312778937959142886L;

    public HomePage(final PageParameters parameters) {
        super(parameters);


        add(new LoginPanel("login") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(WebSession.get().getAttribute("authenticationToken") == null);
            }
        });
        add((new Label("title", new StringResourceModel("homePageTitle"))));

        final MoviesPanel moviesPanel = new MoviesPanel("movies-panel", () -> {
            return "amsterdam";
        }) {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setOutputMarkupId(true);
            }
        };
        add(moviesPanel);

        add(ajaxLink(moviesPanel, "amsterdam"));
        add(ajaxLink(moviesPanel, "rotterdam"));
        add(ajaxLink(moviesPanel, "utrecht"));
        add(ajaxLink(moviesPanel, "all"));
    }

    private AjaxLink<String> ajaxLink(final MoviesPanel moviesPanel, final String city) {

        return new AjaxLink<String>("link-" + city, new StringResourceModel(city)) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.add(moviesPanel);
                moviesPanel.setDefaultModel(() -> {
                    return city;
                });
            }
        };

    }

}

package nl.movie.web.page;

import nl.movie.service.MoviesRestClient;
import nl.movie.web.component.login.LoginPanel;
import nl.movie.web.component.movie.MoviesPanel;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends WebPage {

    private static final long serialVersionUID = -5312778937959142886L;
    public static final String AUTHENTICATION_TOKEN = "authenticationToken";

    @SpringBean
    private MoviesRestClient moviesRestClient;


    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(initializeRefreshButton());

        add(new LoginPanel("login") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(WebSession.get().getAttribute(AUTHENTICATION_TOKEN) == null);
            }
        });
        add((new Label("title", new StringResourceModel("homePageTitle"))));
        add(new FeedbackPanel("feedback"));

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

    private MarkupContainer initializeRefreshButton() {

        return new Form<String>("refreshForm") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(WebSession.get().getAttribute(AUTHENTICATION_TOKEN) != null);
            }
        }.add(new AjaxButton("refresh") {

                    @Override
                    public void onSubmit(final AjaxRequestTarget target) {
                        moviesRestClient.updateMovies((String) WebSession.get().getAttribute(AUTHENTICATION_TOKEN));
                    }
                }.add(new Label("refreshLabel", new StringResourceModel("refresh")))
        );
    }

    private AjaxLink<String> ajaxLink(final MoviesPanel moviesPanel, final String city) {

        final AjaxLink<String> link = new AjaxLink<String>("link-" + city, new StringResourceModel(city)) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.add(moviesPanel);
                moviesPanel.setDefaultModel(() -> {
                    return city;
                });
            }
        };

        link.add(new Label(city, new StringResourceModel(city)));

        return link;
    }

}

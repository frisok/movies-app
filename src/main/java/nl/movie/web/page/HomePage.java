package nl.movie.web.page;

import nl.movie.service.UserService;
import nl.movie.web.component.movie.MoviesPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private UserService userService;


    public HomePage(final PageParameters parameters) {
        super(parameters);

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

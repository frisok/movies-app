package nl.movie.web.component.screening;

import nl.movie.service.domain.Movie;
import nl.movie.service.domain.Screening;
import nl.movie.service.util.MoviesDateUtil;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;

/**
 *
 */
public class ScreeningsPanel extends GenericPanel<Movie> {


    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisibilityAllowed(getModelObject() != null);
    }

    public ScreeningsPanel(final String id, final IModel<Movie> model) {
        super(id, model);

        final Component screeningsComponenent = new WebMarkupContainer("screenings").add(new ListView<Screening>("screenings", new LoadableDetachableModel<List<Screening>>() {
            @Override
            protected List<Screening> load() {
                List<Screening> result = (model.getObject().getScreenings());

                return result;
            }
        }) {
            @Override
            protected void populateItem(final ListItem<Screening> item) {
                final Screening screening = item.getModelObject();
                item.add(new Label("screeningCity", screening.getCinema().getCity()));
                item.add(new Label("screeningCinema", screening.getCinema().getName()));
                item.add(new Label("screeningStartDate", MoviesDateUtil.extractDate(screening.getStartDateTime())));
                item.add(new Label("screeningStartTime", MoviesDateUtil.extractTime(screening.getStartDateTime())));
            }
        });


        add(screeningsComponenent);
    }


}
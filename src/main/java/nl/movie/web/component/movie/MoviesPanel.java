package nl.movie.web.component.movie;

import nl.movie.service.MoviesRestClient;
import nl.movie.service.domain.Movie;
import nl.movie.web.component.image.ExternalSourceImage;
import nl.movie.web.component.screening.ScreeningsAjaxLinkPanel;
import nl.movie.web.component.screening.ScreeningsPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MoviesPanel extends Panel {

    @SpringBean
    private MoviesRestClient moviesRestClient;

    private IModel<Movie> selectedItem = new Model<>();
    private ScreeningsPanel screeningsPanel;
    private ModalWindow modalWindow;


    public MoviesPanel(String id, IModel<String> model) {

        super(id, model);
        screeningsPanel = new ScreeningsPanel("content", selectedItem);
        initializeModalWindow();
        add(modalWindow);

        final DataTable<Movie, String> dataTable = new DataTable<>("moviesTable", createColumns(), new ListDataProvider<Movie>() {

            @Override
            protected List<Movie> getData() {
                return moviesRestClient.findByCity(getDefaultModelObjectAsString());
            }

        }, 1000);

        dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, null));
        final Component moviesComponent = new WebMarkupContainer("moviesTable").add(dataTable);
        moviesComponent.setOutputMarkupId(true);

        add(moviesComponent);
    }

    private void initializeModalWindow() {
        modalWindow = new ModalWindow("screeningsModal");
        modalWindow.setMaskType(ModalWindow.MaskType.SEMI_TRANSPARENT);
        modalWindow.setContent(screeningsPanel);
        modalWindow.setResizable(false);
        //modalWindow.setCssClassName("modal-dialog-custom");
        modalWindow.setTitle(new StringResourceModel("screeningsModalTitle"));
    }

    private List<IColumn<Movie, String>> createColumns() {

        List<IColumn<Movie, String>> columns = new ArrayList<>();

        columns.add(createTitleColumn());
        columns.add(createPosterColumn());
        columns.add(createDescriptionColumn());
        columns.add(createScreeningsTodayColumn());
        columns.add(createDetailsLinkColumn());

        return columns;
    }

    private PropertyColumn<Movie, String> createTitleColumn() {
        return new PropertyColumn<Movie, String>(new StringResourceModel("title"), "title") {
            @Override
            public void populateItem(Item item, String componentId, IModel rowModel) {
                super.populateItem(item, componentId, rowModel);
                item.add(new AttributeModifier("class", "col-md-1"));
            }
        };
    }

    private AbstractColumn<Movie, String> createDescriptionColumn() {
        return new AbstractColumn<Movie, String>(new StringResourceModel("description")) {

            @Override
            public void populateItem(Item<ICellPopulator<Movie>> item, String componentId, IModel<Movie> rowModel) {
                item.add(new Label(componentId, new Model<>(rowModel.getObject().getMovieDetails().getPlot())));
                item.add(new AttributeModifier("class", "col-md-3"));
            }
        };
    }

    private AbstractColumn<Movie, String> createPosterColumn() {
        return new AbstractColumn<Movie, String>(new StringResourceModel("poster")) {

            @Override
            public void populateItem(Item<ICellPopulator<Movie>> item, String componentId, IModel<Movie> rowModel) {
                item.add(new ExternalSourceImage(componentId, new Model<>(rowModel.getObject().getMovieDetails().getPoster())));
                item.add(new AttributeModifier("class", "col-md-1"));
            }
        };

    }

    private AbstractColumn<Movie, String> createScreeningsTodayColumn() {
        return new AbstractColumn<Movie, String>(new StringResourceModel("screeningsToday")) {

            @Override
            public void populateItem(Item<ICellPopulator<Movie>> item, String componentId, IModel<Movie> rowModel) {
                item.add(new Label(componentId, new Model<>(rowModel.getObject().screeningsToday())));
                item.add(new AttributeModifier("class", "col-md-2"));
            }
        };

    }


    protected final PropertyColumn<Movie, String> createDetailsLinkColumn() {
        return new PropertyColumn<Movie, String>(new Model<>(""), "screeningsLink") {

            @Override
            public void populateItem(final Item item, final String componentId, final IModel rowModel) {
                item.add(new ScreeningsAjaxLinkPanel(componentId, selectedItem, modalWindow, rowModel));
                item.add(new AttributeModifier("class", "col-md-1"));
            }
        };

    }

}


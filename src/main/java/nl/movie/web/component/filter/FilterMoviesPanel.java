package nl.movie.web.component.filter;

import nl.movie.data.domain.MovieFilter;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import java.util.Date;

/**
 *
 */
public abstract class FilterMoviesPanel extends Panel {


    public FilterMoviesPanel(final String id) {
        super(id, new CompoundPropertyModel<>(MovieFilter.builder().build()));

        final Form<String> form = new Form<>("filterForm");
        final TextField<String> tileField = new TextField<>("title");
        final TextField<String> cityField = new TextField<>("city");
        final DateTextField startField = new DateTextField("start");
        final DateTextField endField = new DateTextField("end");
        final CheckBox childFriendlyField = new CheckBox("childFriendly");
        final NumberTextField<Integer> maxDistanceinKMField = new NumberTextField<>("maxDistanceinKM");

        final IndicatingAjaxButton filterButton = new IndicatingAjaxButton("filterButton") {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                final Component targetComponent = getTargetComponent();
                target.add(targetComponent);
                targetComponent.setDefaultModel(Model.of(
                        MovieFilter.builder()
                                .title(tileField.getDefaultModelObjectAsString())
                                .city(cityField.getDefaultModelObjectAsString())
                                .start((Date) startField.getDefaultModelObject())
                                .end((Date) endField.getDefaultModelObject())
                                .childFriendly((Boolean) childFriendlyField.getDefaultModelObject())
                                .maxDistanceinKM((Integer) maxDistanceinKMField.getDefaultModelObject())
                                .build())
                );
            }
        };
        filterButton.add(new Label("filterLabel", new StringResourceModel("filterLabel")));

        form.add(new Label("titleLabel",new StringResourceModel("titleLabel")));
        form.add(tileField);
        form.add(new Label("cityLabel",new StringResourceModel("cityLabel")));
        form.add(cityField);
        form.add(new Label("startLabel",new StringResourceModel("startLabel")));
        form.add(startField);
        form.add(new Label("endLabel",new StringResourceModel("endLabel")));
        form.add(endField);
        form.add(new Label("childFriendlyLabel",new StringResourceModel("childFriendlyLabel")));
        form.add(childFriendlyField);
        form.add(new Label("distanceLabel",new StringResourceModel("distanceLabel")));
        form.add(maxDistanceinKMField);
        form.add(filterButton);

        add(form);
    }


    public abstract Component getTargetComponent();

}
package nl.movie.web.component.screening;

import nl.movie.service.domain.Movie;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResourceReference;

/**
 *
 */
public class ScreeningsAjaxLinkPanel extends Panel {

    public ScreeningsAjaxLinkPanel(String id, IModel<Movie> selectItemModel, final ModalWindow modalWindow, final IModel rowModel) {
        super(id);

        AjaxLink<String> ajaxLink = new AjaxLink<String>("link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                selectItemModel.setObject((Movie) rowModel.getObject());
                modalWindow.show(target);
            }
        };

        final Image screeningIcon = new Image("screening-icon", new ContextRelativeResourceReference("images/screening-icon.png"));
        ajaxLink.add(screeningIcon);
        add(ajaxLink);

    }

}
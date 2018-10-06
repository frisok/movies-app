package nl.movie.web.component.image;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 *
 */
public class ExternalSourceImage extends Panel {


    private static final String HTTP = "http";

    public ExternalSourceImage(String id, IModel<String> model) {
        super(id, model);
        final String url = model.getObject();
        add(new Image("image", new UrlResourceReference(Url.parse(StringUtils.contains(url, HTTP) ? url : ""))) {

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(StringUtils.contains(url, HTTP));
            }
        });
    }

}
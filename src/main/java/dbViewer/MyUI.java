package dbViewer;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    SQLDriver sqlDriver=new SQLDriver();
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        ViewQuery vc=new ViewQuery();

        TextField tf=new TextField();

        layout.addComponents(vc, tf, new Button("click",e->{
            vc.render(sqlDriver.query(tf.getValue()));
        }));
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}


class ViewQuery extends VerticalLayout{
    public void render(List<List<String>> lls){
        removeAllComponents();
        addComponent(new Grid<List<String>>(){{
            setSizeFull();
            setItems(lls.subList(1,lls.size()));

            for(int i=0;i<lls.get(0).size();i++){
                final int ii=i;
                addColumn(ls->ls.get(ii)).setCaption(lls.get(0).get(ii));
            }

        }});
    }
}


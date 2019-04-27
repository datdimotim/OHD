package dbViewer;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        final VerticalLayout layout = new VerticalLayout();
        final SQLDriver sqlDriver=new SQLDriver();
        ViewQuery vc=new ViewQuery();



        TextField tf=new TextField();


        VerticalLayout tablesLayout = new VerticalLayout(){{
                Button tablePerson = new Button("Persons", (cl)->{
                    vc.renderFullTable("Persons", new Consumer<Void>() {
                        @Override
                        public void accept(Void aVoid) {
                            InputWindow subWindowUI = new InputWindow(new Consumer<InputWindow>() {
                                @Override
                                public void accept(InputWindow w) {
                                    List<List<String>> lls=sqlDriver.query("select max(id) from Persons");
                                    int i = Integer.parseInt(lls.get(1).get(0))+1;
                                    sqlDriver.update("insert into Persons values (" + i + ",\"" + w.getName() + "\"," + w.getAge() + ");");
                                    vc.update();
                                }
                            });
                            UI.getCurrent().addWindow(subWindowUI);
                        }
                        });
                });

                tablePerson.setStyleName(ValoTheme.BUTTON_TINY);
                Button tableStudents = new Button("Students", (cl)->{
                    vc.renderFullTable("Students", new Consumer<Void>() {
                        @Override
                        public void accept(Void aVoid) {
                            InputStudentWindow subWindowUI = new InputStudentWindow(new Consumer<InputStudentWindow>() {
                                @Override
                                public void accept(InputStudentWindow w) {
                                    List<List<String>> lls=sqlDriver.query("select max(id) from Students");
                                    int i = Integer.parseInt(lls.get(1).get(0))+1;
                                    sqlDriver.update("insert into Students values (" + i + ",\"" + w.getName() + "\"," + w.getAge() + ",\"" + w.getDepartment()+"\");");
                                    vc.update();
                                }
                            });
                            UI.getCurrent().addWindow(subWindowUI);
                        }
                    });

                }) ;
                tableStudents.setStyleName(ValoTheme.BUTTON_TINY);
                addComponents(tablePerson,tableStudents);
        }};

        VerticalLayout queryLayout = new VerticalLayout(){{
            Button query1 = new Button("query1", cl->{
                vc.render("select nameNum from Persons where (ageNum = (select max(ageNum) from Persons))");
            });
            query1.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query1);
        }};


        layout.addComponents(vc, tf, new Button("click",e->{
            vc.render(tf.getValue());
        }));
        horizontalLayout.addComponents(tablesLayout,queryLayout,layout);

        setContent(horizontalLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}


class ViewQuery extends VerticalLayout{
    private final SQLDriver sqlDriver=new SQLDriver();
    Consumer<Void> consumer;
    private String table;
    public void update(){
        renderFullTable(table,consumer);
    }
    ViewQuery(){
        setMargin(false);
    }
    public void render(String query){

        List<List<String>> lls=sqlDriver.query(query);
        removeAllComponents();
        addComponent(new Label("Текущий запрос: "+query));
        addComponent(new Grid<List<String>>(){{
            setSizeFull();
            setItems(lls.subList(1,lls.size()));

            for(int i=0;i<lls.get(0).size();i++){
                final int ii=i;
                addColumn(ls->ls.get(ii)).setCaption(lls.get(0).get(ii));
            }
        }});
    }
    public void renderFullTable(String table, Consumer<Void> addButtonListener){
        this.table = table;
        this.consumer=addButtonListener;
        String query="select * from "+table;
        List<List<String>> lls=sqlDriver.query(query);
        removeAllComponents();

        Button addButton = new Button("Добавить",(cl)-> addButtonListener.accept(null));
        addButton.setStyleName(ValoTheme.BUTTON_TINY);
        addComponent(addButton);
        addComponent(new Label("Текущий запрос: "+query));
        addComponent(new Grid<List<String>>(){{
            setSizeFull();
            setItems(lls.subList(1,lls.size()));

            for(int i=0;i<lls.get(0).size();i++){
                final int ii=i;
                addColumn(ls->ls.get(ii)).setCaption(lls.get(0).get(ii));
            }

            addColumn(ls->{
                Button delete =  new Button("удалить "+ls.get(0), e->{
                    sqlDriver.update("delete from "+ table+" where id = "+ls.get(0));
                    renderFullTable(table, addButtonListener);
                });
                delete.setStyleName(ValoTheme.BUTTON_TINY);
                return delete;
            } ,new ComponentRenderer()).setCaption("удалить заголовок");

        }});
    }
}


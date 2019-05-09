package dbViewer;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
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
        final DateField dateField = new DateField(null,LocalDate.now());

        dateField.setVisible(false);
        Label infoLabel = new Label();
        layout.addComponent(infoLabel);
        ViewQuery vc=new ViewQuery();
        final Button makeQuery = new Button("Запрос", c->{
            vc.render("select distinct Students.nameNUm from  Students inner join Marks on Students.id=st_id where Marks.exam_date <= \""+dateField.getValue().toString()+"\";");
        });
        makeQuery.setVisible(false);
        makeQuery.setStyleName(ValoTheme.BUTTON_TINY);
        layout.addComponent(makeQuery);


        TextField tf=new TextField();



        VerticalLayout tablesLayout = new VerticalLayout(){{

                Button tablePerson = new Button("Persons", (cl)->{
                    dateField.setVisible(false);
                    makeQuery.setVisible(false);
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
                Button tableStudents = new Button("Клиенты", (cl)->{
                    dateField.setVisible(false);
                    makeQuery.setVisible(false);

                    vc.renderFullTable("Clients", new Consumer<Void>() {
                        @Override
                        public void accept(Void aVoid) {
                            InputStudentWindow subWindowUI = new InputStudentWindow(new Consumer<InputStudentWindow>() {
                                @Override
                                public void accept(InputStudentWindow w) {

                                    sqlDriver.update("insert into Clients values (\"" + w.getPassport() + "\"" + "," + "\"" + w.getName() + "\"," +
                                            w.getAge() + "," + w.getAccidents() + "," + w.getExpir() + ",\"" + w.getPol() + "\"" + ")");
                                    vc.update();
                                }
                            });
                            UI.getCurrent().addWindow(subWindowUI);
                        }
                    }, new Consumer<List<String>>() {
                        @Override
                        public void accept(List<String> ls) {
                            sqlDriver.update("delete from "+ "Clients "+" where passport = \""+ls.get(0)+"\"");
                        }
                    });

                }) ;
                tableStudents.setStyleName(ValoTheme.BUTTON_TINY);

            Button tableLectires = new Button("Механики", (cl)->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                vc.renderFullTable("Mechanics", new Consumer<Void>() {
                    @Override
                    public void accept(Void aVoid) {
                        LecturesInputWindow subWindowUI = new LecturesInputWindow(new Consumer<LecturesInputWindow>() {
                            @Override
                            public void accept(LecturesInputWindow w) {
                                System.out.println("insert into Mechanics values (\"" + w.getPassport() + "\",\"" + w.getName() + "\","  +
                                        w.getSalary() +","+w.getExpir()+","+w.getAge() + ",\"" + w.getPol()+"\"" + ");");
                                sqlDriver.update("insert into Mechanics values (\"" + w.getPassport() + "\",\"" + w.getName() + "\","  +
                                        w.getSalary() +","+w.getExpir()+","+w.getAge() + ",\"" + w.getPol()+"\"" + ");");
                                vc.update();
                            }
                        });
                        UI.getCurrent().addWindow(subWindowUI);
                    }
                },new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> ls) {
                        sqlDriver.update("delete from "+ "Mechanics "+" where passport = \""+ls.get(0)+"\"");
                    }
                });

            }) ;
            tableLectires.setStyleName(ValoTheme.BUTTON_TINY);

            Button tableSubjects = new Button("Автомобили", (cl)->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                vc.renderFullTable("Cars", new Consumer<Void>() {
                    @Override
                    public void accept(Void aVoid) {
                        SubjectInputWindow subWindowUI = new SubjectInputWindow(new Consumer<SubjectInputWindow>() {
                            @Override
                            public void accept(SubjectInputWindow w) {


                                sqlDriver.update("insert into Cars values (\"" + w.getPassport() + "\",\"" + w.getName() + "\","  +
                                       w.getAge()+","+w.getAccidents()+ ");");
                                vc.update();
                            }
                        });
                        UI.getCurrent().addWindow(subWindowUI);
                    }
                },new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> ls) {
                        sqlDriver.update("delete from "+ "Cars "+" where LIC_NUM = \""+ls.get(0)+"\"");
                    }
                });

            }) ;
            tableSubjects.setStyleName(ValoTheme.BUTTON_TINY);


            Button tableMarks = new Button("Ремонт", (cl)->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                vc.renderFullTable("JOBS", aVoid -> {
                    MarksInputWindow subWindowUI = new MarksInputWindow(w -> {
                        sqlDriver.update("insert into JOBS values (\"" +
                                w.getCar_id() + "\",\"" + w.getVisitDate() + "\",\"" + w.getClient_Id() + "\",\"" + w.getMec_id() + "\"," + w.getTime()+","+w.getCost()  +
                                ");");
                        vc.update();
                    });
                    UI.getCurrent().addWindow(subWindowUI);
                }, ls -> {
                    sqlDriver.update("delete from "+ "JOBS"+" where LIC_NUM = \""+ls.get(0)+"\"" +" and acc_date = \""+ls.get(1)+"\"");
                });
            }) ;
            tableMarks.setStyleName(ValoTheme.BUTTON_TINY);

            addComponents(tableStudents,tableLectires,tableSubjects,tableMarks);
        }};

        VerticalLayout queryLayout = new VerticalLayout(){{
            Button query1 = new Button("query1", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Работник мужчина с самой высокой зарплатой");
                vc.render("select name from Mechanics where (salary = (select max(salary) from Mechanics where pol=\"М\"));");
            });
            query1.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query1);

            Button query2 = new Button("query2", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Имена преподавателей, которые ставили 5 студентам");
                vc.render("select distinct Teachers.NameNum from Teachers inner join Marks on ID=T_ID where mark=5;");
            });
            query2.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query2);

            Button query3 = new Button("query3", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Отличие среднего балла студента ФКТИ от среднего по всем факультетам");
                vc.render("select avg(mark/(select avg(mark)  from Teachers inner join Marks on Teachers.ID=T_ID inner join Students on Students.id=st_id where department=\"FKTI\" || department=\"ФКТИ\"))  from Teachers inner join Marks on Teachers.ID=T_ID inner join Students on Students.id=st_id;\n");
            });
            query3.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query3);

            Button query4 = new Button("query4", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Средние баллы по факультетам");
                vc.render("select distinct department, avg(mark)  from Subjects inner join Marks on  ID=SUB_id inner join Students on Students.id=st_id group by(department);");
            });
            query4.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query4);

            Button query5 = new Button("query5", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Преподаватели, которые не поставили ни одной 5");
                vc.render("select distinct Teachers.NameNum  from Teachers inner join Marks on id=t_id where (t_id not  in  (select Teachers.id from Teachers inner join Marks on ID=T_ID where mark=5) );");
            });
            query5.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query5);

            Button query6 = new Button("query6", cl->{
                dateField.setVisible(true);
                makeQuery.setVisible(true);

                infoLabel.setValue("Студенты, которые сдавали экзамен до указанной даты");
            });
            query6.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query6);


            Button query7 = new Button("query7", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Средние баллы по городам");
                vc.render("select distinct city, avg(mark)  from Subjects inner join Marks on  ID=SUB_id inner join Students on Students.id=st_id group by(city);");
            });
            query7.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query7);

            Button query8 = new Button("query8", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Предметы, которые сдают на ФКТИ");
                vc.render("select distinct Subjects.NameNum  from Subjects inner join Marks on id= st_id inner join Students on Students.id = st_id where Students.Department = \"ФКТИ\";");
            });
            query8.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query8);

            Button query9 = new Button("query9", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Предмет с самой большой нагруженностью");
                vc.render("select nameNum from Subjects where (nagr = (select max(nagr) from Subjects))");
            });
            query9.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query9);

            Button query10 = new Button("query10", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Средний балл по студентам в порядке убывания");
                vc.render("select Students.NameNum, avg(mark) from  Students inner join Marks on id=st_id group by (Students.Id) Order by avg(mark) DESC;");
            });
            query10.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query10);

            Button query11 = new Button("query11", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Преподаватели, которые не принимали экзамены");
                vc.render("select Teachers.NameNum  from Teachers left join Marks on id=t_id where sub_id is null;");
            });
            query11.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query11);

            Button query12 = new Button("query12", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Количество профессоров, которые принимали экзамены");
                vc.render("select count(id) from (select distinct Teachers.id  from  Teachers inner join Marks on id=t_id where position = \"профессор\") as ids;");
            });
            query12.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query12);


            Button query13 = new Button("query13", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Список доцентов");
                vc.render("select  Teachers.nameNum  from  Teachers where Teachers.position = \"доцент\";");
            });
            query13.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query13);

            Button query14 = new Button("query14", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Список студентов, у которых средний балл не меньше 4.5");
                vc.render("select distinct Students.NameNum  from  Students inner join Marks on id=st_id group by (Students.id) having (avg(mark)>=4.5);");
            });
            query14.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query14);

            Button query15 = new Button("query15", cl->{
                dateField.setVisible(false);
                makeQuery.setVisible(false);

                infoLabel.setValue("Список студентов из Спб старше 21 года");
                vc.render("select Students.NameNum from Students where Students.AgeNum > 21 and Students.City = \"Спб\";");
            });
            query15.setStyleName(ValoTheme.BUTTON_TINY);
            addComponents(query15);

        }};
        layout.addComponent(dateField);
        layout.addComponents(vc/*, new Button("click",e->{
            vc.render(tf.getValue())
        })*/);
        //
        //layout.setWidth("1200px");
        horizontalLayout.setWidth("100%");
        layout.setWidth("100%");
        tablesLayout.setWidth("100%");
        queryLayout.setWidth("100%");

        horizontalLayout.addComponents(tablesLayout,queryLayout,layout);
        horizontalLayout.setExpandRatio(layout,0.7f);
        horizontalLayout.setExpandRatio(tablesLayout,0.15f);
        horizontalLayout.setExpandRatio(queryLayout,0.15f);

        setContent(horizontalLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}


class ViewQuery extends VerticalLayout{
    private final SQLDriver sqlDriver=new SQLDriver();
    private Consumer<Void> addButtonListener;
    private Consumer<List<String>> deleteButtonListener=null;
    private String table;
    public void update(){
        renderFullTable(table, addButtonListener,deleteButtonListener);
    }
    ViewQuery(){
        setMargin(false);
    }
    public void render(String query){

        List<List<String>> lls=sqlDriver.query(query);
        removeAllComponents();
        addComponent(new Label("Запрос: "+query));
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
        renderFullTable(table,addButtonListener,null);
    }
    public void renderFullTable(String table, Consumer<Void> addButtonListener,  Consumer<List<String>> deleteButtonListener){
        this.table = table;
        this.addButtonListener =addButtonListener;
        this.deleteButtonListener = deleteButtonListener;
        String query="select * from "+table;

        List<List<String>> lls=sqlDriver.query(query);
        removeAllComponents();

        Button addButton = new Button("Добавить",(cl)-> addButtonListener.accept(null));
        addButton.setStyleName(ValoTheme.BUTTON_TINY);
        addComponent(addButton);
        addComponent(new Label("Запрос: "+query));
        addComponent(new Grid<List<String>>(){{
            setSizeFull();
            setItems(lls.subList(1,lls.size()));

            for(int i=0;i<lls.get(0).size();i++){
                final int ii=i;
                addColumn(ls->ls.get(ii)).setCaption(lls.get(0).get(ii));
            }

            addColumn(ls->{
                Button delete =  new Button("удалить", e->{
                    if (deleteButtonListener==null){
                        sqlDriver.update("delete from "+ table+" where id = "+ls.get(0));
                    } else {
                        deleteButtonListener.accept(ls);
                    }
                    renderFullTable(table, addButtonListener,deleteButtonListener);
                });
                delete.setStyleName(ValoTheme.BUTTON_TINY);
                return delete;
            } ,new ComponentRenderer()).setCaption("удалить заголовок");

        }});
    }
}


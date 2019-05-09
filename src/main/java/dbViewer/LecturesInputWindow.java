package dbViewer;

import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.function.Consumer;

public class LecturesInputWindow extends Window {
    private final VerticalLayout verticalLayout;
    private final TextField nameTextEdit;
    private final TextField passport;
    private final CNumber ageTextEdit;
    private final CNumber expirTextEdit;
    private final CNumber salaryTextEdit;
    private final ComboBox pol;

    public int getExpir(){
        return expirTextEdit.getIntNumber();
    }
    public int getAge(){
        return ageTextEdit.getIntNumber();
    }
    public int getSalary(){
        return salaryTextEdit.getIntNumber();
    }
    public String getPol(){return pol.getValue().toString();}
    public String getPassport(){ return passport.getValue(); }

    public String getName(){ return nameTextEdit.getValue(); }

    LecturesInputWindow(Consumer<LecturesInputWindow> onOkListener){
        super("Добавить механика");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        passport = new TextField();
        passport.setCaption("Паспрорт");
        nameTextEdit = new TextField();
        nameTextEdit.setCaption("ФИО");
        ageTextEdit = new CNumber(1,0,400);
        expirTextEdit = new CNumber(1,0,400);
        salaryTextEdit = new CNumber(20000,10000,400000);
        pol = new ComboBox("Пол");
        pol.setItems(Arrays.asList("Ж","М"));
        verticalLayout.addComponents(passport,nameTextEdit,new Label("Возраст") ,ageTextEdit,
                new Label("Опыт"),expirTextEdit,new Label("Зарплата"),salaryTextEdit,pol, new Button("ok",(cl)->{
            onOkListener.accept(this);
            close();
        }));
    }
}

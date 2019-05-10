package dbViewer;

import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class InputStudentWindow extends Window {
    private final VerticalLayout verticalLayout;
    private final TextField nameTextEdit;
    private final TextField passport;
    private final CNumber ageTextEdit;
    private final CNumber expirTextEdit;
    private final CNumber acc_countTextEdit;
    private final ComboBox pol;
    public int getAge(){
        return ageTextEdit.getIntNumber();
    }
    public int getAccidents(){
        return acc_countTextEdit.getIntNumber();
    }
    public int getExpir(){
        return expirTextEdit.getIntNumber();
    }
    public String getPol(){return pol.getValue().toString();}
    public String getName(){ return nameTextEdit.getValue();}
    public String getPassport(){ return passport.getValue(); }
    InputStudentWindow(Consumer<InputStudentWindow> onOkListener){
        super("Добавить клиента");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        nameTextEdit = new TextField();
        nameTextEdit.setCaption("ФИО");
        passport = new TextField();
        passport.setCaption("Паспрорт");
        ageTextEdit = new CNumber(1,0,400);
        acc_countTextEdit = new CNumber(1,0,10000);
        expirTextEdit = new CNumber(1,0,400);
        pol = new ComboBox("Пол");
        pol.setItems(Arrays.asList("Ж","М"));
        verticalLayout.addComponents(passport,nameTextEdit,new Label("Возраст"),ageTextEdit,
                new Label("Количество обращений"),new Label("Опыт вождения"),expirTextEdit,pol, new Button("ok",(cl)->{
            onOkListener.accept(this);
            close();
        }));
    }
}

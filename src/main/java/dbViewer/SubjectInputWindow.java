package dbViewer;

import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.function.Consumer;

public class SubjectInputWindow extends Window {

    private final VerticalLayout verticalLayout;
    private final TextField nameTextEdit;
    private final CNumber accidsTextEdit;
    private final CNumber ageTextEdit;
    private final TextField passport;


    public int getAge(){ return ageTextEdit.getIntNumber(); }
    public int getAccidents(){
        return accidsTextEdit.getIntNumber();
    }
    public String getPassport(){ return passport.getValue(); }
    public String getName(){
        return nameTextEdit.getValue();
    }

    SubjectInputWindow(Consumer<SubjectInputWindow> onOkListener){
        super("Добавить предмет");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        nameTextEdit = new com.vaadin.ui.TextField();
        nameTextEdit.setCaption("Название");
        accidsTextEdit = new CNumber(1,0,400);
        ageTextEdit = new CNumber(1,0,400);
        passport = new TextField();
        passport.setCaption("Номер лицензии");
        verticalLayout.addComponents(passport,nameTextEdit,new Label("Возраст"), ageTextEdit,new Label("Количество обращений"), accidsTextEdit, new com.vaadin.ui.Button("ok",(cl)->{
            onOkListener.accept(this);
            close();
        }));
    }

}

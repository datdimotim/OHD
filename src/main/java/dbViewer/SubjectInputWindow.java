package dbViewer;

import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.function.Consumer;

public class SubjectInputWindow extends Window {

    private final VerticalLayout verticalLayout;
    private final TextField nameTextEdit;
    private final CNumber zeTextEdit;
    private final CNumber nagrTextEdit;

    public int getNagr(){ return nagrTextEdit.getIntNumber(); }
    public int getZe(){
        return zeTextEdit.getIntNumber();
    }

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
        zeTextEdit = new CNumber(1,0,400);
        nagrTextEdit = new CNumber(1,0,400);

        verticalLayout.addComponents(nameTextEdit,new Label("Зачетные единицы"), zeTextEdit,new Label("Нагрузка"),nagrTextEdit, new com.vaadin.ui.Button("ok",(cl)->{
            onOkListener.accept(this);
            close();
        }));
    }

}

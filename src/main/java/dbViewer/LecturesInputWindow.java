package dbViewer;

import com.vaadin.ui.*;

import java.util.function.Consumer;

public class LecturesInputWindow extends Window {
    private final VerticalLayout verticalLayout;
    private final TextField nameTextEdit;
    private final TextField positionTextEdit;
    private final TextField cafedraTextField;
    public String getName(){
        return nameTextEdit.getValue();
    }
    public String getPosition(){
        return positionTextEdit.getValue();
    }
    public String getCafedra(){return cafedraTextField.getValue();}

    LecturesInputWindow(Consumer<LecturesInputWindow> onOkListener){
        super("Добавить преподавателя");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        nameTextEdit = new TextField();
        cafedraTextField = new TextField();
        nameTextEdit.setCaption("ФИО");
        positionTextEdit = new TextField();
        positionTextEdit.setCaption("Должность");
        cafedraTextField.setCaption("Кафедра");
        verticalLayout.addComponents(nameTextEdit, positionTextEdit, cafedraTextField, new Button("ok",(cl)->{
            onOkListener.accept(this);
            close();
        }));
    }
}

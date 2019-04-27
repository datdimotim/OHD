package dbViewer;

import com.vaadin.ui.*;

import java.util.function.Consumer;

public class InputStudentWindow extends Window {
    private final VerticalLayout verticalLayout;
    private final TextField nameTextEdit;
    private final TextField departmentTextEdit;
    private final TextField cityTextField;
    private final CNumber ageTextEdit;
    public int getAge(){
        return ageTextEdit.getIntNumber();
    }
    public String getName(){
        return nameTextEdit.getValue();
    }
    public String getDepartment(){
        return departmentTextEdit.getValue();
    }
    public String getCity(){return cityTextField.getValue();}

    InputStudentWindow(Consumer<InputStudentWindow> onOkListener){
        super("Добавить студента");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        nameTextEdit = new TextField();
        cityTextField = new TextField();
        nameTextEdit.setCaption("ФИО");
        departmentTextEdit = new TextField();
        departmentTextEdit.setCaption("Факультет");
        ageTextEdit = new CNumber(1,0,400);
        cityTextField.setCaption("Город");
        verticalLayout.addComponents(nameTextEdit, departmentTextEdit,new Label("Возраст"),ageTextEdit,cityTextField, new Button("ok",(cl)->{
            onOkListener.accept(this);
            close();
        }));
    }
}

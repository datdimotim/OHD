package dbViewer;

import com.vaadin.ui.*;

import java.util.function.Consumer;

public class InputStudentWindow extends Window {
    private final VerticalLayout verticalLayout;
    private final TextField nameTextEdit;
    private final TextField departmentTextEdit;
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


    InputStudentWindow(Consumer<InputStudentWindow> onOkListener){
        super("add student");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        nameTextEdit = new TextField();
        nameTextEdit.setCaption("Имя");
        departmentTextEdit = new TextField();
        departmentTextEdit.setCaption("Факультет");
        ageTextEdit = new CNumber(1,0,400);
        verticalLayout.addComponents(nameTextEdit, departmentTextEdit,new Label("Возраст"),ageTextEdit, new Button("ok",(cl)->{
            onOkListener.accept(this);
            close();
        }));
    }
}

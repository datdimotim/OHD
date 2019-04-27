package dbViewer;


import com.vaadin.ui.*;

import java.util.function.Consumer;

public class InputWindow extends Window {
    private final VerticalLayout verticalLayout;
    private final TextField nameTextEdit;
    private final CNumber ageTextEdit;
    public int getAge(){
        return ageTextEdit.getIntNumber();
    }
    public String getName(){
        return nameTextEdit.getValue();
    }


    InputWindow(Consumer<InputWindow> onOkListener){
        super("add person");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        nameTextEdit = new TextField();
        nameTextEdit.setCaption("Имя");
        ageTextEdit = new CNumber(1,0,400);
        verticalLayout.addComponents(nameTextEdit,new Label("Возраст"),ageTextEdit, new Button("ok",(cl)->{
            onOkListener.accept(this);
            close();
        }));
    }
}


package dbViewer;

import com.vaadin.ui.DateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.time.LocalDate;
import java.util.function.Consumer;

public class MarksInputWindow extends Window {
    private final VerticalLayout verticalLayout;
    private final CNumber st_id_TextEdit;
    private final CNumber sub_id_TextEdit;
    private final CNumber lec_id_TextEdit;
    private final CNumber markTextEdit;
    private final DateField dateField;
    public int getSub_id(){ return sub_id_TextEdit.getIntNumber(); }
    public int getSt_id(){return st_id_TextEdit.getIntNumber();}
    public int getLec_Id(){ return lec_id_TextEdit.getIntNumber(); }
    public String getExamDate(){return dateField.getValue().toString();}
    public int getMark(){ return markTextEdit.getIntNumber(); }

    MarksInputWindow(Consumer<MarksInputWindow> onOkListener){
        super("Добавить успеваемость");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        st_id_TextEdit = new CNumber(1,0,Integer.MAX_VALUE-1);
        sub_id_TextEdit = new CNumber(1,0,Integer.MAX_VALUE-1);
        lec_id_TextEdit = new CNumber(1,0,Integer.MAX_VALUE-1);
        markTextEdit = new CNumber(4,2,5);
        dateField = new DateField("Дата экзамена", LocalDate.now());
        verticalLayout.addComponents(
                new com.vaadin.ui.Label("Id предмета"), sub_id_TextEdit,
                new com.vaadin.ui.Label("Id студента"), st_id_TextEdit,
                new com.vaadin.ui.Label("Id преподавателя"), lec_id_TextEdit,
                new com.vaadin.ui.Label("Оценка"), markTextEdit,
                dateField,
                new com.vaadin.ui.Button("ok",(cl)->{
                    onOkListener.accept(this);
                    close();
                }));
    }

}

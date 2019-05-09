package dbViewer;

import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.time.LocalDate;
import java.util.function.Consumer;

public class MarksInputWindow extends Window {
    private final VerticalLayout verticalLayout;
    private final TextField mechanic_id_TextEdit;
    private final TextField car_id_TextEdit;
    private final TextField client_id_TextEdit;
    private final CNumber costTextEdit;
    private final CNumber lastTextEdit;
    private final DateField dateField;
    public String getCar_id(){ return car_id_TextEdit.getValue(); }
    public String getMec_id(){return mechanic_id_TextEdit.getValue();}
    public String  getClient_Id(){ return client_id_TextEdit.getValue(); }
    public String getVisitDate(){return dateField.getValue().toString();}
    public int getCost(){ return costTextEdit.getIntNumber(); }
    public int getTime(){ return lastTextEdit.getIntNumber(); }

    MarksInputWindow(Consumer<MarksInputWindow> onOkListener){
        super("Добавить успеваемость");
        center();
        setClosable(true);
        setModal(true);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);

        mechanic_id_TextEdit = new TextField();
        mechanic_id_TextEdit.setCaption("Паспорт ответственного механика");
        car_id_TextEdit = new TextField();
        car_id_TextEdit.setCaption("Номер регистрации автомобиля");
        client_id_TextEdit = new TextField();
        client_id_TextEdit.setCaption("Паспорт клиента");
        costTextEdit = new CNumber(2000,500,5000000);
        lastTextEdit = new CNumber(7,1,300);

        dateField = new DateField("Дата обращения", LocalDate.now());
        verticalLayout.addComponents(
                dateField,
                car_id_TextEdit,
                mechanic_id_TextEdit,
                client_id_TextEdit,
                new com.vaadin.ui.Label("Стомость"), costTextEdit,
                new com.vaadin.ui.Label("Продолжительность(в днях)"), lastTextEdit,

                new com.vaadin.ui.Button("ok",(cl)->{
                    onOkListener.accept(this);
                    close();
                }));
    }

}

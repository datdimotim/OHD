package dbViewer;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import lombok.Data;
import lombok.Setter;

public class CNumber extends CssLayout {
    public static final int BAD_VALUE = Integer.MIN_VALUE;

    @Setter
    private final TextField textField;
    private final int labelWidth=6;
    private final int labelOffset=20;
    private Binder<TextForValidate> binder = new Binder<>();


    public void setTextFieldValue(String s){
        textField.setValue(s);
    }
    public int getIntNumber(){
        int fig=0;
        try {
            fig = Integer.parseInt(textField.getValue());

        } catch (Exception e) {
            fig = BAD_VALUE;
        }
        return fig;
    }
    public String getStringNumber(){
        return textField.getValue();
    }
    private boolean isNumberFormat(String str){
        try {
            Integer.parseInt(str);
            return true;

        } catch (Exception e){
            return false;
        }
    }

    CNumber(int initial, int min, int max) {
        textField = new TextField();
        textField.setValue(initial+"");
        textField.setWidth(textField.getValue().length()*labelWidth+labelOffset+"px");
        textField.setStyleName("borderless i-hPadding3 small i-small");
        textField.addValueChangeListener(cl->{
            if (isNumberFormat(textField.getValue())&&Integer.parseInt(textField.getValue())>=max) {
                textField.setValue(max+"");
            }
            if (isNumberFormat(textField.getValue())&&Integer.parseInt(textField.getValue())<min) {
                textField.setValue(min+"");
            }
            textField.setWidth(textField.getValue().length()*labelWidth+labelOffset+"px");
            binder.validate();
        });

        //binder.bind(textField, TextForValidate::getText, TextForValidate::setText);
        TextForValidate textForValidate = new TextForValidate(initial+"");
        binder.setBean(textForValidate);
        binder.forField(textField)
                .withValidator(text -> isNumberFormat(text),
                       ("MustBeOnlyNumbers"))
                .bind(TextForValidate::getText, TextForValidate::setText);
        VerticalLayout verticalLayout = new VerticalLayout();
        Button up = new Button("↑",(cl)->{
            try {
                if (Integer.parseInt(textField.getValue()) >= max) return;
            } catch (Exception ex) {return;}
            textField.setValue(Integer.parseInt(textField.getValue())+1+"");
            textField.setWidth(textField.getValue().length()*labelWidth+labelOffset+"px");
            binder.validate();
        });
        up.setStyleName("borderless i-hPadding3 small i-small");
        up.setHeight("15px");

        Button down = new Button("↓",(cl)->{
            try {
                if (Integer.parseInt(textField.getValue()) <= min) return;
            } catch (Exception ex){return;}
            textField.setValue(Integer.parseInt(textField.getValue())-1+"");
            textField.setWidth(textField.getValue().length()*labelWidth+labelOffset+"px");
            binder.validate();
        });
        setWidthUndefined();
        down.setStyleName("borderless i-hPadding3 small i-small");
        down.setHeight("15px");
        verticalLayout.setHeight("15px");
        verticalLayout.setWidth("15px");
        verticalLayout.setMargin(false);
        verticalLayout.addComponents(up, down);

        Label label = new Label("");
        label.setWidth("20px");
        addComponents(textField,verticalLayout);
    }
    @Data
    private class TextForValidate {
        TextForValidate(String initial){
            text=initial;
        }
        String text;
    }
}

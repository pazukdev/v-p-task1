package com.pazukdev;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


public class AddNewHotelForm extends FormLayout {

    private TextField name = new TextField("Name");

    private TextField address = new TextField("Address");
    private TextField rating = new TextField("Rating");
    private NativeSelect<HotelCategory> category = new NativeSelect<>("Hotel category");
    private DateField operatesFrom = new DateField("Operates from");
    private TextField url = new TextField("URL");
    private TextArea description = new TextArea("Description");


    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Close");

    private HotelService service = HotelService.getInstance();
    private Hotel hotel;
    private MyUI myUI;
    private Binder<Hotel> binder = new Binder<>(Hotel.class);

    public AddNewHotelForm(MyUI myUI) {
        this.myUI = myUI;
        binder.bindInstanceFields(this);

        category.setItems(HotelCategory.values());

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> this.save());

        delete.addClickListener(e -> this.delete());

        close.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        close.addClickListener(e -> this.close());

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete, close);
        addComponents(name, address, rating, category, operatesFrom, url, description, buttons);

        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.setRequiredIndicatorVisible(true);
        address.setValueChangeMode(ValueChangeMode.EAGER);
        rating.setValueChangeMode(ValueChangeMode.EAGER);
        url.setValueChangeMode(ValueChangeMode.EAGER);
        description.setValueChangeMode(ValueChangeMode.EAGER);

        delete.setVisible(false);
        save.setVisible(false);

    }

    public void setHotel(Hotel hotel) {
        this.hotel=hotel;
        binder.readBean(hotel);

        name.addValueChangeListener(event -> {
            save.setVisible(true);
            delete.setVisible(false);
        });

        name.addValueChangeListener(event -> {
            if(name.isEmpty()) save.setVisible(false);
        });
        save.setVisible(false);
        delete.setVisible(false);

        address.addValueChangeListener(event -> {
            save.setVisible(true);
            delete.setVisible(false);
        });

        rating.addValueChangeListener(event -> {
            save.setVisible(true);
            delete.setVisible(false);
        });

        category.addValueChangeListener(event -> {
            save.setVisible(true);
            delete.setVisible(false);
        });

        operatesFrom.addValueChangeListener(event -> {
            save.setVisible(true);
            delete.setVisible(false);
        });

        url.addValueChangeListener(event -> {
            save.setVisible(true);
            delete.setVisible(false);
        });

        description.addValueChangeListener(event -> {
            save.setVisible(true);
            delete.setVisible(false);
        });

        //delete.setVisible(hotel.isPersisted());
        setVisible(true);
        name.selectAll();
    }



    public void setNewHotel(Hotel hotel) {
        this.hotel=hotel;
        binder.readBean(hotel);

        name.addValueChangeListener(event -> {
            save.setVisible(true);
            delete.setVisible(false);
        });

        name.addValueChangeListener(event -> {
            if(name.isEmpty()) save.setVisible(false);
        });
        save.setVisible(false);
        delete.setVisible(false);

        setVisible(true);
        name.selectAll();
    }

    private void close() {
        setVisible(false);
    }

    private void delete() {
        service.delete(hotel);
        myUI.updateList();
        setVisible(false);
    }

    private void save() {
        try {
            binder.writeBean(hotel);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        service.save(hotel);
        myUI.updateList();
        setVisible(false);
    }

    void setButtonsVisiblity() {
        save.setVisible(false);
        delete.setVisible(true);
    }


}

package com.pazukdev;

import com.vaadin.data.Binder;
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
    TextField url = new TextField("URL");
    private TextArea description = new TextArea("Description");


    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

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

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(name, address, rating, category, operatesFrom, url, description, buttons);

        url.setValueChangeMode(ValueChangeMode.BLUR);
    }

    public void setHotel(Hotel hotel) {
        this.hotel=hotel;
        binder.setBean(hotel);

        // Show delete button for only hotels already in the database
        delete.setVisible(hotel.isPersisted());
        setVisible(true);
        name.selectAll();
    }

    private void delete() {
        service.delete(hotel);
        myUI.updateList();
        setVisible(false);
    }

    private void save() {
        service.save(hotel);
        myUI.updateList();
        setVisible(false);
    }
}

package com.pazukdev;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.event.FieldEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import java.util.Locale;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private TextField filterByName =new TextField();
    private TextField filterByAddress=new TextField();
    private HotelService service=HotelService.getInstance();
    private Grid<Hotel> grid=new Grid<>(Hotel.class);
    private AddNewHotelForm form = new AddNewHotelForm(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        filterByName.setPlaceholder("filter by name...");
        filterByName.addValueChangeListener(e -> updateList("by name"));
        filterByName.setValueChangeMode(ValueChangeMode.LAZY);

        filterByAddress.setPlaceholder("filter by address...");
        filterByAddress.addValueChangeListener(e -> updateList("by address"));
        filterByAddress.setValueChangeMode(ValueChangeMode.LAZY);


        Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> filterByName.clear());
        clearFilterTextBtn.addClickListener(e -> filterByAddress.clear());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterByName, filterByAddress, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        Button addHotelButton = new Button("Add new hotel");
        addHotelButton.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setHotel(new Hotel());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addHotelButton);

        grid.setColumns(
                //"id",
                "name",
                "address",
                //"rating",
                //"category",
                //"operatesFrom",
                //"url",
                "description"
        );

        Grid.Column<Hotel, String> urlColumn = grid.addColumn(hotel ->
                "<a target=\"_blank\" href='" + hotel.getUrl() + "'>" + hotel.getUrl() + "</a>", new HtmlRenderer())
                .setCaption("URL");

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                form.setVisible(false);
            } else {
                form.setHotel(event.getValue());
            }
        });

        form.setVisible(false);

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        layout.addComponents(toolbar, main);

        updateList();
        setContent(layout);
    }

    public void updateList() {
        //List<Hotel> hotels=service.findAll();
        List<Hotel> hotels = service.findAll(filterByName.getValue());
        grid.setItems(hotels);
    }

    public void updateList(String filter) {
        List<Hotel> hotels=null;
        switch(filter) {
            case "by name":
                hotels = service.findAll(filterByName.getValue(), "by name");
                break;
            case "by address":
                hotels = service.findAll(filterByAddress.getValue(), "by address");
                break;

        }
        if(hotels!=null) grid.setItems(hotels);
    }



    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

package com.sda.exampleclient;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import javafx.beans.property.Property;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringUI
public class ClientGui extends UI{

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout components = new VerticalLayout();

        TextField textFieldIn = new TextField();
        TextField textFieldOut = new TextField();

        TextField ile = new TextField();
        /*List<String> data = IntStream.range(0, 6).mapToObj(i -> "Option " + i).collect(Collectors.toList());

        ListSelect<String> sample = new ListSelect<>("Select an option", data);
        sample.setRows(6);
        sample.select(data.get(2));
        sample.setWidth(20.0f, Unit.PERCENTAGE);

        sample.addValueChangeListener(event -> Notification.show("Value changed:", String.valueOf(event.getValue()),
                Notification.Type.TRAY_NOTIFICATION));*/

        Button button = new Button("Przelicz");
        Label labelResult = new Label();
        button.addClickListener(clickEvent -> {

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Response> exchange = restTemplate.exchange(
                    "https://free.currencyconverterapi.com/api/v6/convert?q=" +
                            textFieldIn.getValue() + "_" + textFieldOut.getValue(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    Response.class);

            LinkedHashMap eur_usd = (LinkedHashMap) exchange.getBody().getResults().getAdditionalProperties().get(
                    textFieldIn.getValue() + "_" + textFieldOut.getValue());
            Object val1 = eur_usd.get("val");
/*            int ileWaluty = Integer.parseInt(ile.getValue());
            int suma = ileWaluty * ((int)val1);*/

            labelResult.setValue(String.valueOf(val1));
            /*exchange.getStatusCodeValue();
            double val = exchange.getBody().getResults().getEURPLN().getVal();
            labelResult.setValue(String.valueOf(val));*/
        });


        components.addComponent(textFieldIn);
        components.addComponent(textFieldOut);
        components.addComponent(ile);
        components.addComponent(labelResult);
/*
        components.addComponent(sample);
*/
        components.addComponent(button);

        setContent(components);
    }
}

package com.sda.exampleclient;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@SpringUI
public class ClientGui extends UI{

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout components = new VerticalLayout();

        TextField textFieldKwota = new TextField("Kwota");
        TextField textFieldIn = new TextField("Przelicz z:");
        TextField textFieldOut = new TextField("Przelicz na:");
        Button button = new Button("Przelicz");
        Label labelResult = new Label();
        labelResult.setCaption("Kurs");

        Label labelTest = new Label();
        labelTest.setCaption("Koszt");

        button.addClickListener(clickEvent -> {

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Response> exchange = restTemplate.exchange(
                    "https://free.currencyconverterapi.com/api/v6/convert?q=" +
                            textFieldIn.getValue() + "_" + textFieldOut.getValue(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    Response.class);

            LinkedHashMap kurs = (LinkedHashMap) exchange.getBody().getResults().getAdditionalProperties().get(
                    textFieldIn.getValue() + "_" + textFieldOut.getValue());
            Object val1 = kurs.get("val");

            labelResult.setValue(String.valueOf(val1));
            labelTest.setValue(String.valueOf(Double.parseDouble(String.valueOf(val1))*Double.parseDouble(textFieldKwota.getValue())));
            /*exchange.getStatusCodeValue();
            double val = exchange.getBody().getResults().getEURPLN().getVal();
            labelResult.setValue(String.valueOf(val));*/
        });

        components.addComponent(textFieldKwota);
        components.addComponent(textFieldIn);
        components.addComponent(textFieldOut);
        components.addComponent(button);
        components.addComponent(labelResult);
        components.addComponent(labelTest);

        setContent(components);
    }
}

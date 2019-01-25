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

        TextField textFieldIn = new TextField();
        TextField textFieldOut = new TextField();
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
            labelResult.setValue(String.valueOf(val1));
            /*exchange.getStatusCodeValue();
            double val = exchange.getBody().getResults().getEURPLN().getVal();
            labelResult.setValue(String.valueOf(val));*/
        });


        components.addComponent(textFieldIn);
        components.addComponent(textFieldOut);
        components.addComponent(labelResult);
        components.addComponent(button);

        setContent(components);
    }
}

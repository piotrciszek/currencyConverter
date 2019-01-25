package org.sample.currency.app.service;

import org.sample.currency.app.controller.currency.dto.CurrencyExchange;
import org.sample.currency.app.dao.CurrencyExchangeHistoryRepository;
import org.sample.currency.app.model.CurrencyExchangeHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Business service for currency exchange history operations.
 *
 * Created by Mohamed Mekkawy.
 */
@Service
public class CurrencyExchangeHistoryService {

    @Autowired
    private CurrencyExchangeHistoryRepository currencyExchangeHistoryRepository;

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeHistoryService.class);

    /**
     * Add entry in the history collection for the passed exchange for the current user.
     *
     * @param currencyExchange
     */
    public void saveExchangeInUserHistory(CurrencyExchange currencyExchange){
        //Get current user
        User activeUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CurrencyExchangeHistory currencyExchangeHistory = new CurrencyExchangeHistory();

        currencyExchangeHistory.setFromCurrency(currencyExchange.getLeftCurrency());
        currencyExchangeHistory.setToCurrency(currencyExchange.getRightCurrency());
        currencyExchangeHistory.setUserId(activeUser.getUsername());
        currencyExchangeHistory.setCurrentDate(new Date());
        currencyExchangeHistory.setValue(currencyExchange.getValue());
        currencyExchangeHistory.setExchangeDate(currencyExchange.getExchangeDate());

        currencyExchangeHistoryRepository.save(currencyExchangeHistory);

    }

    /**
     * Get all entries in exchange history for the current active user.
     *
     */
    public List<CurrencyExchangeHistory> getHistoryForCurrentUser(){
        User activeUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currencyExchangeHistoryRepository.findByUserId(activeUser.getUsername());
    }
}

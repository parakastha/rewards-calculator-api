package com.example.rewardscalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerRewardService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository; // Declare the CustomerRepository field


    @Autowired
    public CustomerRewardService(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository; // Set the CustomerRepository field

    }

    public Map<Month, Integer> calculateRewardPoints(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<Transaction> transactions = transactionRepository.findByCustomerAndDateBetween(customer, threeMonthsAgo, LocalDate.now());

        Map<Month, Integer> monthlyPoints = new HashMap<>();

        for (Transaction transaction : transactions) {
            int points = calculatePoints(transaction.getAmount());
            monthlyPoints.merge(transaction.getDate().getMonth(), points, Integer::sum);
        }

        return monthlyPoints;
    }

    private int calculatePoints(double amount) {
        if (amount > 100) {
            return (int) ((amount - 100) * 2 + 50);
        } else if (amount > 50) {
            return (int) (amount - 50);
        } else {
            return 0;
        }
    }
}

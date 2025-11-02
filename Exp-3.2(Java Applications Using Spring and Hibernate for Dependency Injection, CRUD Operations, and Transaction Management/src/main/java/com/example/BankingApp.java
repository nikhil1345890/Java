package com.example;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

public class BankingApp {

    // ---- Entities ----
    @Entity
    @Table(name = "accounts")
    public static class Account {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String accountNumber;

        @Column(nullable = false)
        private double balance;

        public Account() {}
        public Account(String accountNumber, double balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        // getters and setters
        public Long getId() { return id; }
        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public double getBalance() { return balance; }
        public void setBalance(double balance) { this.balance = balance; }
        @Override
        public String toString() { return accountNumber + ": " + balance; }
    }

    public static void main(String[] args) {
        // ---- Hibernate Setup ----
        Configuration cfg = new Configuration()
                .addAnnotatedClass(Account.class)
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://bytexldb.com:5051/db_4432wq58b")
                .setProperty("hibernate.connection.username", "user_4432wq58b")
                .setProperty("hibernate.connection.password", "p4432wq58b")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty("hibernate.show_sql", "true");

        SessionFactory sessionFactory = cfg.buildSessionFactory();

        // ---- Transaction Manager ----
        PlatformTransactionManager txManager = new HibernateTransactionManager(sessionFactory);

        // ---- Create accounts ----
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(new Account("ACC1001", 1000));
            session.persist(new Account("ACC1002", 500));
            session.getTransaction().commit();
        }

        // ---- Money transfer logic ----
        transferMoney(sessionFactory, txManager, "ACC1001", "ACC1002", 200);

        // ---- Verify balances ----
        try (Session session = sessionFactory.openSession()) {
            Account acc1 = session.createQuery("FROM Account WHERE accountNumber='ACC1001'", Account.class).uniqueResult();
            Account acc2 = session.createQuery("FROM Account WHERE accountNumber='ACC1002'", Account.class).uniqueResult();
            System.out.println("After transfer:");
            System.out.println(acc1);
            System.out.println(acc2);
        }

        sessionFactory.close();
    }

    public static void transferMoney(SessionFactory sessionFactory, PlatformTransactionManager txManager,
                                     String fromAccNum, String toAccNum, double amount) {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());

        try (Session session = sessionFactory.openSession()) {
            Account from = session.createQuery("FROM Account WHERE accountNumber=:num", Account.class)
                                  .setParameter("num", fromAccNum).uniqueResult();
            Account to = session.createQuery("FROM Account WHERE accountNumber=:num", Account.class)
                                .setParameter("num", toAccNum).uniqueResult();

            if (from.getBalance() < amount) throw new RuntimeException("Insufficient funds!");

            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);

            session.merge(from);
            session.merge(to);

            txManager.commit(status);
            System.out.println("Transfer successful!");
        } catch (Exception e) {
            txManager.rollback(status);
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
}

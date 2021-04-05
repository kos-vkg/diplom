package ru.netology.manager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.data.Credit;
import ru.netology.data.Payment;
import ru.netology.data.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class DbManager {

    //static final String URLDB = "jdbc:mysql://localhost:3306/app";
    static final String URLDB = "jdbc:postgresql://localhost:5432/app";

     // возвращает запись из таблицы
    public static Credit getCredit() {
        String usersSQL = "SELECT * FROM credit_request_entity;";
        var runner = new QueryRunner();
        try (
                Connection conn = DriverManager.getConnection(
                        URLDB, "app", "pass"
                )
        ) {
            Credit record = runner.query(conn, usersSQL, new BeanHandler<>(Credit.class));
            return record;
        } catch (SQLException e) {
            System.out.println("Не удалось получить код доступа к таблице Credit");
        }
        return null;
    }

    public static Payment getPayment() {
        String usersSQL = "SELECT * FROM payment_entity;";
        var runner = new QueryRunner();
        try (
                Connection conn = DriverManager.getConnection(
                        URLDB, "app", "pass"
                )
        ) {
            Payment record = runner.query(conn, usersSQL, new BeanHandler<>(Payment.class));
            return record;
        } catch (SQLException e) {
            System.out.println("Не удалось получить код доступа к таблице Payment");
        }
        return null;
    }

    public static Order getOrder() {
        String usersSQL = "SELECT * FROM order_entity;";
        var runner = new QueryRunner();
        try (
                Connection conn = DriverManager.getConnection(
                        URLDB, "app", "pass"
                )
        ) {
            Order record = runner.query(conn, usersSQL, new BeanHandler<>(Order.class));
            return record;
        } catch (SQLException e) {
            System.out.println("Не удалось получить код доступа к таблице Order");
        }
        return null;
    }

       //проверяет количество записей в таблице
    public static void checkRecordCount(String tabName, long expectedRecordCount) {
        String countSQL = "SELECT COUNT(*) FROM " + tabName;
        var runner = new QueryRunner();
        try (
                Connection conn = DriverManager.getConnection(
                        URLDB, "app", "pass"
                )
        ) {
       assertEquals(expectedRecordCount, (long) runner.query(conn, countSQL, new ScalarHandler<>())
               ,"неверное количество записей в таблице " + tabName+" ");
        return ;
        } catch (SQLException e) {
            System.out.println("Не удалось получить код доступа к таблице " + tabName );
        }
        return ;
    }

      // очищает все таблицы
    public static void clearTables() {
        var runner = new QueryRunner();
        String delOrderSQL = "DELETE FROM app.public.order_entity;";
        String delPaymentSQL = "DELETE FROM app.public.payment_entity;";
        String delCreditSQL = "DELETE FROM app.public.credit_request_entity;";
        try (
                Connection conn = DriverManager.getConnection(
                        URLDB, "app", "pass"
                )
        ) {
            // очистка таблиц
            runner.update(conn, delOrderSQL);
            runner.update(conn, delPaymentSQL);
            runner.update(conn, delCreditSQL);
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицы базы данных");
        }
    }
}

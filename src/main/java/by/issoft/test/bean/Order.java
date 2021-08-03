package by.issoft.test.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    private static final long serialVersionUID = -7418382734486249507L;
    @CsvBindByName(column = "ID")
    private String id;
    @CsvBindByName(column = "DATE_TIME")
    @CsvDate("yyyy-MM-dd")
    private Date date;



    public Order(String id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "{" +
                "id='" + id + '\'' +
                ", date=" + date +
                '}';
    }
}

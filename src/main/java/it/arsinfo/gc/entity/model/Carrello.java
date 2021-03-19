package it.arsinfo.gc.entity.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"scanCode"})
})
public class Carrello extends EntityBase {
    @Override
    public String toString() {
        return "Carrello{" +
                "scanCode='" + scanCode + '\'' +
                '}';
    }

    public Carrello() {
        super();
    }

    @Column(nullable = false)
    private String scanCode;

    public Carrello(String scanCode) {
        this.scanCode = scanCode;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }
}

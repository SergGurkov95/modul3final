package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "following_connection")
public class FollSub implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "master_id")
    private User master;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slave_id")
    private User slave;


    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public User getSlave() {
        return slave;
    }

    public void setSlave(User slave) {
        this.slave = slave;
    }
}

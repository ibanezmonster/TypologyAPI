package com.typology.entity.user;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    private String name;

    @ManyToOne							//establishes link between Customer and Authorities
    @JoinColumn(name = "customer_id")	//column that joins the two tables
    private AppUser appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppUser getCustomer() {
        return appUser;
    }

    public void setCustomer(AppUser customer) {
        this.appUser = customer;
    }

}

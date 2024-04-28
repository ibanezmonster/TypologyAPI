package com.typology.entity.user;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//,generator="native")
    //@GenericGenerator(name = "native",strategy = "native")
    private long id;

    private String name;

    @ManyToOne(fetch=FetchType.EAGER)							
    @JoinColumn(name = "user_id")		
    private AppUser appUser;

    public long getId() {
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

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser user) {
        this.appUser = user;
    }

}

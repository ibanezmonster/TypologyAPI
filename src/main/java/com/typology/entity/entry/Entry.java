package com.typology.entity.entry;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import static com.typology.namedQueries.NamedQueriesDB.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@NoArgsConstructor
//@Setter
//@Getter
@Entity
@Table(name = "entry")
@NamedQueries(value = {
				@NamedQuery(name = "query_jpql", query = GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS_JPQL)
				})
public class Entry
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "enneagramCoreType")
	private int enneagramCoreType;
	
	//@JsonProperty("enneagramCoreType")
	public int getEnneagramCoreType() {
		return enneagramCoreType;
	}
	
	public void setEnneagramCoreType(int enneagramCoreType) {
		this.enneagramCoreType = enneagramCoreType;
	}
		
	
	
	//private String entryType;	
//	private int enneagramTriType;
//	private int mbtiType;
//	private int socionicsType;
	
	
//	
//
//    @NotBlank(message = "name must be not empty")
//    private String name;
//
//    @NotBlank(message = "password must be not empty")
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private String password;
//
//    @NotBlank(message = "email must be not empty")
//    @Email
//    private String email;
//
//    Customer(Long id,
//             String name,
//             String password,
//             String email) {
//        this.id = id;
//        this.name = name;
//        this.password = password;
//        this.email = email;
//    }
//
//    public Customer() {
//    }
//
    //@JsonProperty("customer_id")
    public long getId() {
        return id;
    }
//
//    public String getName() {
//        return name;
//    }
//
//    @JsonIgnore
//    public String getPassword() {
//        return password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public String toString() {
//        return "Customer{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", password='" + password + '\'' +
//                ", email='" + email + '\'' +
//                '}';
//    }
	
}
















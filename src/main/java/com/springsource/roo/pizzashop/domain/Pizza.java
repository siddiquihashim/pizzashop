package com.springsource.roo.pizzashop.domain;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import com.springsource.roo.pizzashop.domain.Topping;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import com.springsource.roo.pizzashop.domain.Base;
import javax.persistence.ManyToOne;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooEntity
public class Pizza {

    @NotNull
    @Size(min = 2)
    private String name;

    private Float price;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Topping> toppings = new HashSet<Topping>();

    @ManyToOne
    private Base base;

	@PersistenceContext
    transient EntityManager entityManager;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Pizza attached = Pizza.findPizza(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Pizza merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Pizza merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static final EntityManager entityManager() {
        EntityManager em = new Pizza().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPizzas() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Pizza o", Long.class).getSingleResult();
    }

	public static List<Pizza> findAllPizzas() {
        return entityManager().createQuery("SELECT o FROM Pizza o", Pizza.class).getResultList();
    }

	public static Pizza findPizza(Long id) {
        if (id == null) return null;
        return entityManager().find(Pizza.class, id);
    }

	public static List<Pizza> findPizzaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Pizza o", Pizza.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Float getPrice() {
        return this.price;
    }

	public void setPrice(Float price) {
        this.price = price;
    }

	public Set<Topping> getToppings() {
        return this.toppings;
    }

	public void setToppings(Set<Topping> toppings) {
        this.toppings = toppings;
    }

	public Base getBase() {
        return this.base;
    }

	public void setBase(Base base) {
        this.base = base;
    }

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Base: ").append(getBase()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("Price: ").append(getPrice()).append(", ");
        sb.append("Toppings: ").append(getToppings() == null ? "null" : getToppings().size()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
}

package transport.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import transport.customtype.DataStatus;
import transport.customtype.Role;

@Entity
@Table(name="TRANAUTHORIZATION")
@SequenceGenerator(name="SEQ_AUTHORIZATION", sequenceName="GEN_AUTHORIZATION", allocationSize=1)
public class Authorization implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="SEQ_AUTHORIZATION", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private User user;
	
	@Column(name="ROLE")
	@Enumerated(EnumType.STRING)
	private Role role = Role.NORMAL;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}

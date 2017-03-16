package de.rwth.i9.cimt.model.wikipedia;
// Generated Mar 14, 2017 3:48:09 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Interwiki generated by hbm2java
 */
@Entity
@Table(name = "interwiki", catalog = "mediawiki", uniqueConstraints = @UniqueConstraint(columnNames = "iw_prefix"))
public class Interwiki implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036004574701891746L;
	private InterwikiId id;

	public Interwiki() {
	}

	public Interwiki(InterwikiId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "iwPrefix", column = @Column(name = "iw_prefix", unique = true, nullable = false, length = 32)),
			@AttributeOverride(name = "iwUrl", column = @Column(name = "iw_url", nullable = false)),
			@AttributeOverride(name = "iwApi", column = @Column(name = "iw_api", nullable = false)),
			@AttributeOverride(name = "iwWikiid", column = @Column(name = "iw_wikiid", nullable = false, length = 64)),
			@AttributeOverride(name = "iwLocal", column = @Column(name = "iw_local", nullable = false)),
			@AttributeOverride(name = "iwTrans", column = @Column(name = "iw_trans", nullable = false)) })
	public InterwikiId getId() {
		return this.id;
	}

	public void setId(InterwikiId id) {
		this.id = id;
	}

}

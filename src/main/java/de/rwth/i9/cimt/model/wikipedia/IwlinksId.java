package de.rwth.i9.cimt.model.wikipedia;
// Generated Mar 14, 2017 3:48:09 PM by Hibernate Tools 4.3.5.Final

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IwlinksId generated by hbm2java
 */
@Embeddable
public class IwlinksId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4480834778487302326L;
	private int iwlFrom;
	private byte[] iwlPrefix;
	private String iwlTitle;

	public IwlinksId() {
	}

	public IwlinksId(int iwlFrom, byte[] iwlPrefix, String iwlTitle) {
		this.iwlFrom = iwlFrom;
		this.iwlPrefix = iwlPrefix;
		this.iwlTitle = iwlTitle;
	}

	@Column(name = "iwl_from", nullable = false)
	public int getIwlFrom() {
		return this.iwlFrom;
	}

	public void setIwlFrom(int iwlFrom) {
		this.iwlFrom = iwlFrom;
	}

	@Column(name = "iwl_prefix", nullable = false)
	public byte[] getIwlPrefix() {
		return this.iwlPrefix;
	}

	public void setIwlPrefix(byte[] iwlPrefix) {
		this.iwlPrefix = iwlPrefix;
	}

	@Column(name = "iwl_title", nullable = false)
	public String getIwlTitle() {
		return this.iwlTitle;
	}

	public void setIwlTitle(String iwlTitle) {
		this.iwlTitle = iwlTitle;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IwlinksId))
			return false;
		IwlinksId castOther = (IwlinksId) other;

		return (this.getIwlFrom() == castOther.getIwlFrom())
				&& ((this.getIwlPrefix() == castOther.getIwlPrefix())
						|| (this.getIwlPrefix() != null && castOther.getIwlPrefix() != null
								&& Arrays.equals(this.getIwlPrefix(), castOther.getIwlPrefix())))
				&& ((this.getIwlTitle() == castOther.getIwlTitle()) || (this.getIwlTitle() != null
						&& castOther.getIwlTitle() != null && this.getIwlTitle().equals(castOther.getIwlTitle())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIwlFrom();
		int iwlPrefixHashcode = 0;
		byte[] iwlPrefixProperty = this.getIwlPrefix();
		if (iwlPrefixProperty != null) {
			iwlPrefixHashcode = 1;
			for (int i = 0; i < iwlPrefixProperty.length; i++) {
				iwlPrefixHashcode = 37 * iwlPrefixHashcode + iwlPrefixProperty[i];
			}
		}

		result = 37 * result + iwlPrefixHashcode;

		result = 37 * result + (getIwlTitle() == null ? 0 : this.getIwlTitle().hashCode());
		return result;
	}

}

package de.rwth.i9.cimt.model.wikipedia;
// Generated Mar 14, 2017 3:48:09 PM by Hibernate Tools 4.3.5.Final

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * InterwikiId generated by hbm2java
 */
@Embeddable
public class InterwikiId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1606831897959741549L;
	private String iwPrefix;
	private byte[] iwUrl;
	private byte[] iwApi;
	private String iwWikiid;
	private boolean iwLocal;
	private byte iwTrans;

	public InterwikiId() {
	}

	public InterwikiId(String iwPrefix, byte[] iwUrl, byte[] iwApi, String iwWikiid, boolean iwLocal, byte iwTrans) {
		this.iwPrefix = iwPrefix;
		this.iwUrl = iwUrl;
		this.iwApi = iwApi;
		this.iwWikiid = iwWikiid;
		this.iwLocal = iwLocal;
		this.iwTrans = iwTrans;
	}

	@Column(name = "iw_prefix", unique = true, nullable = false, length = 32)
	public String getIwPrefix() {
		return this.iwPrefix;
	}

	public void setIwPrefix(String iwPrefix) {
		this.iwPrefix = iwPrefix;
	}

	@Column(name = "iw_url", nullable = false)
	public byte[] getIwUrl() {
		return this.iwUrl;
	}

	public void setIwUrl(byte[] iwUrl) {
		this.iwUrl = iwUrl;
	}

	@Column(name = "iw_api", nullable = false)
	public byte[] getIwApi() {
		return this.iwApi;
	}

	public void setIwApi(byte[] iwApi) {
		this.iwApi = iwApi;
	}

	@Column(name = "iw_wikiid", nullable = false, length = 64)
	public String getIwWikiid() {
		return this.iwWikiid;
	}

	public void setIwWikiid(String iwWikiid) {
		this.iwWikiid = iwWikiid;
	}

	@Column(name = "iw_local", nullable = false)
	public boolean isIwLocal() {
		return this.iwLocal;
	}

	public void setIwLocal(boolean iwLocal) {
		this.iwLocal = iwLocal;
	}

	@Column(name = "iw_trans", nullable = false)
	public byte getIwTrans() {
		return this.iwTrans;
	}

	public void setIwTrans(byte iwTrans) {
		this.iwTrans = iwTrans;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InterwikiId))
			return false;
		InterwikiId castOther = (InterwikiId) other;

		return ((this.getIwPrefix() == castOther.getIwPrefix()) || (this.getIwPrefix() != null
				&& castOther.getIwPrefix() != null && this.getIwPrefix().equals(castOther.getIwPrefix())))
				&& ((this.getIwUrl() == castOther.getIwUrl()) || (this.getIwUrl() != null
						&& castOther.getIwUrl() != null && Arrays.equals(this.getIwUrl(), castOther.getIwUrl())))
				&& ((this.getIwApi() == castOther.getIwApi()) || (this.getIwApi() != null
						&& castOther.getIwApi() != null && Arrays.equals(this.getIwApi(), castOther.getIwApi())))
				&& ((this.getIwWikiid() == castOther.getIwWikiid()) || (this.getIwWikiid() != null
						&& castOther.getIwWikiid() != null && this.getIwWikiid().equals(castOther.getIwWikiid())))
				&& (this.isIwLocal() == castOther.isIwLocal()) && (this.getIwTrans() == castOther.getIwTrans());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIwPrefix() == null ? 0 : this.getIwPrefix().hashCode());
		int iwUrlHashcode = 0;
		byte[] iwUrlProperty = this.getIwUrl();
		if (iwUrlProperty != null) {
			iwUrlHashcode = 1;
			for (int i = 0; i < iwUrlProperty.length; i++) {
				iwUrlHashcode = 37 * iwUrlHashcode + iwUrlProperty[i];
			}
		}

		result = 37 * result + iwUrlHashcode;

		int iwApiHashcode = 0;
		byte[] iwApiProperty = this.getIwApi();
		if (iwApiProperty != null) {
			iwApiHashcode = 1;
			for (int i = 0; i < iwApiProperty.length; i++) {
				iwApiHashcode = 37 * iwApiHashcode + iwApiProperty[i];
			}
		}

		result = 37 * result + iwApiHashcode;

		result = 37 * result + (getIwWikiid() == null ? 0 : this.getIwWikiid().hashCode());
		result = 37 * result + (this.isIwLocal() ? 1 : 0);
		result = 37 * result + this.getIwTrans();
		return result;
	}

}

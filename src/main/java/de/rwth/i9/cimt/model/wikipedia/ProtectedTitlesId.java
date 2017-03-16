package de.rwth.i9.cimt.model.wikipedia;
// Generated Mar 14, 2017 3:48:09 PM by Hibernate Tools 4.3.5.Final

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProtectedTitlesId generated by hbm2java
 */
@Embeddable
public class ProtectedTitlesId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1766670767108893840L;
	private int ptNamespace;
	private String ptTitle;
	private int ptUser;
	private byte[] ptReason;
	private byte[] ptTimestamp;
	private byte[] ptExpiry;
	private byte[] ptCreatePerm;

	public ProtectedTitlesId() {
	}

	public ProtectedTitlesId(int ptNamespace, String ptTitle, int ptUser, byte[] ptTimestamp, byte[] ptExpiry,
			byte[] ptCreatePerm) {
		this.ptNamespace = ptNamespace;
		this.ptTitle = ptTitle;
		this.ptUser = ptUser;
		this.ptTimestamp = ptTimestamp;
		this.ptExpiry = ptExpiry;
		this.ptCreatePerm = ptCreatePerm;
	}

	public ProtectedTitlesId(int ptNamespace, String ptTitle, int ptUser, byte[] ptReason, byte[] ptTimestamp,
			byte[] ptExpiry, byte[] ptCreatePerm) {
		this.ptNamespace = ptNamespace;
		this.ptTitle = ptTitle;
		this.ptUser = ptUser;
		this.ptReason = ptReason;
		this.ptTimestamp = ptTimestamp;
		this.ptExpiry = ptExpiry;
		this.ptCreatePerm = ptCreatePerm;
	}

	@Column(name = "pt_namespace", nullable = false)
	public int getPtNamespace() {
		return this.ptNamespace;
	}

	public void setPtNamespace(int ptNamespace) {
		this.ptNamespace = ptNamespace;
	}

	@Column(name = "pt_title", nullable = false)
	public String getPtTitle() {
		return this.ptTitle;
	}

	public void setPtTitle(String ptTitle) {
		this.ptTitle = ptTitle;
	}

	@Column(name = "pt_user", nullable = false)
	public int getPtUser() {
		return this.ptUser;
	}

	public void setPtUser(int ptUser) {
		this.ptUser = ptUser;
	}

	@Column(name = "pt_reason")
	public byte[] getPtReason() {
		return this.ptReason;
	}

	public void setPtReason(byte[] ptReason) {
		this.ptReason = ptReason;
	}

	@Column(name = "pt_timestamp", nullable = false)
	public byte[] getPtTimestamp() {
		return this.ptTimestamp;
	}

	public void setPtTimestamp(byte[] ptTimestamp) {
		this.ptTimestamp = ptTimestamp;
	}

	@Column(name = "pt_expiry", nullable = false)
	public byte[] getPtExpiry() {
		return this.ptExpiry;
	}

	public void setPtExpiry(byte[] ptExpiry) {
		this.ptExpiry = ptExpiry;
	}

	@Column(name = "pt_create_perm", nullable = false)
	public byte[] getPtCreatePerm() {
		return this.ptCreatePerm;
	}

	public void setPtCreatePerm(byte[] ptCreatePerm) {
		this.ptCreatePerm = ptCreatePerm;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProtectedTitlesId))
			return false;
		ProtectedTitlesId castOther = (ProtectedTitlesId) other;

		return (this.getPtNamespace() == castOther.getPtNamespace())
				&& ((this.getPtTitle() == castOther.getPtTitle()) || (this.getPtTitle() != null
						&& castOther.getPtTitle() != null && this.getPtTitle().equals(castOther.getPtTitle())))
				&& (this.getPtUser() == castOther.getPtUser())
				&& ((this.getPtReason() == castOther.getPtReason())
						|| (this.getPtReason() != null && castOther.getPtReason() != null
								&& Arrays.equals(this.getPtReason(), castOther.getPtReason())))
				&& ((this.getPtTimestamp() == castOther.getPtTimestamp())
						|| (this.getPtTimestamp() != null && castOther.getPtTimestamp() != null
								&& Arrays.equals(this.getPtTimestamp(), castOther.getPtTimestamp())))
				&& ((this.getPtExpiry() == castOther.getPtExpiry())
						|| (this.getPtExpiry() != null && castOther.getPtExpiry() != null
								&& Arrays.equals(this.getPtExpiry(), castOther.getPtExpiry())))
				&& ((this.getPtCreatePerm() == castOther.getPtCreatePerm())
						|| (this.getPtCreatePerm() != null && castOther.getPtCreatePerm() != null
								&& Arrays.equals(this.getPtCreatePerm(), castOther.getPtCreatePerm())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPtNamespace();
		result = 37 * result + (getPtTitle() == null ? 0 : this.getPtTitle().hashCode());
		result = 37 * result + this.getPtUser();
		int ptReasonHashcode = 0;
		byte[] ptReasonProperty = this.getPtReason();
		if (ptReasonProperty != null) {
			ptReasonHashcode = 1;
			for (int i = 0; i < ptReasonProperty.length; i++) {
				ptReasonHashcode = 37 * ptReasonHashcode + ptReasonProperty[i];
			}
		}

		result = 37 * result + ptReasonHashcode;

		int ptTimestampHashcode = 0;
		byte[] ptTimestampProperty = this.getPtTimestamp();
		if (ptTimestampProperty != null) {
			ptTimestampHashcode = 1;
			for (int i = 0; i < ptTimestampProperty.length; i++) {
				ptTimestampHashcode = 37 * ptTimestampHashcode + ptTimestampProperty[i];
			}
		}

		result = 37 * result + ptTimestampHashcode;

		int ptExpiryHashcode = 0;
		byte[] ptExpiryProperty = this.getPtExpiry();
		if (ptExpiryProperty != null) {
			ptExpiryHashcode = 1;
			for (int i = 0; i < ptExpiryProperty.length; i++) {
				ptExpiryHashcode = 37 * ptExpiryHashcode + ptExpiryProperty[i];
			}
		}

		result = 37 * result + ptExpiryHashcode;

		int ptCreatePermHashcode = 0;
		byte[] ptCreatePermProperty = this.getPtCreatePerm();
		if (ptCreatePermProperty != null) {
			ptCreatePermHashcode = 1;
			for (int i = 0; i < ptCreatePermProperty.length; i++) {
				ptCreatePermHashcode = 37 * ptCreatePermHashcode + ptCreatePermProperty[i];
			}
		}

		result = 37 * result + ptCreatePermHashcode;

		return result;
	}

}

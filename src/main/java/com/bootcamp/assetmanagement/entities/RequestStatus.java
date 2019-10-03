/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "tb_tr_request_status")
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "insert_request_status",
            procedureName = "sp_add_status_request",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "catatan", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "statusid", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "requestid", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "approver", type = String.class)
            }
    )
})
@NamedQueries({
    @NamedQuery(name = "RequestStatus.findAll", query = "SELECT r FROM RequestStatus r")
    , @NamedQuery(name = "RequestStatus.findById", query = "SELECT r FROM RequestStatus r WHERE r.id = :id")
    , @NamedQuery(name = "RequestStatus.findByDateTime", query = "SELECT r FROM RequestStatus r WHERE r.dateTime = :dateTime")})
public class RequestStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "approver", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Employee approver;
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ApprovalStatus status;
    @JoinColumn(name = "request", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Request request;

    public RequestStatus() {
    }

    public RequestStatus(String id) {
        this.id = id;
    }

    public RequestStatus(String id, Date dateTime, String note) {
        this.id = id;
        this.dateTime = dateTime;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestStatus)) {
            return false;
        }
        RequestStatus other = (RequestStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.assetmanagement.entities.RequestStatus[ id=" + id + " ]";
    }
    
}

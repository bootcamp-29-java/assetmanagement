/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "tb_tr_request")
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "insert_request",
            procedureName = "sp_add_request",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "note", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "typeid", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "requesterid", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "reportid", type = String.class)
            }
    )
})
@NamedQueries({
    @NamedQuery(name = "Request.findAll", query = "SELECT r FROM Request r")
    , @NamedQuery(name = "Request.findById", query = "SELECT r FROM Request r WHERE r.id = :id")
    , @NamedQuery(name = "Request.findByRequestDate", query = "SELECT r FROM Request r WHERE r.requestDate = :requestDate")})
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "request_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "requester", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Employee requester;
    @JoinColumn(name = "current_status", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ApprovalStatus currentStatus;
    @JoinColumn(name = "type", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RequestType type;
    @JoinColumn(name = "current_approval", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee currentApproval;
    @OneToOne(mappedBy = "report", fetch = FetchType.LAZY)
    private Request request;
    @JoinColumn(name = "report", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Request report;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request", fetch = FetchType.LAZY)
    private List<RequestStatus> requestStatusList;

    public Request() {
    }

    public Request(String id) {
        this.id = id;
    }

    public Request(String id, Date requestDate) {
        this.id = id;
        this.requestDate = requestDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee getRequester() {
        return requester;
    }

    public void setRequester(Employee requester) {
        this.requester = requester;
    }

    public ApprovalStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ApprovalStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Employee getCurrentApproval() {
        return currentApproval;
    }

    public void setCurrentApproval(Employee currentApproval) {
        this.currentApproval = currentApproval;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getReport() {
        return report;
    }

    public void setReport(Request report) {
        this.report = report;
    }

    @XmlTransient
    public List<RequestStatus> getRequestStatusList() {
        return requestStatusList;
    }

    public void setRequestStatusList(List<RequestStatus> requestStatusList) {
        this.requestStatusList = requestStatusList;
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
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.assetmanagement.entities.Request[ id=" + id + " ]";
    }
    
}
